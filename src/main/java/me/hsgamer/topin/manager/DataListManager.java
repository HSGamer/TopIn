package me.hsgamer.topin.manager;

import me.hsgamer.topin.data.list.DataList;

import java.util.*;

/**
 * The data list manager
 */
public final class DataListManager {

    private final Map<String, DataList> dataListMap = new HashMap<>();

    /**
     * Register a data list
     *
     * @param dataList the data list
     */
    public void register(DataList dataList) {
        if (!dataList.canRegister()) {
            return;
        }

        String name = dataList.getName();
        if (dataListMap.containsKey(name)) {
            return;
        }
        dataListMap.put(name, dataList);
        dataList.registerConfigPath();
        dataList.register();
        dataList.loadData();
    }

    /**
     * Unregister a data list
     *
     * @param name the name of the data list
     */
    public void unregister(String name) {
        if (!dataListMap.containsKey(name)) {
            return;
        }

        DataList dataList = dataListMap.remove(name);
        dataList.saveData();
        dataList.unregister();
    }

    /**
     * Save a data list
     *
     * @param name the name of the data list
     */
    public void saveDataList(String name) {
        if (!dataListMap.containsKey(name)) {
            return;
        }

        dataListMap.get(name).saveData();
    }

    /**
     * Save all data lists to file
     */
    public void saveAll() {
        dataListMap.forEach((name, dataList) -> dataList.saveData());
    }

    /**
     * Unregister all data lists
     */
    public void unregisterAll() {
        dataListMap.values().forEach(DataList::unregister);
    }

    /**
     * Get the data list
     *
     * @param name the name
     * @return the data list
     */
    public Optional<DataList> getDataList(String name) {
        return Optional.ofNullable(dataListMap.get(name));
    }

    /**
     * Get the list of the available data lists
     *
     * @return the list of data lists
     */
    public Collection<DataList> getDataLists() {
        return new ArrayList<>(dataListMap.values());
    }

    /**
     * Clear all data lists
     */
    public void clearAll() {
        dataListMap.clear();
    }

    /**
     * Add a new unique id to the data list
     *
     * @param uuid the unique id
     */
    public void addNew(UUID uuid) {
        dataListMap.values().forEach(dataList -> dataList.add(uuid));
    }

    /**
     * Get the suggested data list
     *
     * @param start the start of the names
     * @return the suggested names
     */
    public List<String> getSuggestedDataListNames(String start) {
        List<String> list = new ArrayList<>(dataListMap.keySet());
        if (start != null && !start.isEmpty()) {
            list.removeIf(s -> !s.startsWith(start));
        }
        return list;
    }
}
