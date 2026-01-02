package me.shadowedleaves.chatManager.setting;

import me.shadowedleaves.chatManager.ChatManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatManagerSettings {

    private final static ChatManagerSettings instance = new ChatManagerSettings();

    private File configFile;
    private YamlConfiguration config;

    private String prefix;

    private String noPermissionMessage;
    private String usageMessage;

    private String blockedMessage;
    private String repetitiveMessage;
    private String urlMessage;
    private String staffNotifyMessage;

    private boolean chatFilter;
    private boolean repetitiveMessageFilter;
    private boolean urlFilter;
    private boolean notifyStaff;

    private List<String> blockedWords;

    private ChatManagerSettings() {
    }

    public void load(){
        configFile = new File(ChatManager.getInstance().getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            ChatManager.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        prefix = config.getString("prefix", "§7[§9ChatManager§7]§r ");

        noPermissionMessage = config.getString("no-permission-message", "§cYou do not have permission to use this command!");
        usageMessage = config.getString("usage-message", "§cUsage: /chatmanager [config/gui/help/logs/clearchat/reload]");

        blockedMessage = config.getString("blocked-message", "§cError: Your message contained filtered content and was not sent!");
        staffNotifyMessage = config.getString("notify-message", "§cA message sent by §4{player}§c was blocked: \"§4{message}§c\"!");
        repetitiveMessage = config.getString("repetitive-message", "§cError: Your message cannot be the same as your last message!");
        urlMessage = config.getString("url-message", "§cError: Your message cannot contain a URL!");

        chatFilter = config.getBoolean("chat-filter.enabled", true);
        repetitiveMessageFilter = config.getBoolean("repetitive-message-filter.enabled", true);
        urlFilter = config.getBoolean("url-filter.enabled", true);
        notifyStaff = config.getBoolean("notify-staff.enabled", true);

        blockedWords = new ArrayList<>(
                config.getStringList("blocked-words")
                        .stream()
                        .map(String::toLowerCase)
                        .toList());
    }

    public void reload() {
        load();
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getUsageMessage() {
        return usageMessage;
    }

    public String getStaffNotifyMessage() {
        return staffNotifyMessage;
    }

    public String getBlockedMessage() {
        return blockedMessage;
    }

    public String getRepetitiveMessage() {
        return repetitiveMessage;
    }

    public String getUrlMessage() {
        return urlMessage;
    }

    public boolean getChatFilter() {
        return chatFilter;
    }

    public boolean getRepetitiveMessageFilter() {
        return repetitiveMessageFilter;
    }

    public boolean getUrlFilter() {
        return urlFilter;
    }

    public boolean getNotifyStaff() {
        return notifyStaff;
    }

    public List<String> getBlockedWords() {
        return blockedWords;
    }

    public void setChatFilter(boolean chatFilter) {
        this.chatFilter = chatFilter;
        set("chat-filter.enabled", chatFilter);
    }

    public void setRepetitiveMessageFilter(boolean repetitiveMessageFilter) {
        this.repetitiveMessageFilter = repetitiveMessageFilter;
        set("repetitive-message-filter.enabled", repetitiveMessageFilter);
    }

    public void setUrlFilter(boolean urlFilter) {
        this.urlFilter = urlFilter;
        set("url-filter.enabled", urlFilter);
    }

    public void setNotifyStaff(boolean notifyStaff) {
        this.notifyStaff = notifyStaff;
        set("notify-staff.enabled", notifyStaff);
    }

    public boolean addBlockedWord(String word) {
        if (word == null || word.isBlank()) return false;

        word = word.toLowerCase();

        if (blockedWords.contains(word)) return false;

        blockedWords.add(word);
        config.set("blocked-words", blockedWords);

        save();

        return true;
    }

    public boolean removeBlockedWord(String word) {
        if (word == null || word.isBlank()) return false;

        word = word.toLowerCase();

        if (!blockedWords.remove(word)) return false;

        config.set("blocked-words", blockedWords);

        save();

        return true;
    }

    public static ChatManagerSettings getInstance() {

        return instance;
    }
}
