package de.kesuaheli.keepinventorycost.commands;

import de.kesuaheli.keepinventorycost.KeepInventoryCost;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KeepInventoryAdminCommand implements CommandExecutor {

    private final KeepInventoryCost plugin;

    public KeepInventoryAdminCommand(KeepInventoryCost plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] arg) {

        if (arg.length == 0) {
            return false;
        }

        switch (arg[0]) {
        case "reload":
            this.plugin.reloadConfig();
            this.plugin.sendMessage(sender, Component.text("Successfully reloaded config!",NamedTextColor.GREEN));
            return true;
        case "config":
            this.plugin.sendMessage(sender, "Coming soon...");
            return true;
        }
        return false;
    }
}
