package tw.at.clo5de.utils;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import tw.at.clo5de.EconomyExtended;

public class ConfigManager {

    private EconomyExtended plugin = null;
    private FileConfiguration config = null;

    public boolean debug = false;

    public ConfigManager (EconomyExtended plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
    }

    public void loadConfig () {
        this.debug = this.config.getBoolean("Setting.Debug");
        this.loadCurrency();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    private boolean loadCurrency () {
        if (this.config.get("Currency") == null) {
            EconomyExtended.INSTANCE._getLogger().warning("Can not find Currency setting in config.");
            return false;
        }
        this.plugin.setCurrencyHandler((MemorySection) this.config.get("Currency"));
        return EconomyExtended.INSTANCE.getCurrencyHandler() != null;
    }

}
