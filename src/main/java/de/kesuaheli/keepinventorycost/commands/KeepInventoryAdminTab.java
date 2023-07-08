package de.kesuaheli.keepinventorycost.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeepInventoryAdminTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] arg) {

        List<String> arguments = new ArrayList<>();

        if (arg.length == 1) {
            arguments.add("reload");
        }

        return arguments.stream()
                .filter(a -> a.toLowerCase().startsWith(arg[arg.length-1].toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
