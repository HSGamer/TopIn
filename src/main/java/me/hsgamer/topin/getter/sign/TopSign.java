package me.hsgamer.topin.getter.sign;

import io.papermc.lib.PaperLib;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.data.list.DataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.util.*;

public final class TopSign {

    private final Location location;
    private final String dataListName;
    private final int index;

    public TopSign(Location location, String dataListName, int index) {
        this.location = location;
        this.dataListName = dataListName;
        this.index = index;
    }

    public static TopSign deserialize(Map<String, Object> args) {
        return new TopSign(Location.deserialize(args), (String) args.get("data-list"), (int) args.get("index"));
    }

    public void update() {
        Optional<DataList> optionalDataList = TopIn.getInstance().getDataListManager()
                .getDataList(dataListName);
        if (!optionalDataList.isPresent()) {
            return;
        }

        PaperLib.getChunkAtAsync(location, false).thenAccept(chunk -> {
            if (chunk == null) {
                return;
            }

            DataList dataList = optionalDataList.get();
            if (index < 0 || index >= dataList.getSize()) {
                return;
            }
            PairDecimal pairDecimal = dataList.getPair(index);
            BlockState blockState = location.getBlock().getState();
            if (blockState instanceof Sign) {
                Sign sign = (Sign) blockState;
                String[] lines = getSignLines(pairDecimal, dataList);
                for (int i = 0; i < 4; i++) {
                    sign.setLine(i, lines[i]);
                }
                sign.update(false, false);
            }
        });
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(location.serialize());
        map.put("data-list", dataListName);
        map.put("index", index);
        return map;
    }

    private String[] getSignLines(PairDecimal pairDecimal, DataList dataList) {
        List<String> list = MessageConfig.SIGN_LINES.getValue();
        int startIndex = MainConfig.DISPLAY_TOP_START_INDEX.getValue();
        Objects.requireNonNull(list).replaceAll(s -> MessageUtils.colorize(s
                .replace("<name>", Bukkit.getOfflinePlayer(pairDecimal.getUniqueId()).getName())
                .replace("<value>", dataList.formatValue(pairDecimal.getValue()))
                .replace("<suffix>", dataList.getSuffix())
                .replace("<index>", String.valueOf(index + startIndex))
                .replace("<data_list>", dataList.getDisplayName())
        ));
        String[] lines = new String[4];
        for (int i = 0; i < 4; i++) {
            lines[i] = i < list.size() ? list.get(i) : "";
        }
        return lines;
    }

    public Location getLocation() {
        return location;
    }
}
