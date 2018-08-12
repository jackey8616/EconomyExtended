
public class Listener implements org.bukkit.event.Listener {

    private tw.at.clo5de.player.Handler playerHandler = null;

    public Listener (tw.at.clo5de.player.Handler handler) {
        this.handler = handler;
        getServer().getPluginManager().registerEvents(this, EconomyExtended.INSTANCE);
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(EconomyExtended.INSTANCE, new Runnable(){
            public void run(){
                //Here your code that you want to run after the delay
                try {
                    handler.invokePlayerBalance((Player) event.getPlayer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5L);//20L = 1 sec
    }

    @EventHandler
    public void onPlayerOpenInventory (InventoryOpenEvent event) {
        handler.invokePlayerBalance((Player) event.getPlayer());
    }

    @EventHandler
    public void onPlayerCloseInventory (InventoryCloseEvent event) {
        handler.invokePlayerBalance((Player) event.getPlayer());
    }

    @EventHandler
    public void onPlayerDropItem (PlayerDropItemEvent event) {
        if (handler.chain.containCurrency(event.getItemDrop())) {
            handler.invokePlayerBalance(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerPickItem (EntityPickupItemEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(EconomyExtended.INSTANCE, new Runnable(){
            public void run(){
                //Here your code that you want to run after the delay
                try {
                    if (event.getEntity() instanceof Player && handler.chain.containCurrency(event.getItem())) {
                        Player player = (Player) event.getEntity();
                        handler.invokePlayerBalance(player);
                        if (autoConvert) {
                            handler.convertCurrencies(player);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5L);//20L = 1 sec
    }

    @EventHandler
    public void onPlayerUseItem (PlayerInteractEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(EconomyExtended.INSTANCE, new Runnable(){
            public void run(){
                //Here your code that you want to run after the delay
                try {
                    if (handler.chain.containCurrency(event.getItem())) {
                        handler.invokePlayerBalance((Player) event.getPlayer());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5L);//20L = 1 sec
    }

}