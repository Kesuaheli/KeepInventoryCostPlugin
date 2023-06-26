package de.kesuaheli.keepinventorycost.commands;

import de.kesuaheli.keepinventorycost.KeepInventoryCost;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KeepInventoryCommand implements CommandExecutor {

    private final KeepInventoryCost plugin;

    public KeepInventoryCommand(KeepInventoryCost plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] arg) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }
        Player player = (Player) sender;
        if (arg.length < 1 ||
                arg[0].equals("get") && arg.length != 1 ||
                arg[0].equals("set") && arg.length != 2) {
            return false;
        }

        boolean enabled = this.plugin.pConf.getConfig().getBoolean(player.getUniqueId() + ".enabled", false);

        switch (arg[0]) {
        case "get":
            player.sendMessage("Your setting is set to " + enabled);
            return true;
        case "set":
            if (!arg[1].equals("true") && !arg[1].equals("false")) {
                return false;
            }
            boolean enable = arg[1].equals("true");
            if (enable == enabled) {
                player.sendMessage("Your setting is already set to " + enable);
                return true;
            }
            this.plugin.pConf.getConfig().set(player.getUniqueId() + ".enabled", enable);
            this.plugin.pConf.saveConfig();
            player.sendMessage("Your setting was updated to " + enable);
            return true;
        }
        return false;
    }
}
