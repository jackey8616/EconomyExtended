package tw.at.clo5de.player;

import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tw.at.clo5de.EconomyExtended;
import tw.at.clo5de.currency.Currency;
import tw.at.clo5de.currency.CurrencyChain;
import tw.at.clo5de.invoke.EssentialsInvoke;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Handler {

    private tw.at.clo5de.player.Listener listener = null;
    private EssentialsInvoke essInvoker = null;
    // public ArrayList<CurrencyChain> chains = new ArrayList<>();
    public CurrencyChain chain = null;
    public Map<String, Currency> currencies = new HashMap<>();

    public boolean autoConvert = true;

    public Handler (MemorySection config) {
        if (currenciesLoad(config) && chainLoad() && setupLoad(config)) {
            listener = new tw.at.clo5de.player.Listener(this);
            essInvoker = new EssentialsInvoke();
        } else {
            EconomyExtended.INSTANCE._getLogger().warning("There is some field missed, maybe check your config is at latest version.");
            getServer().getPluginManager().disablePlugin(EconomyExtended.INSTANCE);
        }
    }

    public void invokePlayerBalance (Player player) {
        long amount = calculateInventoryCurrency(player.getInventory());
        double balance = EconomyExtended.INSTANCE.getEconomy().getBalance(player);
        if (balance > amount) {
            EconomyExtended.INSTANCE.getEconomy().withdrawPlayer(player, balance - amount);
        } else {
            EconomyExtended.INSTANCE.getEconomy().depositPlayer(player, amount - balance);
        }
    }

    public ArrayList<ItemStack> filterInventory (Inventory inv) {
        ArrayList<ItemStack> isl = new ArrayList<>();
        for (ItemStack is : inv.getContents()) {
            if (chain.containCurrency(is)) {
                isl.add(is);
            }
        }
        return isl;
    }

    public void givePlayerMissedCurrency(Player player, long amount) {
        /*
        for (CurrencyChain cc : chains) {
            for (ItemStack is : cc.getEqualCurrency(amount)) {
                player.getInventory().addItem(is);
            }
        }
        */
        for (ItemStack is : chain.getEqualCurrency(amount)) {
            player.getInventory().addItem(is);
        }
        if (this.autoConvert) {
            this.convertCurrencies (player);
        }
    }

    public void removePlayerSurplusCurrency(Player player, long amount) {
        for (ItemStack is : filterInventory(player.getInventory())) {
            player.getInventory().remove(is);
        }
        this.givePlayerMissedCurrency(player, amount);
    }

    public void convertCurrencies (Player player) {
        long amount = calculateInventoryCurrency(player.getInventory());
        for (ItemStack is : filterInventory(player.getInventory())) {
            player.getInventory().removeItem(is);
        }
        for (ItemStack is : chain.getEqualCurrency(amount)) {
            player.getInventory().addItem(is);
        }
    }

    public long calculateInventoryCurrency(Inventory inv) {
        long amount = 0;
        for (ItemStack is : inv.getContents()) {
            if (is != null && currencies.containsKey(is.getItemMeta().getDisplayName())) {
                // amount += getBaseValue(is);
                amount += chain.getBaseValue(is);
            }
        }
        return amount;
    }

    private List<Currency> getTopStack(ItemStack is, ArrayList<tw.at.clo5de.currency.Currency> concat) {
        tw.at.clo5de.currency.Currency c = this.currencies.get(is.getItemMeta().getDisplayName());
        concat.add(c);
        return c.isTop() ? concat : getTopStack(c.getNextItemStack(), concat);
    }

    private boolean currenciesLoad(MemorySection config) {
        if (config.get("Kyc") == null) {
            EconomyExtended.INSTANCE._getLogger().warning("Can not find Kyc setting in Currency config.");
            return false;
        }
        List list = config.getList("Kyc");
        for (int i = 0; i < list.size(); ++i) {
            tw.at.clo5de.currency.Currency c = new tw.at.clo5de.currency.Currency((Map) list.get(i));
            currencies.put(c.getName(), c);
        }
        return true;
    }

    private boolean chainLoad () {
        for (Iterator it = this.currencies.keySet().iterator(); it.hasNext();) {
            tw.at.clo5de.currency.Currency c = this.currencies.get(it.next());
            if (c.isBase()) {
                ArrayList<Currency> chainList = new ArrayList<>();
                getTopStack(c.getThisItemStack(), chainList);
                // this.chains.add(new CurrencyChain(chainList));
                chain = new CurrencyChain(chainList);
                return true;
            }
        }
        return false;
    }

    private boolean setupLoad (MemorySection config) {
        try {
            this.autoConvert = config.getBoolean("AutoConvert");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
