package tw.at.clo5de.currency;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CurrencyChain {

    private ArrayList<Currency> currencies = new ArrayList<>();
    private Map<Long, Currency> amountMap = new LinkedHashMap<>();

    public CurrencyChain (ArrayList<Currency> list) {
        this.currencies = list;
        for (Currency c : currencies) {
            amountMap.put(getBaseValue(c.getThisItemStack()), c);
        }
    }

    public long getBaseValue (ItemStack is) {
        int count = is.getAmount() == 0 ? 1 : is.getAmount();
        for (Currency c : currencies) {
            if (c.getThisItemStack().getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())) {
                if (c.isBase()) {
                    return count;
                } else {
                    return count * c.getMaxAmount() * getBaseValue(c.getPrevItemStack());
                }
            }
        }
        return 0L;
    }

    public List<ItemStack> getEqualCurrency (long amount) {
        ArrayList<ItemStack> list = new ArrayList<>();
        ArrayList<Long> reverse = new ArrayList<>(amountMap.keySet());

        for (int i = reverse.size() - 1; i >= 0; i--) {
            long mapAmount = reverse.get(i);
            if (mapAmount <= amount) {
                ItemStack is = amountMap.get(reverse.get(i)).getThisItemStack().clone();
                is.setAmount((int) (amount / mapAmount));
                amount %= mapAmount;
                list.add(is);
            }
        }
        return list;
    }

    public boolean containCurrency (Item i) {
        return i == null ? false : containCurrency(i.getItemStack());
    }

    public boolean containCurrency (ItemStack is) {
        if (is == null)
            return false;
        for (Currency c : currencies) {
            if (c.getThisItemStack().getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    public boolean containCurrency (String checkC) {
        for (Currency c : currencies) {
            if (c.getName().equals(checkC)) {
                return true;
            }
        }
        return false;
    }

    public boolean containCurrency (Currency checkC) {
        if (checkC == null)
            return false;
        for (Currency c : currencies) {
            if (c == checkC) {
                return true;
            }
        }
        return false;
    }

}
