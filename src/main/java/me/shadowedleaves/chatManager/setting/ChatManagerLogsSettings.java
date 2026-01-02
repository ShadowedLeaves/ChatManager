package me.shadowedleaves.chatManager.setting;

import me.shadowedleaves.chatManager.ChatManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ChatManagerLogsSettings {

    private final static ChatManagerLogsSettings instance = new ChatManagerLogsSettings();

    private File logsFile;
    private YamlConfiguration logsConfig;

    private ChatManagerLogsSettings() {
    }

    public void load(){
        logsFile = new File(ChatManager.getInstance().getDataFolder(), "logs.yml");

        if (!logsFile.exists()) {
            ChatManager.getInstance().saveResource("logs.yml", false);
        }

        logsConfig = new YamlConfiguration();
        logsConfig.options().parseComments(true);

        try {
            logsConfig.load(logsFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reload() {
        load();
    }

    public void save() {
        try {
            logsConfig.save(logsFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        logsConfig.set(path, value);

        save();
    }

    public Set<String> getLogs() {
        ConfigurationSection section = logsConfig.getConfigurationSection("logs");

        if (section == null) {
            return Collections.emptySet();
        }

        return section.getKeys(false);
    }

    public void addLog(String username, String message) {
        String id = UUID.randomUUID().toString();

        set("logs." + username + "." + id, message);

        save();
    }

    public Map<String, String> getMessages(String username) {
        ConfigurationSection section = logsConfig.getConfigurationSection("logs." + username);

        if (section == null) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new LinkedHashMap<>();

        for (String id : section.getKeys(false)) {
            result.put(id, section.getString(id));
        }

        return result;
    }

    public int getTotalLogsCount() {
        int count = 0;

        for (String username : getLogs()) {
            count += getMessages(username).size();
        }

        return count;
    }

    public void clearLogs() {
        set("logs", null);
        set("logs", new LinkedHashMap<>());

        save();
    }

    public void removeMessage(String username, String uuid) {
        ConfigurationSection section = logsConfig.getConfigurationSection("logs." + username);

        if (section == null || !section.contains(uuid)) return;

        set("logs." + username + "." + uuid, null);

        save();
    }

    public void removePlayer(String username) {
        if (!logsConfig.contains("logs." + username)) return;

        set("logs." + username, null);

        save();
    }

    public static ChatManagerLogsSettings getInstance() {

        return instance;
    }
}