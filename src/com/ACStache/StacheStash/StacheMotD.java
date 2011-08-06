package com.ACStache.StacheStash;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StacheMotD
{
    @SuppressWarnings("unused")
    private StacheStash plugin;
    private static ArrayList<String> motdlist;
    private static Logger log = Logger.getLogger("Minecraft");
    private static File file = new File("plugins/MotD/MotD.txt");
    
    public StacheMotD(StacheStash stachestash)
    {
        plugin = stachestash;
    }
    
    public static void showMotD(Player who)
    {
        for(String s: motdlist)
            who.sendMessage(ChatColor.GREEN + s);
    }
    public static void showMotD(CommandSender sender) throws IOException
    {
        Player player = (Player)sender;
        if(sender instanceof Player && StacheStash.has(player, "StacheStash.MotD"))
        {
            if(file.exists())
            {
                showMotD(player);
            }
            else
            {
                player.sendMessage("No MotD Found");
                createMotD();
                player.sendMessage("Default MotD created");
                loadMotD();
            }
        }
        else if(!(sender instanceof Player))
        {
            log.info("You can't use that command from the console");
        }
        else
        {
            player.sendMessage("You don't have permission to use that command");
        }
    }
    
    public static void loadMotD() throws IOException
    {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        while((line = br.readLine()) != null)
            motdlist.add(line);
        
        br.close();
    }
    
    public static void createMotD() throws IOException
    {
        FileWriter writer = new FileWriter(file);
        writer.write("Welcome to the server!");
        writer.write("Please enjoy your stay!");
    }
}