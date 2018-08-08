package tw.at.clo5de;

import com.kunyihua.crafte.api.Bukkit.BukkitKyctAPI;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Currency {

    private ItemStack itemStack, nextItemStack, prevItemStack;
    private long maxAmount = 0;

    public Currency (Map map) {
        this(map.containsKey("Name") ? map.get("Name").toString() : null,
             map.containsKey("NextCurrency") ? map.get("NextCurrency").toString() : null,
             map.containsKey("PrevCurrency") ? map.get("PrevCurrency").toString() : null,
             map.containsKey("MaxAmount") ? new Long((int) map.get("MaxAmount")) : 64L);
    }

    public Currency (String thisName, String nextName, String prevName, long maxAmount) {
        BukkitKyctAPI kycAPI = new BukkitKyctAPI();
        this.itemStack = kycAPI.getItemByItemKey(thisName);
        this.nextItemStack = kycAPI.getItemByItemKey(nextName);
        this.prevItemStack = kycAPI.getItemByItemKey(prevName);
        this.maxAmount = maxAmount;
        if (this.nextItemStack == null || this.prevItemStack == null) {
            TraditionalEconomy._getLogger().warning(String.format("Detected Currency with no prev / next currency: [%s], next: [%s], prev: [%s]", thisName, nextName, prevName));
        }
    }

}
