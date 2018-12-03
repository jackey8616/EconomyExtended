package info.clo5de.economyex;

import info.clo5de.economyex.currency.Handler;
import info.clo5de.economyex.invoke.KycraftInvoke;
import info.clo5de.economyex.invoke.VaultInvoke;
import info.clo5de.economyex.metrics.Metrics;
import info.clo5de.economyex.utils.ConfigManager;
import info.clo5de.economyex.invoke.EssentialsInvoke;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class EconomyExtended extends JavaPlugin {

    public static JavaPlugin INSTANCE;
    public static final Logger logger = Logger.getLogger("Minecraft");

    public static VaultInvoke vaultInvoke = new VaultInvoke();
    public static EssentialsInvoke essentialsInvoke = new EssentialsInvoke();
    public static KycraftInvoke kycraftInvoke = new KycraftInvoke();
    public static ConfigManager configManager = null;
    public static Handler currencyHandler = null;
    // Still developing of trading system.
    public static info.clo5de.economyex.trade.Handler tradeHandler = null;

    @Override
    public void onEnable () {
        Metrics metrics = new Metrics(this);
        INSTANCE = this;
        // Invoke of every depend plugin
        vaultInvoke.invoke();
        essentialsInvoke.invoke();
        kycraftInvoke.invoke();
        // Config Init
        configManager =  new ConfigManager(this);
        configManager.loadConfig();
        currencyHandler = configManager.loadCurrencyConfig();
    }

    @Override
    public void onDisable () {  }

}
