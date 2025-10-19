package com.ryderbelserion.map.constants;

import net.kyori.adventure.key.Key;
import static com.ryderbelserion.map.Pl3xMapCommon.namespace;

public class Messages {

    public static final Key default_locale = Key.key(namespace, "default"); // if there is no files in the locale folder, this represents messages.yml

    public static final Key reload_plugin = Key.key(namespace, "reload_plugin");
    public static final Key feature_disabled = Key.key(namespace, "feature_disabled");
    public static final Key must_be_player = Key.key(namespace, "must_be_player");
    public static final Key must_be_console_sender = Key.key(namespace, "must_be_console_sender");
    public static final Key target_not_online = Key.key(namespace, "target_not_online");
    public static final Key target_same_player = Key.key(namespace, "target_same_player");
    public static final Key no_permission = Key.key(namespace, "no_permission");
    public static final Key inventory_not_empty = Key.key(namespace, "inventory_not_empty");
    public static final Key internal_error = Key.key(namespace, "internal_error");
    public static final Key message_empty = Key.key(namespace, "message_empty");

}