package com.ACStache.StacheStash;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StacheList
{
    private static Logger log = Logger.getLogger("Minecraft");

    public static void showOnline(CommandSender sender)
    {
        if((sender instanceof Player) && StacheStash.has((Player)sender, "StacheStash.List"))
        {
            showOnline((Player)sender);
        }
        else
        {
            if(!(sender instanceof Player))
            {
                log.info("Online Players [world: players]");
                for(World w : Bukkit.getServer().getWorlds()) //get list of worlds and look through them
                {
                   String temp = w.getName() + ": "; //add world name to String

                   for(Player p : w.getPlayers()) //get list of players and look through them
                       temp += p.getName() + " "; //add player name to String

                   if(temp.equals(w.getName() + ": ")) //no players in world
                       temp += "[empty]"; //add [empty] if no players are in world

                   log.info(temp); //send message to console listing online players
                }
            }
            else
            {
                ((Player)sender).sendMessage(ChatColor.RED + "You do not have permission to do that");
            }
        }
    }
    public static void showOnline(Player player)
    {
        player.sendMessage("Online Players [world: players]");
        for(World w : Bukkit.getServer().getWorlds()) //get list of worlds and look through them
        {
           String temp = w.getName() + ": "; //add world name to String

           for(Player p : w.getPlayers()) //get list of players and look through them
               temp += p.getName() + " "; //add player name to String

           if(temp.equals(w.getName() + ": ")) //no players in world
               temp += "[empty]"; //add [empty] if no players are in world

           player.sendMessage(temp); //send message to player listing online players
        }
    }
}