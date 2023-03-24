package me.benwithjamin.mccommons.confighandler;

import me.benwithjamin.mccommons.confighandler.errors.ConfigTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Project: me.benwithjamin.mccommons | Author: BenWithJamIn#4547
 * Created: 24/03/2023 at 22:36
 */

/**
 * Represents data parsed from YAML file
 */
public class ConfigSection {
    private final Map<String, Object> data;

    /**
     * @param data The data
     */
    public ConfigSection(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Get a string from the config file
     *
     * @param key The key of the string
     *
     * @return The string value
     */
    public String getString(String key) {
        try {
            return (String) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a string");
        }
    }

    /**
     * Get an int from the config file
     *
     * @param key The key of the int
     *
     * @return The int value
     */
    public int getInt(String key) {
        try {
            return (int) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a int");
        }
    }

    /**
     * Get a double from the config file
     *
     * @param key The key of the double
     *
     * @return The double value
     */
    public double getDouble(String key) {
        try {
            return (double) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a double");
        }
    }

    /**
     * Get a long from the config file
     *
     * @param key The key of the long
     *
     * @return The long value
     */
    public long getLong(String key) {
        try {
            return (long) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a long");
        }
    }

    /**
     * Get a boolean from the config file
     *
     * @param key The key of the boolean
     *
     * @return The boolean value
     */
    public boolean getBoolean(String key) {
        try {
            return (boolean) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a boolean");
        }
    }

    /**
     * Get a section from the config file
     *
     * @param key The key of the section
     *
     * @return The section value
     */
    @SuppressWarnings("unchecked")
    public ConfigSection getSection(String key) {
        try {
            return new ConfigSection((Map<String, Object>) this.data.get(key));
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not a config section");
        }
    }

    /**
     * Get a list from the config file
     *
     * @param key The key of the list
     *
     * @return The list value
     */
    public List<?> getList(String key) {
        try {
            return (List<?>) this.data.get(key);
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not an array");
        }
    }

    /**
     * Get a list of strings from the config file
     *
     * @param key The key of the list
     * @param clazz The type of the list
     *
     * @return The list value
     */
    public <T> List<T> getTypeList(String key, Class<T> clazz) {
        try {
            List<T> stringList = new ArrayList<>();
            List<?> list = (List<?>) this.data.get(key);
            for (Object o : list) {
                if (!clazz.isInstance(o)) {
                    throw new ConfigTypeException("Config value " + key + " is not an array of strings");
                }
                stringList.add(clazz.cast(o));
            }
            return stringList;
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not an array");
        }
    }

    /**
     * Get a list of sections from the config file
     *
     * @param key The key of the list
     *
     * @return The list value
     */
    @SuppressWarnings("unchecked")
    public List<ConfigSection> getSectionList(String key) {
        try {
            List<ConfigSection> sectionList = new ArrayList<>();
            List<?> list = (List<?>) this.data.get(key);
            for (Object o : list) {
                if (!(o instanceof Map)) {
                    throw new ConfigTypeException("Config value " + key + " is not an array of config sections");
                }
                sectionList.add(new ConfigSection((Map<String, Object>) o));
            }
            return sectionList;
        } catch (ClassCastException e) {
            throw new ConfigTypeException("Config value " + key + " is not an array");
        }
    }
}
