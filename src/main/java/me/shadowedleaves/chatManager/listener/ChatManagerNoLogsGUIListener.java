package me.shadowedleaves.chatManager.listener;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ChatManagerNoLogsGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("noLogsMenu")) {
            event.setCancelled(true);

            if (!player.hasPermission("chatmanager.command.logs")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatColor.RED + "You do not have permission to do this!");

                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("noLogsMenu")) {
            player.removeMetadata("noLogsMenu", ChatManager.getInstance());
        }
    }
}