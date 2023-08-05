package me.chickxn.commands;

import me.chickxn.Monopoly;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("monopoly.setup")) {
                if (args.length == 0) {
                    player.sendMessage(Monopoly.getInstance().getPrefix() + "Nutze /setup spawn");
                }else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("SPAWN")) {
                        Monopoly.getInstance().getLocationHandler().setLocation("SPAWN", player.getLocation());
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "§cSPAWN §7wurde gesetzt§8!");
                    }else if (args[0].equalsIgnoreCase("FIELD_LOS")) {
                        Monopoly.getInstance().getLocationHandler().setLocation("FIELD_LOS", player.getLocation());
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "§cFIELD_LOS §7wurde gesetzt§8!");
                    }else{
                        int fieldNumber = Integer.parseInt(args[0]);
                        Monopoly.getInstance().getLocationHandler().setLocation("FIELD_" + fieldNumber, player.getLocation());
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "§cFIELD_" + fieldNumber + " §7wurde gesetzt§8!");
                    }
                }
            }
        }
        return false;
    }
}
