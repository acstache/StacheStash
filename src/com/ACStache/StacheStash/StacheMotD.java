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
        for(String s: motdlist) //for every line read from the text file
            player.sendMessage(ChatColor.GREEN + s); //print it out to the player
    }
    /**
     * Determines who typed /motd then deals with it accordingly
     * @param sender the player (or console) that typed the command
     * @throws IOException
     */
    public static void showMotD(CommandSender sender, String[] args) throws IOException
    {
        Player player = null;
        if(sender instanceof Player) //if sender is a player, initialize player
            player = (Player)sender;
        
        if(args.length < 1) //just '/motd' or 'motd' typed
        {
            if(file.exists()) //checks the file exists
            {
                if(StacheStash.Permissions == null && sender instanceof Player) //if no permissions detected and it's a player
                {
                    showMotD(player); //display the MotD
                }
                else if(sender instanceof Player && StacheStash.has(player, "StacheStash.MotD")) //is a player and has permission
                {
                    showMotD(player); //display the MotD
                }
                else //is console or doesn't have permission
                {
                    if(!(sender instanceof Player)) //console
                        log.info("[StacheStash] You can't use that command from the console");
                    else //no permission
                        player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
                }
            }
            else //no file exists
            {
                createMotD(); //create default MotD file
                loadMotD(); //load newly created MotD file
                if(sender instanceof Player) //tell player what happened
                {
                    player.sendMessage(ChatColor.RED + "No MotD found");
                    player.sendMessage(ChatColor.GREEN + "Default MotD created, please type /motd again");
                }
                else //tell console what happened
                {
                    log.info("[StacheStash] No MotD found");
                    log.info("[StacheStash] Default MotD created. 'motd' not allowed in console.");
                }
            }
        }
        else if(args[0].equalsIgnoreCase("reload")) //reloading MotD.txt
        {
            if(StacheStash.Permissions == null && sender instanceof Player && player.isOp()) //if no permissions detected and it's a player that isOp
            {
                loadMotD();
                player.sendMessage(ChatColor.GREEN + "MotD reloaded successfully!");
            }
            else if(!(sender instanceof Player) || StacheStash.has(player, "StacheStash.MotDreload")) //if from console or player has permission
            {
                loadMotD();
                if(!(sender instanceof Player))
                    log.info("[StacheStash] MotD reloaded successfully!");
                else
                    player.sendMessage(ChatColor.GREEN + "MotD reloaded successfully!");
            }
            else //not from console and no permission
            {
                player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else
        {
            if(!(sender instanceof Player))
                log.info("[StacheStash] Please type 'motd reload'");
            else
                player.sendMessage(ChatColor.RED + "Please type '/motd' or '/motd reload'");
        }
    }
    
    /**
     * Load the Message of the Day to be displayed later on
     * @throws IOException
     */
    public static void loadMotD() throws IOException
    {
        clearMotD();
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
        loadMotD();
    }
    
    /**
     * Clears out the ArrayList<String> of any current MotD loaded into it.
     * Useful for reloading a new MotD
     */
    public static void clearMotD()
    {
        while(!motdlist.isEmpty())
            motdlist.remove(0);
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