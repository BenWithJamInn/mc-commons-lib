package me.benwithjamin.mccommons.confighandler;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

/*
 * Project: me.benwithjamin.mccommons | Author: BenWithJamIn#4547
 * Created: 24/03/2023 at 22:09
 */
public class Config {
    private final File configFile;
    @Getter private final ConfigSection rootSection;

    /**
     * Creates a new Config instance
     * A default file stream can be provided, if the file does not exist
     * This stream can be retrieved from JavaPlugin#getResource(String)
     *
     * @param fileName The name of the config file (yaml)
     * @param defaultConfig The default config file stream if the file does not exist, use null if not required
     */
    public Config(String fileName, @Nullable InputStream defaultConfig) throws IOException {
        this.configFile = new File(ConfigHandler.getConfigFolder() + fileName);
        // if config does not exist create new file
        if (!configFile.exists()) {
            if (defaultConfig != null) {
                // if default config stream is provided copy it to the new file
                try (OutputStream out = Files.newOutputStream(configFile.toPath())) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = defaultConfig.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
            } else {
                // if there is no default stream create new empty file
                boolean success = this.configFile.createNewFile();
                if (!success) {
                    throw new IOException("Failed to create new config file " + this.configFile.getPath());
                }
            }
        }
        // load config data
        Map<String, Object> data = ConfigHandler.getYaml().load(Files.newInputStream(this.configFile.toPath()));
        this.rootSection = new ConfigSection(data);
    }
}
