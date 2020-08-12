package me.hsgamer.topin;

import static me.hsgamer.hscore.bukkit.utils.PermissionUtils.createPermission;
import static me.hsgamer.topin.TopIn.getInstance;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {

  public static final String PREFIX = getInstance().getName().toLowerCase();

  public static final Permission RELOAD = createPermission(PREFIX + ".reload", null,
      PermissionDefault.OP);
  public static final Permission HELP = createPermission(PREFIX + ".help", null,
      PermissionDefault.OP);
  public static final Permission GETTERS = createPermission(PREFIX + ".getters", null,
      PermissionDefault.OP);
  public static final Permission SKULL = createPermission(PREFIX + ".skull", null,
      PermissionDefault.OP);
  public static final Permission SKULL_BREAK = createPermission(PREFIX + ".skull.break", null,
      PermissionDefault.OP);
  public static final Permission DATALIST = createPermission(PREFIX + ".datalist", null,
      PermissionDefault.OP);
  public static final Permission SIGN = createPermission(PREFIX + ".sign", null,
      PermissionDefault.OP);
  public static final Permission SIGN_BREAK = createPermission(PREFIX + ".sign.break", null,
      PermissionDefault.OP);

  private Permissions() {

  }
}
