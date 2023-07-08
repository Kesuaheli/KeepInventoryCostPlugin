package de.kesuaheli.keepinventorycost.commands;

import de.kesuaheli.keepinventorycost.KeepInventoryCost;
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
            sender.sendMessage("Successfully reloaded config!");
            return true;
        case "config":
            sender.sendMessage("Coming soon...");
            return true;
        }
        return false;
    }
}
