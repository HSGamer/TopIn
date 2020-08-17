package me.hsgamer.topin.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;

/**
 * The getter manager
 */
public final class GetterManager {

  private static final String GETTER_PLACEHOLDER = "<getter>";
  private final List<Getter> getterList = new ArrayList<>();

  /**
   * Register the getter
   *
   * @param getter the getter
   */
  public void register(Getter getter) {
    if (MainConfig.IGNORED_DATA_LIST.getValue().contains(getter.getName())) {
      return;
    }
    if (!getter.canRegister()) {
      return;
    }

    getter.register();
    getterList.add(getter);
    MessageUtils.sendMessage(Bukkit.getConsoleSender(),
        MessageConfig.GETTER_REGISTERED.getValue().replace(GETTER_PLACEHOLDER, getter.getName()));
  }

  /**
   * Unregister the getter
   *
   * @param getter the getter
   */
  public void unregister(Getter getter) {
    getter.unregister();
    getterList.remove(getter);
    MessageUtils.sendMessage(Bukkit.getConsoleSender(),
        MessageConfig.GETTER_UNREGISTERED.getValue().replace(GETTER_PLACEHOLDER, getter.getName()));
  }

  /**
   * Unregister all getters
   */
  public void unregisterAll() {
    getterList.forEach(getter -> {
      getter.unregister();
      MessageUtils.sendMessage(Bukkit.getConsoleSender(),
          MessageConfig.GETTER_UNREGISTERED.getValue()
              .replace(GETTER_PLACEHOLDER, getter.getName()));
    });
    getterList.clear();
  }

  /**
   * Get the list of getters
   *
   * @return the list of getters
   */
  public List<String> getGetters() {
    return getterList.stream().map(Getter::getName).collect(Collectors.toList());
  }

  /**
   * Get the getter by the name
   *
   * @param name the name
   * @return the getter
   */
  public Optional<Getter> getGetter(String name) {
    return getterList.stream().filter(getter -> getter.getName().equals(name)).findFirst();
  }
}
