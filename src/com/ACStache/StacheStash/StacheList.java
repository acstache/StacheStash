package com.ACStache.StacheStash;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StacheList
{
    private static Logger log = Logger.getLogger("Minecraft");
    private static ArrayList<String> online;
    
    public static void initList()
    {
        online.add("Online players: ");
    }
    
    public static void addOnline(Player player)
    {
        online.add(player.getName());
        showOnline(player);
    }
    
    public static void removeOnline(Player player)
    {
        online.remove(player.getName());
    }
    
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
                for(int i = 0; i < online.size(); i++)
                    log.info(online.get(i) + " ");
            }
            else
            {
                ((Player)sender).sendMessage(ChatColor.RED + "You do not have permission to do that");
            }
        }
    }
    public static void showOnline(Player player)
    {
        for(int i = 0; i < online.size(); i++)
            player.sendMessage(online.get(i) + " ");
    }
}