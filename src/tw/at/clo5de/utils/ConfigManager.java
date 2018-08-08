package tw.at.clo5de.utils;

import com.kunyihua.crafte.api.Bukkit.BukkitKyctAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import tw.at.clo5de.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> list = (List<String>)this.config.getList("KycCurrency");
        if (list.size() == 0) {

        } else {
            BukkitKyctAPI kycAPI = new BukkitKyctAPI();
            for (String str : list) {
                ItemStack thisStack = kycAPI.getItemByItemKey(str),
                          nextStack = kycAPI.getItemByItemKey(this.config.getString("KycCurrency." + str + ".NextCurrency")),
                          prevStack = kycAPI.getItemByItemKey(this.config.getString("KycCurrency." + str + ".PrevCurrency"));
                this.currencys.add(new Currency(thisStack, nextStack, prevStack));
            }
        }
        return false;
    }

}
