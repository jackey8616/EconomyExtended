package info.clo5de.economyex.utils;

import info.clo5de.economyex.EconomyExtended;
import info.clo5de.economyex.currency.Handler;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

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
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    public Handler loadCurrencyConfig () {
        if (this.config.get("Currency") == null) {
            EconomyExtended.logger.warning("Can not find Currency setting in config.");
            return null;
        }
        return new Handler((MemorySection) this.config.get("Currency"));
    }

}
