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
    private static ArrayList<String> motdlist = new ArrayList<String>();
    private static Logger log = Logger.getLogger("Minecraft");
    private static File file = new File("plugins/StacheStash/MotD.txt");
    
    /**
     * Display the Message of the Day to player
     * @param player the person who signed in or typed /motd
     */
    public static void showMotD(Player player)
    {
        for(String s: motdlist)
            player.sendMessage(ChatColor.GREEN + s);
    }
    /**
     * Determines who typed /motd then deals with it accordingly
     * @param sender
     * @throws IOException
     */
    public static void showMotD(CommandSender sender, String[] args) throws IOException
    {
        Player player = null;
        if(sender instanceof Player)
        {
            player = (Player)sender;
        }
        
        if(args.length < 1)
        {
            if(sender instanceof Player && StacheStash.has(player, "StacheStash.MotD"))
            {
                showMotD(player);
            }
            else
            {
                if(!(sender instanceof Player))
                    log.info("You can't use that command from the console");
                else
                    player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else if(args[0].equalsIgnoreCase("reload"))
        {
            if(!(sender instanceof Player) || StacheStash.has(player, "StacheStash.MotDreload"))
            {
                try
                {
                    StacheMotD.loadMotD();
                    if(!(sender instanceof Player))
                        log.info("MotD loaded successfully!");
                    else
                        player.sendMessage(ChatColor.GREEN + "MotD loaded successfully!");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else
        {
            if(!(sender instanceof Player))
                log.info("Please type 'motd reload'");
            else
                player.sendMessage(ChatColor.RED + "Please type '/motd' or '/motd reload'");
        }
        
        /*
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
                player.sendMessage("Default MotD created, please type /motd again");
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
        */
    }
    
    /**
     * Load the Message of the Day to be displayed later on
     * @throws IOException
     */
    public static void loadMotD() throws IOException
    {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        while((line = br.readLine()) != null)
            motdlist.add(line);
        
        br.close();
    }
    
    /**
     * Create a default Message of the Day if none is found
     * @throws IOException
     */
    public static void createMotD() throws IOException
    {
        File cFile = new File("plugins/StacheStash");
        cFile.mkdir();
        FileWriter writer = new FileWriter(file);        
        writer.write("Welcome to the server!");
        writer.write("Please enjoy your stay!");
    }
    
    /**
     * Returns the file of the MotD
     * @return the file of the MotD
     */
    public static File getFile()
    {
        return file;
    }
}