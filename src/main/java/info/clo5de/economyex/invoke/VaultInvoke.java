package info.clo5de.economyex.invoke;

import info.clo5de.economyex.EconomyExtended;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultInvoke {

    private Permission permission = null;
    private Economy economy = null;

    public VaultInvoke () {}

    public void invoke () {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            EconomyExtended.logger.warning("Can not detect Vault, Please make sure you do install a Economy plugin support Vault.");
            getServer().getPluginManager().disablePlugin(EconomyExtended.INSTANCE);
        } else {
            EconomyExtended.logger.info("Vault detected, Try to load Vault Economy plugin.");
            this.setEconomy();
            this.setPermission();
        }
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

}
