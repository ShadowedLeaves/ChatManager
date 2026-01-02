package me.shadowedleaves.chatManager;

import me.shadowedleaves.chatManager.command.ChatManagerCommand;
import me.shadowedleaves.chatManager.hook.ProtocolLibHook;
import me.shadowedleaves.chatManager.listener.ChatManagerGUIListener;
import me.shadowedleaves.chatManager.listener.ChatManagerLogsGUIListener;
import me.shadowedleaves.chatManager.listener.ChatManagerNoLogsGUIListener;
import me.shadowedleaves.chatManager.setting.ChatManagerLogsSettings;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatManagerGUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatManagerLogsGUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatManagerNoLogsGUIListener(), this);

        getCommand("chatmanager").setExecutor(new ChatManagerCommand());

        ChatManagerSettings.getInstance().load();
        ChatManagerLogsSettings.getInstance().load();

        ProtocolLibHook.register();
    }

    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.hasMetadata("configMenu")) {
                player.closeInventory();
                player.removeMetadata("configMenu", this);
            }

            if (player.hasMetadata("logsMenu")) {
                player.closeInventory();
                player.removeMetadata("logsMenu", this);
            }

            if (player.hasMetadata("noLogsMenu")) {
                player.closeInventory();
                player.removeMetadata("noLogsMenu", this);
            }
        }
    }

    public static ChatManager getInstance() {

        return getPlugin(ChatManager.class);
    }
}
