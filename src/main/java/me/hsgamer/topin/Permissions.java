package me.hsgamer.topin;

import static me.hsgamer.hscore.bukkit.utils.PermissionUtils.createPermission;
import static me.hsgamer.topin.TopIn.getInstance;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Permissions {

  public static final String PREFIX = getInstance().getName().toLowerCase();

  public static final Permission RELOAD = createPermission(PREFIX + ".reload", null,
      PermissionDefault.OP);
  public static final Permission HELP = createPermission(PREFIX + ".help", null,
      PermissionDefault.OP);
  public static final Permission GETTERS = createPermission(PREFIX + ".getters", null,
      PermissionDefault.OP);
  public static final Permission SKULL = createPermission(PREFIX + ".skull", null,
      PermissionDefault.OP);

  private Permissions() {

  }
}
