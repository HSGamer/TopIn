package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultMoney extends AutoUpdateSimpleDataList {

  private Economy economy;

  public VaultMoney() {
    super(40);
  }

  @Override
  public boolean canRegister() {
    if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
      return false;
    }

    RegisteredServiceProvider<Economy> rspE = Bukkit.getServicesManager()
        .getRegistration(Economy.class);
    if (rspE == null) {
      return false;
    }

    economy = rspE.getProvider();
    return true;
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return new PairDecimal(uuid) {
      @Override
      public void update() {
        setValue(BigDecimal.valueOf(economy.getBalance(Bukkit.getOfflinePlayer(getUniqueId()))));
      }
    };
  }

  @Override
  public String getName() {
    return "vault_money";
  }

  @Override
  public String getDefaultDisplayName() {
    return "Money";
  }

  @Override
  public String getDefaultSuffix() {
    return "$";
  }
}
