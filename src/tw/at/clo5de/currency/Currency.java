package tw.at.clo5de.currency;

import com.kunyihua.crafte.api.Bukkit.BukkitKyctAPI;
import org.bukkit.inventory.ItemStack;
import tw.at.clo5de.EconomyExtended;

import java.util.Map;

public class Currency {

    private String thisItemKey, nextItemKey, prevItemKey;
    private ItemStack thisItemStack, nextItemStack, prevItemStack;
    private long maxAmount = 0;

    public Currency (Map map) {
        this(map.containsKey("ItemKey") ? map.get("ItemKey").toString() : null,
             map.containsKey("NextCurrency") ? map.get("NextCurrency").toString() : null,
             map.containsKey("PrevCurrency") ? map.get("PrevCurrency").toString() : null,
             map.containsKey("MaxAmount") ? new Long((int) map.get("MaxAmount")) : 64L);
    }

    public Currency (String thisName, String nextName, String prevName, long maxAmount) {
        try {
            BukkitKyctAPI kycAPI = new BukkitKyctAPI();
            this.thisItemStack = kycAPI.getItemByItemKey(this.thisItemKey = thisName);
            this.nextItemStack = kycAPI.getItemByItemKey(this.nextItemKey = nextName);
            this.prevItemStack = kycAPI.getItemByItemKey(this.prevItemKey = prevName);
            this.maxAmount = maxAmount;
            if (EconomyExtended.INSTANCE.getConfigManager().debug && (this.nextItemStack == null || this.prevItemStack == null)) {
                EconomyExtended.INSTANCE._getLogger().warning(
                        String.format("Detected currency with no prev / next currency: [%s], next: [%s], prev: [%s]",
                                this.thisItemKey, this.nextItemKey, this.prevItemKey));
            }

            this.thisItemStack.getType();
        } catch (Exception e) {
            if (EconomyExtended.INSTANCE.getConfigManager().debug) {
                e.printStackTrace();
            }
        }
    }

    public ItemStack getThisItemStack () {
        return this.thisItemStack;
    }

    public ItemStack getPrevItemStack () {
        return this.prevItemStack;
    }

    public ItemStack getNextItemStack () {
        return this.nextItemStack;
    }

    public String getName () {
        return this.thisItemStack.getItemMeta().getDisplayName();
    }

    public String getPrevItemName () {
        return this.prevItemStack.getItemMeta().getDisplayName();
    }

    public boolean isBase () {
        return this.prevItemKey == null;
    }

    public boolean isTop () {
        return this.nextItemKey == null;
    }

    public long getMaxAmount () {
        return this.maxAmount;
    }

}
