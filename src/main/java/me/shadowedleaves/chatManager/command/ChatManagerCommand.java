package me.shadowedleaves.chatManager.command;

import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.gui.ChatManagerGUI;
import me.shadowedleaves.chatManager.gui.ChatManagerLogsGUI;
import me.shadowedleaves.chatManager.setting.ChatManagerLogsSettings;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatManagerCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        String prefix = ChatManagerSettings.getInstance().getPrefix();
        String noPermissionMessage = ChatManagerSettings.getInstance().getNoPermissionMessage();
        String usageMessage = ChatManagerSettings.getInstance().getUsageMessage();

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(prefix + ChatColor.RED + "Only players can use this command!");

            return true;
        }

        if (strings.length == 0) {

            if (!player.hasPermission("chatmanager.command.config")) {
                player.sendMessage(prefix + noPermissionMessage);

                return true;
            }

            ChatManagerGUI.openGUI(player);

            return true;
        }

        if (strings[0].equalsIgnoreCase("help")) {

            if (!player.hasPermission("chatmanager.command.help")) {
                player.sendMessage(prefix + noPermissionMessage);
                return true;
            }

            if (strings.length == 1) {
                player.sendMessage(prefix + ChatColor.BLUE + "ChatManager Help");

                if (player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager gui " + ChatColor.WHITE + "- Open the main configuration GUI");
                }

                if (player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager config " + ChatColor.WHITE + "- Configure ChatManager settings");
                }

                if (player.hasPermission("chatmanager.command.logs")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager logs [page] " + ChatColor.WHITE + "- Open the logs GUI");
                }

                if (player.hasPermission("chatmanager.command.clearchat")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager clearchat " + ChatColor.WHITE + "- Clear the chat for all players");
                }

                if (player.hasPermission("chatmanager.command.reload")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager reload " + ChatColor.WHITE + "- Reloads all configuration files");
                }

                if (player.hasPermission("chatmanager.command.help")) {
                    player.sendMessage(ChatColor.BLUE + "/chatmanager help [commandName] " + ChatColor.WHITE + "- Provides help for ChatManager commands");
                }

                return true;
            }

            if (strings.length == 2) {

                if (strings[1].equalsIgnoreCase("gui")) {

                    if (player.hasPermission("chatmanager.command.config")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager gui - Opens the main configuration GUI");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("config")) {

                    if (player.hasPermission("chatmanager.command.config")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager config - Configure settings");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config setChatFilter [true/false]");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config setRepetitiveMessageFilter [true/false]");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config setURLFilter [true/false]");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config setNotifyStaff [true/false]");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config addBlockedWord [word]");
                        player.sendMessage(ChatColor.BLUE + "/chatmanager config removeBlockedWord [word]");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("logs")) {

                    if (player.hasPermission("chatmanager.command.logs")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager logs [page] - Opens the logs GUI for the given page");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("clearchat")) {

                    if (player.hasPermission("chatmanager.command.clearchat")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager clearchat - Clears the chat for all players");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("reload")) {

                    if (player.hasPermission("chatmanager.command.reload")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager reload - Reloads all configuration files");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("help")) {

                    if (player.hasPermission("chatmanager.command.help")) {
                        player.sendMessage(prefix + ChatColor.GREEN + "/chatmanager help [commandName] - Shows help for ChatManager commands");
                    } else {
                        player.sendMessage(prefix + noPermissionMessage);
                    }

                    return true;
                }

                player.sendMessage(prefix + ChatColor.RED + "Unknown command! Usage: /chatmanager help [commandName]");

                return true;
            }
        }

        if (strings.length == 1) {

            if (strings[0].equalsIgnoreCase("reload")) {

                if (!player.hasPermission("chatmanager.command.reload")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                    if (onlinePlayer.hasMetadata("configMenu")) {
                        onlinePlayer.closeInventory();
                        onlinePlayer.removeMetadata("configMenu", ChatManager.getInstance());
                    }

                    if (onlinePlayer.hasMetadata("logsMenu")) {
                        onlinePlayer.closeInventory();
                        onlinePlayer.removeMetadata("logsMenu", ChatManager.getInstance());
                    }

                    if (onlinePlayer.hasMetadata("noLogsMenu")) {
                        onlinePlayer.closeInventory();
                        onlinePlayer.removeMetadata("noLogsMenu", ChatManager.getInstance());
                    }
                }

                ChatManagerSettings.getInstance().reload();
                ChatManagerLogsSettings.getInstance().reload();
                player.sendMessage(prefix + ChatColor.GREEN + "The configuration file was reloaded!");

                return true;
            }

            if (strings[0].equalsIgnoreCase("gui")) {

                if (!player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                ChatManagerGUI.openGUI(player);

                return true;
            }

            if (strings[0].equalsIgnoreCase("config")) {

                if (!player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                ChatManagerGUI.openGUI(player);

                return true;
            }

            if (strings[0].equalsIgnoreCase("logs")) {

                if (!player.hasPermission("chatmanager.command.logs")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                ChatManagerLogsGUI.openGUI(player, 1);

                return true;
            }

            if (strings[0].equalsIgnoreCase("clearchat")) {

                if (!player.hasPermission("chatmanager.command.clearchat")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                    for (int i = 0; i < 100; i++) {
                        onlinePlayer.sendMessage("");
                    }

                    onlinePlayer.sendMessage(prefix + ChatColor.GREEN + "Chat was cleared by " + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + "!");
                }

                return true;
            }

            player.sendMessage(prefix + usageMessage);

            return true;
        }

        if (strings.length == 2) {

            if (strings[0].equalsIgnoreCase("config")) {

                if (!player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setChatFilter")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setChatFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setRepetitiveMessageFilter")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setRepetitiveMessageFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setURLFilter")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setURLFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setNotifyStaff")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setNotifyStaff [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("addBlockedWord")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config addBlockedWord [word]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("removeBlockedWord")) {
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config removeBlockedWord [word]");

                    return true;
                }

                player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config [setting] [true/false]");

                return true;
            }

            if (strings[0].equalsIgnoreCase("logs")) {

                if (!player.hasPermission("chatmanager.command.logs")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                int page;

                try {
                    page = Integer.parseInt(strings[1]);

                    if (page < 1) page = 1;

                    int slotsPerPage = 28;
                    int totalLogs = ChatManagerLogsSettings.getInstance().getTotalLogsCount();
                    int totalPages = Math.max(1, (int) Math.ceil((double) totalLogs / slotsPerPage));

                    if (page > totalPages) {
                        player.sendMessage(prefix + ChatColor.RED + "Invalid page number! Usage: /chatmanager logs [pageNumber]");

                        return true;
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(prefix + ChatColor.RED + "Invalid page number! Usage: /chatmanager logs [pageNumber]");

                    return true;
                }

                ChatManagerLogsGUI.openGUI(player, page);

                return true;
            }

            player.sendMessage(prefix + usageMessage);

            return true;
        }

        if (strings.length == 3) {

            if (strings[0].equalsIgnoreCase("config")) {

                if (!player.hasPermission("chatmanager.command.config")) {
                    player.sendMessage(prefix + noPermissionMessage);

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setChatFilter")) {

                    if (strings[2].equalsIgnoreCase("true")) {

                        if (ChatManagerSettings.getInstance().getChatFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "Chat Filter is already enabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setChatFilter(true);
                        player.sendMessage(prefix + ChatColor.GREEN + "Chat Filter has been enabled!");

                        return true;
                    }

                    if (strings[2].equalsIgnoreCase("false")) {

                        if (!ChatManagerSettings.getInstance().getChatFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "Chat Filter is already disabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setChatFilter(false);
                        player.sendMessage(prefix + ChatColor.GREEN + "Chat Filter has been disabled!");

                        return true;
                    }

                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setChatFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setRepetitiveMessageFilter")) {

                    if (strings[2].equalsIgnoreCase("true")) {

                        if (ChatManagerSettings.getInstance().getRepetitiveMessageFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "Repetitive Message Filter is already enabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setRepetitiveMessageFilter(true);
                        player.sendMessage(prefix + ChatColor.GREEN + "Repetitive Message Filter has been enabled!");

                        return true;
                    }

                    if (strings[2].equalsIgnoreCase("false")) {

                        if (!ChatManagerSettings.getInstance().getRepetitiveMessageFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "Repetitive Message Filter is already disabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setRepetitiveMessageFilter(false);
                        player.sendMessage(prefix + ChatColor.GREEN + "Repetitive Message Filter has been disabled!");

                        return true;
                    }

                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setRepetitiveMessageFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setURLFilter")) {

                    if (strings[2].equalsIgnoreCase("true")) {

                        if (ChatManagerSettings.getInstance().getUrlFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "URL Filter is already enabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setUrlFilter(true);
                        player.sendMessage(prefix + ChatColor.GREEN + "URL Filter has been enabled!");

                        return true;
                    }

                    if (strings[2].equalsIgnoreCase("false")) {

                        if (!ChatManagerSettings.getInstance().getUrlFilter()) {
                            player.sendMessage(prefix + ChatColor.RED + "URL Filter is already disabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setUrlFilter(false);
                        player.sendMessage(prefix + ChatColor.GREEN + "URL Filter has been disabled!");

                        return true;
                    }

                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setURLFilter [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("setNotifyStaff")) {

                    if (strings[2].equalsIgnoreCase("true")) {

                        if (ChatManagerSettings.getInstance().getNotifyStaff()) {
                            player.sendMessage(prefix + ChatColor.RED + "Notify Staff is already enabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setNotifyStaff(true);
                        player.sendMessage(prefix + ChatColor.GREEN + "Notify Staff has been enabled!");

                        return true;
                    }

                    if (strings[2].equalsIgnoreCase("false")) {

                        if (!ChatManagerSettings.getInstance().getNotifyStaff()) {
                            player.sendMessage(prefix + ChatColor.RED + "Notify Staff is already disabled!");

                            return true;
                        }

                        ChatManagerSettings.getInstance().setNotifyStaff(false);
                        player.sendMessage(prefix + ChatColor.GREEN + "Notify Staff has been disabled!");

                        return true;
                    }

                    player.sendMessage(prefix + ChatColor.RED + "Usage: /chatmanager config setNotifyStaff [true/false]");

                    return true;
                }

                if (strings[1].equalsIgnoreCase("addBlockedWord")) {
                    if (ChatManagerSettings.getInstance().addBlockedWord(strings[2])) {
                        player.sendMessage(prefix + ChatColor.GREEN + "The word \"" + strings[2] + "\"" + " has been added to the chat filter!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The word \"" + strings[2] + "\"" + " is already in the chat filter!");
                    }

                    return true;
                }

                if (strings[1].equalsIgnoreCase("removeBlockedWord")) {

                    if (ChatManagerSettings.getInstance().removeBlockedWord(strings[2])) {
                        player.sendMessage(prefix + ChatColor.GREEN + "The word \"" + strings[2] + "\"" + " has been removed from the chat filter!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The word \"" + strings[2] + "\"" + " is not in the chat filter!");
                    }

                    return true;
                }
            }

            player.sendMessage(prefix + usageMessage);

            return true;
        }

        boolean hasChatManagerPermission =
                player.hasPermission("chatmanager.command.config") ||
                        player.hasPermission("chatmanager.command.help") ||
                        player.hasPermission("chatmanager.command.logs") ||
                        player.hasPermission("chatmanager.command.clearchat") ||
                        player.hasPermission("chatmanager.command.reload");

        if (!hasChatManagerPermission) {
            player.sendMessage(prefix + noPermissionMessage);

            return true;
        }

        player.sendMessage(prefix + usageMessage);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {

            return new ArrayList<>();
        }

        if (strings.length == 1) {

            List<String> options = new ArrayList<>();


            if ("reload".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.reload")) {
                options.add("reload");
            }

            if ("gui".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.config")) {
                options.add("gui");
            }

            if ("config".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.config")) {
                options.add("config");
            }

            if ("help".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.help")) {
                options.add("help");
            }

            if ("logs".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.logs")) {
                options.add("logs");
            }


            if ("clearchat".startsWith(strings[0].toLowerCase()) && player.hasPermission("chatmanager.command.clearchat")) {
                options.add("clearchat");
            }

            return options;
        }

        if (strings.length == 2 && strings[0].equalsIgnoreCase("config")) {

            if (player.hasPermission("chatmanager.command.config")) {

                List<String> options = new ArrayList<>();

                if ("setchatfilter".startsWith(strings[1].toLowerCase())) {
                    options.add("setChatFilter");
                }

                if ("setrepetitivemessagefilter".startsWith(strings[1].toLowerCase())) {
                    options.add("setRepetitiveMessageFilter");
                }

                if ("seturlfilter".startsWith(strings[1].toLowerCase())) {
                    options.add("setURLFilter");
                }

                if ("setnotifystaff".startsWith(strings[1].toLowerCase())) {
                    options.add("setNotifyStaff");
                }

                if ("addblockedword".startsWith(strings[1].toLowerCase())) {
                    options.add("addBlockedWord");
                }

                if ("removeblockedword".startsWith(strings[1].toLowerCase())) {
                    options.add("removeBlockedWord");
                }

                return options;
            }
        }

        if (strings.length == 2 && strings[0].equalsIgnoreCase("logs")) {

            if (player.hasPermission("chatmanager.command.logs")) {
                List<String> options = new ArrayList<>();

                int slotsPerPage = 28;
                int totalLogs = ChatManagerLogsSettings.getInstance().getTotalLogsCount();
                int totalPages = Math.max(1, (int) Math.ceil((double) totalLogs / slotsPerPage));

                for (int i = 1; i <= totalPages; i++) {
                    options.add(String.valueOf(i));
                }

                options.removeIf(option -> !option.startsWith(strings[1]));

                return options;
            }
        }

        if (strings.length == 2 && strings[0].equalsIgnoreCase("help")) {

            if (player.hasPermission("chatmanager.command.help")) {
                List<String> options = new ArrayList<>();

                if ("gui".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.config")) {
                    options.add("gui");
                }

                if ("config".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.config")) {
                    options.add("config");
                }

                if ("logs".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.logs")) {
                    options.add("logs");
                }

                if ("clearchat".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.clearchat")) {
                    options.add("clearchat");
                }

                if ("reload".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.reload")) {
                    options.add("reload");
                }

                if ("help".startsWith(strings[1].toLowerCase()) && player.hasPermission("chatmanager.command.help")) {
                    options.add("help");
                }

                return options;
            }
        }

        if (strings.length == 3 && strings[0].equalsIgnoreCase("config")) {

            if (player.hasPermission("chatmanager.command.config")) {

                if (strings[1].equalsIgnoreCase("setChatFilter") ||
                        strings[1].equalsIgnoreCase("setRepetitiveMessageFilter") ||
                        strings[1].equalsIgnoreCase("setURLFilter") ||
                        strings[1].equalsIgnoreCase("setNotifyStaff")) {

                    List<String> options = new ArrayList<>();

                    if ("true".startsWith(strings[2].toLowerCase())) {
                        options.add("true");
                    }

                    if ("false".startsWith(strings[2].toLowerCase())) {
                        options.add("false");
                    }

                    return options;
                }
            }

            if (strings[1].equalsIgnoreCase("removeBlockedWord")) {

                if (player.hasPermission("chatmanager.command.config")) {

                    return new ArrayList<>(ChatManagerSettings.getInstance().getBlockedWords());
                }
            }
        }

        return new ArrayList<>();
    }
}
