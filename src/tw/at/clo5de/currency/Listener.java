package tw.at.clo5de.currency;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tw.at.clo5de.EconomyExtended;

import static org.bukkit.Bukkit.getServer;

public class Listener implements org.bukkit.event.Listener {

    private Handler playerHandler = null;

    public Listener (Handler handler) {
        this.playerHandler = handler;
        getServer().getPluginManager().registerEvents(this, EconomyExtended.INSTANCE);
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(EconomyExtended.INSTANCE, () -> {
            try {
                playerHandler.invokePlayerBalance((Player) event.getPlayer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5L);
    }

    @EventHandler
    public void onPlayerOpenInventory (InventoryOpenEvent event) {
        playerHandler.invokePlayerBalance((Player) event.getPlayer());
    }

    @EventHandler
    public void onPlayerCloseInventory (InventoryCloseEvent event) {
        playerHandler.invokePlayerBalance((Player) event.getPlayer());
    }

    @EventHandler
    public void onPlayerDropItem (PlayerDropItemEvent event) {
        if (playerHandler.chain.containCurrency(event.getItemDrop())) {
            playerHandler.invokePlayerBalance(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerPickItem (EntityPickupItemEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(EconomyExtended.INSTANCE, () -> {
            try {
                if (event.getEntity() instanceof Player && playerHandler.chain.containCurrency(event.getItem())) {
                    Player player = (Player) event.getEntity();
                    playerHandler.invokePlayerBalance(player);
                    if (playerHandler.autoConvert) {
                        playerHandler.convertCurrencies(player);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5L);//20L = 1 sec
    }

    @EventHandler
    public void onPlayerUseItem (PlayerInteractEvent event) {
        try {
            if (playerHandler.chain.containCurrency(event.getItem())) {
                playerHandler.invokePlayerBalance((Player) event.getPlayer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}