package me.hsgamer.topin;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import static me.hsgamer.topin.TopIn.getInstance;

public final class Permissions {

    public static final String PREFIX = getInstance().getName().toLowerCase();

    public static final Permission RELOAD = new Permission(PREFIX + ".reload", PermissionDefault.OP);
    public static final Permission HELP = new Permission(PREFIX + ".help", PermissionDefault.OP);
    public static final Permission GETTERS = new Permission(PREFIX + ".getters", PermissionDefault.OP);
    public static final Permission SKULL = new Permission(PREFIX + ".skull", PermissionDefault.OP);
    public static final Permission SKULL_BREAK = new Permission(PREFIX + ".skull.break", PermissionDefault.OP);
    public static final Permission DATALIST = new Permission(PREFIX + ".datalist", PermissionDefault.OP);
    public static final Permission SIGN = new Permission(PREFIX + ".sign", PermissionDefault.OP);
    public static final Permission SIGN_BREAK = new Permission(PREFIX + ".sign.break", PermissionDefault.OP);
    public static final Permission TOP = new Permission(PREFIX + ".top", PermissionDefault.OP);

    private Permissions() {

    }
}
