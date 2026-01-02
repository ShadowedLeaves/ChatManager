package me.shadowedleaves.chatManager.gui;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class ChatManagerGUI {

    public static void openGUI(Player player) {

        if (!player.hasPermission("chatmanager.command.config")) return;

        Inventory chatManagerConfiguration = Bukkit.createInventory(player, 9 * 3, ChatColor.BLUE + "ChatManager | Configuration");

        ItemStack filler = createFillerItem();

        ItemStack chatFilter = createChatFilterItem();
        ItemStack repetitiveMessageFilter = createRepetitiveMessageFilterItem();
        ItemStack urlFilter = createURLFilterItem();
        ItemStack notifyStaff = createNotifyStaffItem();

        ItemStack logs = createLogsItem();

        int rows = chatManagerConfiguration.getSize() / 9;

        for (int slot = 0; slot < chatManagerConfiguration.getSize(); slot++) {
            int row = slot / 9;
            int column = slot % 9;

            if (row == 0 || row == rows - 1 || column == 0 || column == 8) {
                chatManagerConfiguration.setItem(slot, filler);
            }
        }

        chatManagerConfiguration.setItem(10, chatFilter);
        chatManagerConfiguration.setItem(11, repetitiveMessageFilter);
        chatManagerConfiguration.setItem(12, urlFilter);
        chatManagerConfiguration.setItem(13, notifyStaff);

        chatManagerConfiguration.setItem(15, logs);

        player.openInventory(chatManagerConfiguration);
        player.setMetadata("configMenu", new FixedMetadataValue(ChatManager.getInstance(), "configMenu"));
    }

    private static ItemStack createFillerItem() {
        ItemStack fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerItemMeta = fillerItem.getItemMeta();
        fillerItemMeta.setDisplayName(" ");
        fillerItem.setItemMeta(fillerItemMeta);

        return fillerItem;
    }

    private static ItemStack createChatFilterItem() {
        ItemStack chatFilterItem = new ItemStack(Material.HOPPER);
        ItemMeta chatFilterItemMeta = chatFilterItem.getItemMeta();
        chatFilterItemMeta.setDisplayName(ChatColor.BLUE + "Chat Filter");
        chatFilterItemMeta.setLore(List.of(
                ChatColor.GRAY + "Click to toggle",
                ChatColor.GRAY + "Setting: {setting}".replace("{setting}", ChatManagerSettings.getInstance().getChatFilter() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled")));
        chatFilterItem.setItemMeta(chatFilterItemMeta);

        return chatFilterItem;
    }

    private static ItemStack createRepetitiveMessageFilterItem() {
        ItemStack repetitiveMessageFilterItem = new ItemStack(Material.HOPPER);
        ItemMeta repetitiveMessageFilterItemMeta = repetitiveMessageFilterItem.getItemMeta();
        repetitiveMessageFilterItemMeta.setDisplayName(ChatColor.BLUE + "Repetitive Message Filter");
        repetitiveMessageFilterItemMeta.setLore(List.of(
                ChatColor.GRAY + "Click to toggle",
                ChatColor.GRAY + "Setting: {setting}".replace("{setting}", ChatManagerSettings.getInstance().getRepetitiveMessageFilter() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled")));
        repetitiveMessageFilterItem.setItemMeta(repetitiveMessageFilterItemMeta);

        return repetitiveMessageFilterItem;
    }

    private static ItemStack createURLFilterItem() {
        ItemStack urlFilterItem = new ItemStack(Material.HOPPER);
        ItemMeta urlFilterItemMeta = urlFilterItem.getItemMeta();
        urlFilterItemMeta.setDisplayName(ChatColor.BLUE + "URL Filter");
        urlFilterItemMeta.setLore(List.of(
                ChatColor.GRAY + "Click to toggle",
                ChatColor.GRAY + "Setting: {setting}".replace("{setting}", ChatManagerSettings.getInstance().getUrlFilter() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled")));
        urlFilterItem.setItemMeta(urlFilterItemMeta);

        return urlFilterItem;
    }

    private static ItemStack createNotifyStaffItem() {
        ItemStack notifyStaffItem = new ItemStack(Material.HOPPER);
        ItemMeta notifyStaffItemMeta = notifyStaffItem.getItemMeta();
        notifyStaffItemMeta.setDisplayName(ChatColor.BLUE + "Notify Staff");

        if (!ChatManagerSettings.getInstance().getChatFilter()) {
            notifyStaffItemMeta.setLore(List.of(
                    ChatColor.GRAY + "Click to toggle",
                    ChatColor.GRAY + "Setting: {setting}".replace("{setting}", ChatColor.RED + "Disabled"),
                    "",
                    ChatColor.GRAY + "" + ChatColor.ITALIC + "This setting is {setting}".replace("{setting}", ChatColor.RED + "" + ChatColor.ITALIC + "Disabled"),
                    ChatColor.GRAY + "" + ChatColor.ITALIC + "because Chat Filter is",
                    ChatColor.GRAY + "" + ChatColor.ITALIC + "currently {setting}".replace("{setting}", ChatColor.RED + "" + ChatColor.ITALIC + "Disabled") + ChatColor.GRAY + ChatColor.ITALIC + "!"));
        } else {
            notifyStaffItemMeta.setLore(List.of(
                    ChatColor.GRAY + "Click to toggle",
                    ChatColor.GRAY + "Setting: {setting}".replace("{setting}", ChatManagerSettings.getInstance().getNotifyStaff() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled")));
        }

        notifyStaffItem.setItemMeta(notifyStaffItemMeta);

        return notifyStaffItem;
    }

    private static ItemStack createLogsItem() {
        ItemStack logsItem = new ItemStack(Material.BOOK);
        ItemMeta logsItemMeta = logsItem.getItemMeta();
        logsItemMeta.setDisplayName(ChatColor.BLUE + "Chat Logs");
        logsItemMeta.setLore(List.of(
                "",
                ChatColor.GRAY + "Click to view chat logs"));
        logsItem.setItemMeta(logsItemMeta);

        return logsItem;
    }
}
