package de.kesuaheli.keepinventorycost.commands;

import de.kesuaheli.keepinventorycost.KeepInventoryCost;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
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
            this.plugin.sendMessage(sender, Component.text("Only players can execute this command!", NamedTextColor.RED));
            return true;
        }
        Player player = (Player) sender;
        if (arg.length < 1 ||
                arg[0].equals("get") && arg.length != 1 ||
                arg[0].equals("set") && arg.length != 2) {
            return false;
        }

        boolean enabled = this.plugin.pConf.getConfig().getBoolean(player.getUniqueId() + ".enabled", false);
        String msg;
        String state;

        switch (arg[0]) {
        case "get":
            msg = this.plugin.getConfig().getString("message.setting.get", "MISSING TEXT");
            state = enabled ?
                    this.plugin.getConfigString("message.enabled", "<green>TRUE"):
                    this.plugin.getConfigString("message.disabled", "<red>FALSE");

            this.plugin.sendMessage(sender, Component.translatable(msg).args(Component.text(state)).toString());

            return true;
        case "set":
            if (!arg[1].equals("true") && !arg[1].equals("false")) {
                return false;
            }

            boolean enable = arg[1].equals("true");
            state = enable ?
                    this.plugin.getConfigString("message.enabled", "<green>TRUE"):
                    this.plugin.getConfigString("message.disabled", "<red>FALSE");

            if (enable != enabled) {
                this.plugin.pConf.getConfig().set(player.getUniqueId() + ".enabled", enable);
                this.plugin.pConf.saveConfig();
                msg = this.plugin.getConfigString("message.setting.set");
            } else {
                msg = this.plugin.getConfigString("message.setting.set_refuse");
            }
            this.plugin.sendMessage(player, Component.translatable(msg).args(Component.text(state)).toString());
            return true;
        }
        return false;
    }
}
