package me.shadowedleaves.chatManager.listener;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.gui.ChatManagerGUI;
import me.shadowedleaves.chatManager.gui.ChatManagerLogsGUI;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ChatManagerGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("configMenu")) {
            event.setCancelled(true);

            if (!player.hasPermission("chatmanager.command.config")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatColor.RED + "You do not have permission to do this!");

                return;
            }

            if (event.getRawSlot() == 10) {

                if (ChatManagerSettings.getInstance().getChatFilter()) {
                    ChatManagerSettings.getInstance().setChatFilter(false);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                } else {
                    ChatManagerSettings.getInstance().setChatFilter(true);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                }
            }

            if (event.getRawSlot() == 11) {

                if (ChatManagerSettings.getInstance().getRepetitiveMessageFilter()) {
                    ChatManagerSettings.getInstance().setRepetitiveMessageFilter(false);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                } else {
                    ChatManagerSettings.getInstance().setRepetitiveMessageFilter(true);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                }
            }

            if (event.getRawSlot() == 12) {

                if (ChatManagerSettings.getInstance().getUrlFilter()) {
                    ChatManagerSettings.getInstance().setUrlFilter(false);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                } else {
                    ChatManagerSettings.getInstance().setUrlFilter(true);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                }
            }

            if (event.getRawSlot() == 13) {

                if (ChatManagerSettings.getInstance().getChatFilter()) {

                    if (ChatManagerSettings.getInstance().getNotifyStaff()) {
                        ChatManagerSettings.getInstance().setNotifyStaff(false);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.closeInventory();
                        ChatManagerGUI.openGUI(player);
                    } else {
                        ChatManagerSettings.getInstance().setNotifyStaff(true);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.closeInventory();
                        ChatManagerGUI.openGUI(player);
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                }
            }

            if (event.getRawSlot() == 15) {

                if (player.hasPermission("chatmanager.command.logs")) {
                    player.closeInventory();
                    ChatManagerLogsGUI.openGUI(player, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                    player.closeInventory();
                    ChatManagerGUI.openGUI(player);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("configMenu")) {
            player.removeMetadata("configMenu", ChatManager.getInstance());
        }
    }
}
