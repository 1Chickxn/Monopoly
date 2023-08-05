package me.chickxn.commands;

import me.chickxn.Monopoly;
import me.chickxn.enums.GameStateEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("monopoly.start")) {
                if (args.length == 0) {
                    if (Monopoly.getInstance().getGameStateHandler().getGameStateEnum().equals(GameStateEnum.LOBBY)) {
                        if (Monopoly.getInstance().getGameStateHandler().getSeconds() <= 10) {
                            player.sendMessage(Monopoly.getInstance().getPrefix() + "Der §cCountdown §7wurde bereits verkürtzt§8!");
                        }else{
                            Monopoly.getInstance().getGameStateHandler().shortCountdown(10);
                            player.sendMessage(Monopoly.getInstance().getPrefix() + "Du hast den §cCountdown §7verkürtzt§8!");
                        }
                    }else{
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Du kannst den §cCountdown §7nicht in der §c"+ Monopoly.getInstance().getGameStateHandler().getGameStateEnum() + " §7Phase verkürzen§8!");
                    }
                }else{
                    player.sendMessage(Monopoly.getInstance().getPrefix() + "Nutze /start");
                }
            }else {
                player.sendMessage(Monopoly.getInstance().getPrefix() + "Dazu hast du keine §cRechte§8!");
            }
        }
        return false;
    }
}
