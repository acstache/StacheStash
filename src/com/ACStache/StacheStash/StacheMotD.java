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
    private static ArrayList<String> motdlist;
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