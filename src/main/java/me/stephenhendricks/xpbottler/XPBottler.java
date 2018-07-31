package me.stephenhendricks.xpbottler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPBottler extends JavaPlugin {
    private String prefix;

    /**
     * Return the prefix to be output with console messages
     *
     * @return string
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * @param player the player
     */
    public void bottleXP(Player player) {
        ItemStack stack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        // get get the number of bottles the player should get
        stack.setAmount(this.getBottleNumber(player));
        // set the players level back to 0 (not their experience on the 0 level though)
        player.setLevel(0);
        // add XP Bottles to player inventory
        player.getInventory().addItem(stack);
    }

    /**
     * Return the number of xp bottles to give to the user
     *
     * @param player the player
     * @return int
     */
    private int getBottleNumber(Player player) {
        return this.getCurrentExp(player) / 7;
    }

    /**
     * Return the total current experience of a player
     *
     * @param player the player
     * @return int
     */
    private int getCurrentExp(Player player) {
        double currLevel = player.getLevel() + player.getExp();

        if (currLevel >= 31.0D) {
            return (int) (4.5D * Math.pow(currLevel, 2.0D) - 162.5D * currLevel + 2220.0D);
        }

        if (currLevel >= 16.0D) {
            return (int) (2.5D * Math.pow(currLevel, 2.0D) - 40.5D * currLevel + 360.0D);
        }

        return (int) (Math.pow(currLevel, 2.0D) + 6.0D * currLevel);
    }

    @Override
    public void onEnable() {
        // pull the default config.yml into memory
        saveDefaultConfig();

        // store the message prefix in memory
        this.prefix = getConfig().getString("prefix");

        // register our command and command handler
        getServer().getPluginCommand("xpb").setExecutor(new XPBCommandExecutor(this));

        // register an event listener to override the default xp bottle value
        getServer().getPluginManager().registerEvents(new XPBottleBreakEvent(), this);
    }
}
