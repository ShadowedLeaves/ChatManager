package me.shadowedleaves.chatManager.gui;

import me.shadowedleaves.chatManager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class ChatManagerNoLogsGUI {

    public static void openGUI(Player player) {

        if (!player.hasPermission("chatmanager.command.logs")) return;

        Inventory noLogs = Bukkit.createInventory(player, InventoryType.DROPPER, ChatColor.BLUE + "Chat Logs - Page 1");

        ItemStack filler = createFillerItem();
        ItemStack noLogsItem = createNoLogsItem();

        int rows = noLogs.getSize() / 9;

        for (int slot = 0; slot < noLogs.getSize(); slot++) {
            int row = slot / 9;
            int column = slot % 9;

            if (row == 0 || row == rows - 1 || column == 0 || column == 8) {
                noLogs.setItem(slot, filler);
            }
        }

        noLogs.setItem(4, noLogsItem);

        player.openInventory(noLogs);
        player.setMetadata("noLogsMenu", new FixedMetadataValue(ChatManager.getInstance(), "noLogsMenu"));
    }

    private static ItemStack createFillerItem() {
        ItemStack fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerItemMeta = fillerItem.getItemMeta();
        fillerItemMeta.setDisplayName(" ");
        fillerItem.setItemMeta(fillerItemMeta);

        return fillerItem;
    }

    private static ItemStack createNoLogsItem() {
        ItemStack noLogsItem = new ItemStack(Material.BARRIER);
        ItemMeta nologsItemMeta = noLogsItem.getItemMeta();
        nologsItemMeta.setDisplayName(ChatColor.RED + "There are no logs!");
        nologsItemMeta.setLore(List.of(
                "",
                ChatColor.GRAY + "Check again later for unresolved chat logs"));
        noLogsItem.setItemMeta(nologsItemMeta);

        return noLogsItem;
    }
}
