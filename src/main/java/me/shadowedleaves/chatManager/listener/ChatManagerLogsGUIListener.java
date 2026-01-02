package me.shadowedleaves.chatManager.listener;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.gui.ChatManagerLogsGUI;
import me.shadowedleaves.chatManager.setting.ChatManagerLogsSettings;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataType;

public class ChatManagerLogsGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("logsMenu")) {
            event.setCancelled(true);

            if (!player.hasPermission("chatmanager.command.logs")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatColor.RED + "You do not have permission to do this!");

                return;
            }

            if (event.getRawSlot() == 49) {
                ChatManagerLogsSettings.getInstance().clearLogs();
                player.closeInventory();
                ChatManagerLogsGUI.openGUI(player, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }

            if (event.getCurrentItem() == null) return;

            if (event.getCurrentItem().getType() == Material.ARROW) {

                if (event.getSlot() == 46) {
                    player.closeInventory();
                    int currentPage = getCurrentPage(event);
                    int previousPage = Math.max(1, currentPage - 1);
                    ChatManagerLogsGUI.openGUI(player, previousPage);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                    return;
                }

                if (event.getSlot() == 52) {
                    player.closeInventory();
                    int currentPage = getCurrentPage(event);
                    int nextPage = currentPage + 1;
                    ChatManagerLogsGUI.openGUI(player, nextPage);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                    return;
                }
            }

            if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                String uuid = event.getCurrentItem().getItemMeta().getPersistentDataContainer()
                        .get(new NamespacedKey(ChatManager.getInstance(), "log-id"), PersistentDataType.STRING);

                if (uuid == null) return;

                String username = event.getCurrentItem().getItemMeta().getDisplayName().replace(ChatColor.RED.toString(), "");
                ChatManagerLogsSettings logsSettings = ChatManagerLogsSettings.getInstance();

                logsSettings.removeMessage(username, uuid);

                if (logsSettings.getMessages(username).isEmpty()) {
                    logsSettings.removePlayer(username);
                }

                player.closeInventory();
                ChatManagerLogsGUI.openGUI(player, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("logsMenu")) {
            player.removeMetadata("logsMenu", ChatManager.getInstance());
        }
    }

    private int getCurrentPage(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.contains("Page ")) {

            try {
                return Integer.parseInt(title.split("Page ")[1].trim());
            } catch (NumberFormatException e) {

                return 1;
            }
        }

        return 1;
    }
}
