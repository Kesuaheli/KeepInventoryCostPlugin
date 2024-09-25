package de.kesuaheli.keepinventorycost;

import de.kesuaheli.keepinventorycost.commands.KeepInventoryAdminCommand;
import de.kesuaheli.keepinventorycost.commands.KeepInventoryAdminTab;
import de.kesuaheli.keepinventorycost.commands.KeepInventoryCommand;
import de.kesuaheli.keepinventorycost.commands.KeepInventoryTab;
import de.kesuaheli.keepinventorycost.events.PlayerDeathEvent;
import de.kesuaheli.keepinventorycost.files.PlayersConfig;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class KeepInventoryCost extends JavaPlugin {

    public Economy eco;
    public final Logger logger = getLogger();

    private final Component MSG_PREFIX = configStringText("message.prefix").appendSpace();

    public PlayersConfig pConf;

    @Override
    public void onEnable() {

        // check Economy plugin
        if (!setupEconomy()) {
            this.logger.warning("You need an economy plugin and Vault installed in order to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.saveDefaultConfig();
        this.reloadConfig();
        this.pConf = new PlayersConfig(this);

        this.getServer().getPluginManager().registerEvents(new PlayerDeathEvent(this), this);
        Objects.requireNonNull(this.getCommand("keepinventory")).setExecutor(new KeepInventoryCommand(this));
        Objects.requireNonNull(this.getCommand("keepinventory")).setTabCompleter(new KeepInventoryTab());
        Objects.requireNonNull(this.getCommand("keepinventoryadmin")).setExecutor(new KeepInventoryAdminCommand(this));
        Objects.requireNonNull(this.getCommand("keepinventoryadmin")).setTabCompleter(new KeepInventoryAdminTab());

        this.logger.info("KeepInventoryCost enabled!");
    }

    @Override
    public void onDisable() {
        this.logger.warning("Shutting down, bye!");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            return false;
        }
        this.eco = economyProvider.getProvider();
        return true;
    }

    public void sendMessage(Audience receiver, String message) {
        sendMessage(receiver, MiniMessage.miniMessage().deserialize(message));
    }

    public void sendMessage(Audience receiver, Component component) {
        receiver.sendMessage(MSG_PREFIX.append(component));
    }

    public String getConfigString(String path) {
        return getConfigString(path, path);
    }
    public String getConfigString(String path, String def) {
        return this.getConfig().getString(path, def);
    }

    public Component configStringText(String path) {
        return configStringText(path, path);
    }
    public Component configStringText(String path, String def) {
        return MiniMessage.miniMessage().deserialize(getConfigString(path, def));
    }
}
