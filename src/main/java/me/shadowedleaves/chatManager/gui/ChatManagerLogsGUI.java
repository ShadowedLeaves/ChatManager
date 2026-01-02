package me.shadowedleaves.chatManager.gui;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.setting.ChatManagerLogsSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatManagerLogsGUI {

    public static void openGUI(Player player, int page) {

        if (!player.hasPermission("chatmanager.command.logs")) return;

        if (ChatManagerLogsSettings.getInstance().getLogs().isEmpty()) {
            ChatManagerNoLogsGUI.openGUI(player);

            return;
        }

        Inventory chatManagerLogs = Bukkit.createInventory(player, 9 * 6, ChatColor.BLUE + "Chat Logs - Page " + page);

        ItemStack filler = createFillerItem();

        int size = chatManagerLogs.getSize();

        String[] users = ChatManagerLogsSettings.getInstance().getLogs().toArray(new String[0]);

        List<LogEntry> logEntries = new ArrayList<>();

        for (String username : users) {
            Map<String, String> messages = ChatManagerLogsSettings.getInstance().getMessages(username);

            for (Map.Entry<String, String> entry : messages.entrySet()) {
                logEntries.add(new LogEntry(username, entry.getKey(), entry.getValue()));
            }
        }

        int rows = size / 9;

        for (int slot = 0; slot < chatManagerLogs.getSize(); slot++) {
            int row = slot / 9;
            int column = slot % 9;

            if (row == 0 || row == rows - 1 || column == 0 || column == 8) {
                chatManagerLogs.setItem(slot, filler);
            }
        }

        int slot = 0;

        int slotsPerPage = 28;
        int startIndex = (page - 1) * slotsPerPage;
        int endIndex = Math.min(startIndex + slotsPerPage, logEntries.size());

        for (int i = startIndex; i < endIndex; i++) {
            LogEntry entry = logEntries.get(i);

            while (slot < size && isBorderSlot(slot, size)) {
                slot++;
            }

            if (slot >= size) break;

            ItemStack head = createLogHeadItem(entry.username(), entry.message());
            ItemMeta meta = head.getItemMeta();
            meta.getPersistentDataContainer().set(
                    new NamespacedKey(ChatManager.getInstance(), "log-id"),
                    PersistentDataType.STRING,
                    entry.logId());
            head.setItemMeta(meta);
            chatManagerLogs.setItem(slot, head);

            slot++;
        }

        if (page > 1) chatManagerLogs.setItem(46, createPageArrow("<- Previous Page"));

        if (endIndex < logEntries.size()) chatManagerLogs.setItem(52, createPageArrow("Next Page ->"));

        chatManagerLogs.setItem(49, createClearLogsItem());

        player.openInventory(chatManagerLogs);
        player.setMetadata("logsMenu", new FixedMetadataValue(ChatManager.getInstance(), "logsMenu"));
    }

    private static ItemStack createFillerItem() {
        ItemStack fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerItemMeta = fillerItem.getItemMeta();
        fillerItemMeta.setDisplayName(" ");
        fillerItem.setItemMeta(fillerItemMeta);

        return fillerItem;
    }

    private static ItemStack createLogHeadItem(String username, String message) {
        ItemStack logHeadItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta logHeadItemMeta = (SkullMeta) logHeadItem.getItemMeta();

        try {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
            logHeadItemMeta.setOwningPlayer(offlinePlayer);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to fetch player head for: " + username);
        }

        logHeadItemMeta.setDisplayName(ChatColor.RED + username);

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add(ChatColor.GRAY + "Click to remove this log");
        lore.add("");

        int maxLineLength = 25;
        boolean firstLine = true;

        while (!message.isEmpty()) {
            String line;

            if (message.length() <= maxLineLength) {
                line = message;
                message = "";
            } else {
                int breakIndex = message.lastIndexOf(' ', maxLineLength);

                if (breakIndex == -1) {
                    breakIndex = maxLineLength;
                }
                line = message.substring(0, breakIndex);
                message = message.substring(Math.min(breakIndex + 1, message.length()));
            }

            if (firstLine) {
                lore.add(ChatColor.DARK_RED + "Message: " + line);
                firstLine = false;
            } else {
                lore.add(ChatColor.DARK_RED + line);
            }
        }
        logHeadItemMeta.setLore(lore);
        logHeadItem.setItemMeta(logHeadItemMeta);

        return logHeadItem;
    }

    private static ItemStack createPageArrow(String name) {
        ItemStack pageArrowItem = new ItemStack(Material.ARROW);
        ItemMeta pageArrowItemMeta = pageArrowItem.getItemMeta();
        pageArrowItemMeta.setDisplayName(ChatColor.BLUE + name);
        pageArrowItem.setItemMeta(pageArrowItemMeta);

        return pageArrowItem;
    }

    private static ItemStack createClearLogsItem() {
        ItemStack clearLogsItem = new ItemStack(Material.BARRIER);
        ItemMeta clearLogsItemMeta = clearLogsItem.getItemMeta();
        clearLogsItemMeta.setDisplayName(ChatColor.RED + "Clear Chat Logs");
        clearLogsItemMeta.setLore(List.of(
                "",
                ChatColor.GRAY + "Click to clear " + ChatColor.BOLD + "ALL" + ChatColor.GRAY + " chat logs",
                "",
                ChatColor.DARK_RED + "This action is irreversible!"));
        clearLogsItem.setItemMeta(clearLogsItemMeta);

        return clearLogsItem;
    }

    private static boolean isBorderSlot(int slot, int size) {
        int row = slot / 9;
        int column = slot % 9;
        int rows = size / 9;

        return row == 0 || row == rows - 1 || column == 0 || column == 8;
    }

    private record LogEntry(String username, String logId, String message) {}
}
