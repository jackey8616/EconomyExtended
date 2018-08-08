package tw.at.clo5de;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tw.at.clo5de.utils.ConfigManager;

import java.util.logging.Logger;

public class TraditionalEconomy extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Permission permission = null;
    private static Economy economy = null;

    private static ConfigManager configManager = null;

    @Override
    public void onEnable () {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            log.info(String.format("Vault Detected!"));
            setEconomy();
            setPermission();
            setConfigManager();

            configManager.loadConfig();
        } else {
            log.warning(String.format("Can not detect Valut, please make sure you do install Valut!"));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable () {  }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String cmdLbl, String[] args) {
        return false;
    }

    // Custom Methods
    public static Economy getEconomy () {
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

    public static Permission getPermission () {
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

    public static ConfigManager getConfigManager () {
        return configManager;
    }

    private boolean setConfigManager () {
        configManager =  new ConfigManager(this);
        return configManager != null;
    }
}
