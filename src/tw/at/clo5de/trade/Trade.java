package tw.at.clo5de.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import tw.at.clo5de.EconomyExtended;

import static org.bukkit.Bukkit.getServer;

public class Trade {

    private Handler handler = null;
    private Player asker, taker;
    private int counterId, expireCount = 0;
    private Inventory inv = null;

    public Trade (Handler handler, Player asker, Player taker, int expireCount) {
        this.handler = handler;
        this.asker = asker;
        this.taker = taker;
        this.expireCount = expireCount;
    }

    public void sendInvite () {
        this.taker.sendMessage("Player " + this.asker.getDisplayName() + " invite you with a trade.");
        this.counterId = getServer().getScheduler().scheduleSyncRepeatingTask(EconomyExtended.INSTANCE, () -> {
            try {
                if (expireCount != 0) {
                    expireCount--;
                } else {
                    this.asker.sendMessage("Player does not respond your trade request.");
                    getServer().getScheduler().cancelTask(this.counterId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0L, 20L);//20L = 1 sec
    }

    public void response () {
        getServer().getScheduler().cancelTask(this.counterId);
        this.handler.addTradingPlayers(asker, taker);
        this.asker.sendMessage("Player accepted. Starting trade.");
        this.taker.sendMessage("You accepted, Starting trade.");
        this.inv = Bukkit.createInventory(null, 54, String.format("%s | %s", this.asker.getDisplayName(), this.taker.getDisplayName()));
        this.asker.openInventory(this.inv);
        this.taker.openInventory(this.inv);
    }

    public void handlClickEvent (InventoryClickEvent event) {

    }

    public String inventoryName () {
        return this.inv != null ? this.inv.getName() : null;
    }
}
