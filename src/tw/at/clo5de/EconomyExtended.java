package tw.at.clo5de;

import org.bukkit.plugin.java.JavaPlugin;
import tw.at.clo5de.invoke.EssentialsInvoke;
import tw.at.clo5de.invoke.KycraftInvoke;
import tw.at.clo5de.invoke.VaultInvoke;
import tw.at.clo5de.metrics.Metrics;
import tw.at.clo5de.utils.ConfigManager;

import java.util.logging.Logger;

public class EconomyExtended extends JavaPlugin {

    public static JavaPlugin INSTANCE;
    public static final Logger logger = Logger.getLogger("Minecraft");

    public static VaultInvoke vaultInvoke = new VaultInvoke();
    public static EssentialsInvoke essentialsInvoke = new EssentialsInvoke();
    public static KycraftInvoke kycraftInvoke = new KycraftInvoke();
    public static ConfigManager configManager = null;
    public static tw.at.clo5de.currency.Handler currencyHandler = null;
    // Still developing of trading system.
    public static tw.at.clo5de.trade.Handler tradeHandler = null;

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
