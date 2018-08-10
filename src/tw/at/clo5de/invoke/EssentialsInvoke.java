package tw.at.clo5de.invoke;

import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tw.at.clo5de.EconomyExtended;

import java.math.BigDecimal;

import static org.bukkit.Bukkit.getServer;

public class EssentialsInvoke implements Listener {

    public EssentialsInvoke () {
        if (getServer().getPluginManager().getPlugin("Essentials") != null) {
            EconomyExtended.INSTANCE.getLogger().info("Detected Essentials, Invoked with events.");
            getServer().getPluginManager().registerEvents(this, EconomyExtended.INSTANCE);
        }
    }

    @EventHandler
    public void onPlayerBalanceUpdate(UserBalanceUpdateEvent event) {
        BigDecimal invBalance = new BigDecimal(EconomyExtended.INSTANCE.getCurrencyHandler().calculateInventoryCurrency(event.getPlayer().getInventory()));
        BigDecimal balance = event.getNewBalance();
        int compare = invBalance.compareTo(balance);
        if (compare < 0) {
            EconomyExtended.INSTANCE.getCurrencyHandler().givePlayerMissedCurrency(event.getPlayer(), balance.subtract(invBalance).abs().longValue());
        } else if (compare > 0) {
            EconomyExtended.INSTANCE.getCurrencyHandler().removePlayerSurplusCurrency(event.getPlayer(), balance.subtract(invBalance).abs().longValue());
        }
    }

}
