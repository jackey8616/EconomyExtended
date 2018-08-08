package tw.at.clo5de.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import tw.at.clo5de.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private Plugin plugin = null;
    private FileConfiguration config = null;

    public ArrayList<Currency> currencys = new ArrayList<Currency>();

    public ConfigManager (Plugin plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
    }

    public void loadConfig () {
        this.loadCurrency();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    private boolean loadCurrency () {
        this.config.addDefault("KycCurrency", Arrays.asList());
        List list = this.config.getList("KycCurrency");
        if (list.size() == 0) {

        } else {
            for (int i = 0; i < list.size(); ++i) {
                this.currencys.add(new Currency((Map) list.get(i)));
            }
        }
        return false;
    }

}
