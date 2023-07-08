package de.kesuaheli.keepinventorycost.events;

import de.kesuaheli.keepinventorycost.KeepInventoryCost;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    private final KeepInventoryCost plugin;

    public PlayerDeathEvent(KeepInventoryCost plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        if (event.getKeepInventory() ||
                !this.plugin.pConf.getConfig().getBoolean(event.getPlayer().getUniqueId() + ".enabled", false)) {
            return;
        }

        int cost = this.plugin.getConfig().getInt("cost", 0);
        Component costText = Component.text(cost)
                .appendSpace()
                .append(Component.text(cost == 1? this.plugin.eco.currencyNameSingular():this.plugin.eco.currencyNamePlural()));
        if (cost >= 0 &&
                (this.plugin.eco.getBalance(event.getPlayer()) < cost ||
                !this.plugin.eco.withdrawPlayer(event.getPlayer(), cost).transactionSuccess())) {
            String msg = this.plugin.getConfig().getString("message.death.no_money", "MISSING TEXT");
            this.plugin.sendMessage(event.getPlayer(), Component.translatable(msg)
                    .args(Component.text(this.plugin.eco.currencyNamePlural()), costText));
            return;
        }

        event.setKeepInventory(true);
        event.getDrops().clear();
        event.setKeepLevel(true);
        event.setDroppedExp(0);

        String msg = this.plugin.getConfig().getString("message.death.paid", "MISSING TEXT");
        this.plugin.sendMessage(event.getPlayer(), Component.translatable(msg).args(costText));
    }
}
