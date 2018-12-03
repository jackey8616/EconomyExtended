package info.clo5de.economyex.invoke;

import info.clo5de.economyex.EconomyExtended;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;

import static org.bukkit.Bukkit.getServer;

public class EssentialsInvoke implements Listener {

    public EssentialsInvoke () {  }

    public void invoke () {
        if (getServer().getPluginManager().getPlugin("Essentials") == null) {
            EconomyExtended.logger.warning("Can not detect Essentials, Please make sure you do install it.");
            getServer().getPluginManager().disablePlugin(EconomyExtended.INSTANCE);
        } else {
            EconomyExtended.INSTANCE.getLogger().info("Detected Essentials, Invoked with events.");
            getServer().getPluginManager().registerEvents(this, EconomyExtended.INSTANCE);
        }
    }

    @EventHandler
    public void onPlayerBalanceUpdate (UserBalanceUpdateEvent event) {
        if (event.getPlayer().isOnline()) {
            BigDecimal invBalance = new BigDecimal(EconomyExtended.currencyHandler.calculateInventoryCurrency(event.getPlayer().getInventory()));
            BigDecimal balance = event.getNewBalance();
            int compare = invBalance.compareTo(balance);
            if (compare < 0) {
                EconomyExtended.currencyHandler.givePlayerMissedCurrency(event.getPlayer(), balance.subtract(invBalance).abs().longValue());
            } else if (compare > 0) {
                EconomyExtended.currencyHandler.removePlayerSurplusCurrency(event.getPlayer(), balance.longValue());
                // EconomyExtended.INSTANCE.getCurrencyHandler().removePlayerSurplusCurrency(event.getPlayer(), balance.subtract(invBalance).abs().longValue());
            }
        }
    }

}
