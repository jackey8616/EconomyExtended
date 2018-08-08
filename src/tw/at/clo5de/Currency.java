package tw.at.clo5de;

import org.bukkit.inventory.ItemStack;

public class Currency {

    private ItemStack itemStack, nextItemStack, prevItemStack;

    public Currency(ItemStack itemStack, ItemStack nextItemStack, ItemStack prevItemStack) {
        this.itemStack = itemStack;
        this.nextItemStack = nextItemStack;
        this.prevItemStack = prevItemStack;
    }

}
