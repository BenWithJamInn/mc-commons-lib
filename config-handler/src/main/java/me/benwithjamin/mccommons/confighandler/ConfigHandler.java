package me.benwithjamin.mccommons.confighandler;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public class ConfigHandler {
    @Getter private static final Yaml yaml = new Yaml();
    @Getter private static File configFolder;

    /**
     * Initialise the config handler
     *
     * @param folder The folder to store the config files in
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init(File folder) {
        folder.mkdirs();
        configFolder = folder;
        Config config;
        try {
            config = new Config("config", null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}