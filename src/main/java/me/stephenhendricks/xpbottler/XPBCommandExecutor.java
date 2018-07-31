package me.stephenhendricks.xpbottler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XPBCommandExecutor implements CommandExecutor {

    private XPBottler plugin;

    public XPBCommandExecutor(XPBottler plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle when a user enters the xpb command
     *
     * @param sender The entity sending the command
     * @param cmd    The command typed in (xpb)
     * @param cmdLbl unused
     * @param args   unused
     * @return boolean
     */
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player");
            return false;
        }

        // get the player who sent the command
        Player player = this.plugin.getServer().getPlayer(((Player) sender).getUniqueId());

        double currLevel = player.getLevel() + player.getExp();

        // if the current level is less than 1
        if (currLevel < 1.0D) {
            player.sendMessage(ChatColor.RED + "You don't have enough XP!");
            return true;
        }

        // convert the user's XP to XP bottles
        this.plugin.bottleXP(player);

        player.sendMessage(ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', this.plugin.getPrefix()) + " Your XP has been converted to bottles!");

        return true;
    }
}
