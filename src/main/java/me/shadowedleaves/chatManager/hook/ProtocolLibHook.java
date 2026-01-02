package me.shadowedleaves.chatManager.hook;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.shadowedleaves.chatManager.ChatManager;
import me.shadowedleaves.chatManager.setting.ChatManagerLogsSettings;
import me.shadowedleaves.chatManager.setting.ChatManagerSettings;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProtocolLibHook {

    private static final Map<Player, String> lastMessages = new HashMap<>();

    public static void register() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(new PacketAdapter(ChatManager.getInstance(), PacketType.Play.Client.CHAT) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                String message = packet.getStrings().read(0);

                String lastMessage = lastMessages.get(player);

                if (player.hasPermission("chatmanager.bypass")) {

                    return;
                }

                if (ChatManagerSettings.getInstance().getChatFilter()) {

                    for (String word : ChatManagerSettings.getInstance().getBlockedWords()) {

                        if (word.isEmpty()) continue;

                        if (message.toLowerCase().contains(word)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatManagerSettings.getInstance().getBlockedMessage());
                            notifyStaff(player, message);
                            ChatManagerLogsSettings.getInstance().addLog(player.getName(), message);

                            lastMessages.put(player, message);

                            return;
                        }
                    }
                }

                if (ChatManagerSettings.getInstance().getRepetitiveMessageFilter()) {

                    if (lastMessage != null && lastMessage.equalsIgnoreCase(message)) {
                        event.setCancelled(true);
                        player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatManagerSettings.getInstance().getRepetitiveMessage());

                        lastMessages.put(player, message);

                        return;
                    }
                }

                if (ChatManagerSettings.getInstance().getUrlFilter()) {

                    if (URL.matcher(message).find()) {
                        event.setCancelled(true);
                        player.sendMessage(ChatManagerSettings.getInstance().getPrefix() + ChatManagerSettings.getInstance().getUrlMessage());

                        lastMessages.put(player, message);

                        return;
                    }
                }

                lastMessages.put(player, message);
            }
        });
    }

    private static void notifyStaff(Player player, String message) {

        if (!ChatManagerSettings.getInstance().getNotifyStaff()) return;

        for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {

            if (onlinePlayer.hasPermission("chatmanager.notify")) {
                onlinePlayer.sendMessage(
                        ChatManagerSettings.getInstance().getPrefix()
                                + ChatManagerSettings.getInstance().getStaffNotifyMessage()
                                .replace("{player}", player.getName())
                                .replace("{message}", message));
            }
        }
    }

    private static final Pattern URL = Pattern.compile(
            "(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", Pattern.CASE_INSENSITIVE);
}
