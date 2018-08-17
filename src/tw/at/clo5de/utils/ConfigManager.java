package tw.at.clo5de.utils;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import tw.at.clo5de.EconomyExtended;
import tw.at.clo5de.currency.Handler;

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
