package tw.at.clo5de;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tw.at.clo5de.metrics.Metrics;
import tw.at.clo5de.player.Handler;
import tw.at.clo5de.utils.ConfigManager;

import java.util.logging.Logger;

public class EconomyExtended extends JavaPlugin {

    public static EconomyExtended INSTANCE;
    private final Logger logger = Logger.getLogger("Minecraft");
    private Permission permission = null;
    private Economy economy = null;

    private ConfigManager configManager = null;
    private Handler currencyHandler = null;

    @Override
    public void onEnable () {
        Metrics metrics = new Metrics(this);
        INSTANCE = this;
        if (getServer().getPluginManager().getPlugin("Kycraft") == null) {
            logger.warning("Kycraft is needed! Please make sure your server install it.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            setConfigManager();
            configManager.loadConfig();
        }


        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            logger.warning("Can not detect Vault, Please make sure you do install a Economy plugin support Vault.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            logger.info("Vault detected, Try to load Vault Economy plugin.");
            setEconomy();
            setPermission();
        }

    }

    @Override
    public void onDisable () {  }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String cmdLbl, String[] args) {
        return false;
    }

    // Custom Methods
    public Logger _getLogger () {
        return logger;
    }

    public Economy getEconomy () {
        return economy;
    }

    private boolean setEconomy () {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public Permission getPermission () {
        return permission;
    }

    private boolean setPermission () {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        permission = rsp.getProvider();
        return permission != null;
    }

    public ConfigManager getConfigManager () {
        return configManager;
    }

    private boolean setConfigManager () {
        configManager =  new ConfigManager(this);
        return configManager != null;
    }

    public Handler getCurrencyHandler() {
        return currencyHandler;
    }

    public boolean setCurrencyHandler (MemorySection currencyConfig) {
        currencyHandler = new Handler(currencyConfig);
        return currencyHandler != null;
    }
}
