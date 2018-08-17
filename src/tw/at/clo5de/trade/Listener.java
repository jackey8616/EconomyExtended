package tw.at.clo5de.trade;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import tw.at.clo5de.EconomyExtended;

import static org.bukkit.Bukkit.getServer;

public class Listener implements org.bukkit.event.Listener  {

    private Handler handler = null;

    public Listener (Handler handler) {
        this.handler = handler;
        getServer().getPluginManager().registerEvents(this, EconomyExtended.INSTANCE);
    }


    @EventHandler
    public void onPlayerInteracte (PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && event.getPlayer().isSneaking()) {
            if (this.handler.trading.contains(event.getPlayer())) {
                event.getPlayer().sendMessage("You are already in trading.");
            } else if (this.handler.trading.contains((Player)  event.getRightClicked())) {
                event.getPlayer().sendMessage("That player is trading.");
            } else {
                if (this.handler.askingTrade.keySet().contains(event.getPlayer())) {
                    Trade t = this.handler.askingTrade.get(event.getPlayer());
                    t.response();
                } else {
                    Player asker = event.getPlayer();
                    Player taker = (Player) event.getRightClicked();
                    Trade t = new Trade(handler, asker, taker, 100);
                    this.handler.askingTrade.put(taker, t);
                    t.sendInvite();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerClickInventory (InventoryClickEvent event) {
        Trade t = this.handler.isTradeInv(event.getInventory());
        if (t != null) {
            t.handlClickEvent(event);
        }
    }


}
