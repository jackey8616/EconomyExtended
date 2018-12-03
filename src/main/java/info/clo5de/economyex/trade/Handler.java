package info.clo5de.economyex.trade;

import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Handler {

    protected Listener listener = null;
    protected LinkedHashMap<Player, Trade> askingTrade = new LinkedHashMap<>();
    protected ArrayList<Player> trading = new ArrayList<>();

    public Handler (MemorySection config) {
        listener = new Listener(this);
    }

    public void addTradingPlayers (Player ... add) {
        for (Player p : add) {
            this.trading.add(p);
        }
    }

    public Trade isTradeInv (Inventory inv) {
        for (Iterator it = askingTrade.keySet().iterator(); it.hasNext();) {
            Trade t = askingTrade.get(it.next());
            if (t.inventoryName().equals(inv.getName())) {
                return t;
            }
        }
        return null;
    }

}
