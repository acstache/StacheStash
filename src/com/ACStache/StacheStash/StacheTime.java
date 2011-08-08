package com.ACStache.StacheStash;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StacheTime
{
    private static Logger log = Logger.getLogger("Minecraft");
    
    /**
     * Show the possible '/time' commands to player
     * @param player the player who either typed '/time' or incorrectly typed a different '/time' command
     */
    public static void showTime(Player player)
    {
        player.sendMessage(ChatColor.GREEN + "Any parameters in " + ChatColor.RED + "[]" + ChatColor.GREEN + "'s are optional");
        player.sendMessage(ChatColor.GREEN + "'/time set ## " + ChatColor.RED + "[worldname]'");
        player.sendMessage(ChatColor.GREEN + "'/time add ## " + ChatColor.RED + "[worldname]'");
        player.sendMessage(ChatColor.GREEN + "'/time (day/noon/dusk/midnight) " + ChatColor.RED + "[worldname]'");
    }
    
    /**
     * Shows the possible 'time' commands in the console if 'time' was typed
     * or a differnt 'time' command was incorrectly typed
     */
    public static void showTimeConsole()
    {
        log.info("Please enter one of the following commands:");
        log.info("'time set ## worldname'");
        log.info("'time add ## worldname'");
        log.info("'time (day/noon/dusk/midnight) worldname'");
    }
    
    /**
     * Algorythim to set or add time, or just display the time commands when a '/time' or 'time' command was used
     * @param sender The sender of the command
     * @param args The list of arguments with the command
     * @return true/false for the onCommand method
     */
    public static void TimeChanger(CommandSender sender, String[] args) 
    {
        Player player = (Player)sender;
        
        if(args.length < 1) //if player types just '/time' or console types just 'time'
        {
            if(sender instanceof Player) //if it's a player
            {
                showTime(player); //send commands to chat
            }
            else //if it's from console
            {
                showTimeConsole(); //send commands to console
            }
        }
        else if(args[0].equalsIgnoreCase("set")) //sets time to args[1]
        {
            if(StacheStash.has(player, "StacheStache.TimeSet") || !(sender instanceof Player)) //player has TickTock.Set perm or from console
            {
                if(sender instanceof Player) //sees if it's the player typing the command
                {
                    if(args[1].matches("^[0-9]+$")) //check args[1] is all numbers
                    {
                        if(args.length < 3) //no world specified
                        {
                            World pWorld = player.getWorld(); //get player's current world
                            pWorld.setTime(Long.parseLong(args[1])); //cast args[1] to long from String and set time
                        }
                        else //world specified
                        {
                            World poWorld = Bukkit.getServer().getWorld(args[2]); //gets world based on args[2]
                            if(!(poWorld==null)) //make sure it's not a null world
                            {
                                poWorld.setTime(Long.parseLong(args[1])); //cast args[1] to long from String and set time
                            }
                            else
                            {
                                player.sendMessage(ChatColor.GREEN + "You need to specify an existing world. '/time set ## " + ChatColor.RED + "[worldname]'");
                            }
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.GREEN + "Please enter a numeric value for the time. '/time set ##'");
                    }
                }
                else //happens if the console types the command
                {
                    if(args[1].matches("^[0-9]+$")) //check args[1] is all numbers
                    {
                        if(args.length == 3) //checks to make sure a world name was given
                        {
                            World cWorld = Bukkit.getServer().getWorld(args[2]); //gets world name from server
                            if(!(cWorld==null)) //makes sure it's not a null world
                            {
                                cWorld.setTime(Long.parseLong(args[1])); //cast args[1] to long from String and set time
                            }
                            else
                            {
                                log.info("You need to specify an existing world. 'time set ## worldname'");
                            }
                        }
                        else
                        {
                            log.info("You need to specify a world. 'time set ## worldname'");
                        }
                    }
                    else
                    {
                        log.info("Please enter a numeric value for the time. 'time set ## worldname'");
                    }
                }
            }
            else //player doesn't have permission
            {
                player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else if(args[0].equalsIgnoreCase("day") || args[0].equalsIgnoreCase("noon") || args[0].equalsIgnoreCase("dusk") || args[0].equalsIgnoreCase("midnight"))
        {
            if(StacheStash.has(player, "StacheStash.TimeSet") || !(sender instanceof Player)) //player has TickTock.Set perm or from console
            {
                if(sender instanceof Player) //sees if it's the player typing the command
                {
                    if(args.length < 2) //checks to see if player provided a world name
                    {
                        World pWorld = player.getWorld();
                        if(args[0].equalsIgnoreCase("day")) //start of the day
                            pWorld.setTime(0);
                        else if(args[0].equalsIgnoreCase("noon")) //middle of the day
                            pWorld.setTime(6000);
                        else if(args[0].equalsIgnoreCase("dusk")) //start of the night
                            pWorld.setTime(12000);
                        else if(args[0].equalsIgnoreCase("midnight")) //middle of the night
                            pWorld.setTime(18000);
                        else
                            player.sendMessage(ChatColor.GREEN + "Please specify a time (day/noon/dusk/midnight)");
                    }
                    else
                    {
                        World cWorld = Bukkit.getServer().getWorld(args[1]); //gets world name from server
                        if(!(cWorld==null)) //makes sure it's not a null world
                        {
                            if(args[0].equalsIgnoreCase("day")) //start of the day
                                cWorld.setTime(0);
                            else if(args[0].equalsIgnoreCase("noon")) //middle of the day
                                cWorld.setTime(6000);
                            else if(args[0].equalsIgnoreCase("dusk")) //start of the night
                                cWorld.setTime(12000);
                            else if(args[0].equalsIgnoreCase("midnight")) //middle of the night
                                cWorld.setTime(18000);
                            else
                                player.sendMessage(ChatColor.GREEN + "Please specify a time (day/noon/dusk/midnight)");
                        }
                        else
                        {
                            player.sendMessage(ChatColor.GREEN + "You need to specify an existing world. 'time (day/noon/dusk/midnight) " + ChatColor.RED + "[worldname]'");
                        }
                    }
                }
                else
                {
                    World cWorld = Bukkit.getServer().getWorld(args[1]); //gets world name from server
                    if(!(cWorld==null)) //makes sure it's not a null world
                    {
                        if(args[0].equalsIgnoreCase("day")) //start of the day
                            cWorld.setTime(0);
                        else if(args[0].equalsIgnoreCase("noon")) //middle of the day
                            cWorld.setTime(6000);
                        else if(args[0].equalsIgnoreCase("dusk")) //start of the night
                            cWorld.setTime(12000);
                        else if(args[0].equalsIgnoreCase("midnight")) //middle of the night
                            cWorld.setTime(18000);
                        else
                            log.info("Please specify a time (day/noon/dusk/midnight)");
                    }
                    else
                    {
                        log.info("Please specify a world. 'time (day/noon/dusk/midnight) worldname'");
                    }
                }
            }
            else //player doesn't have permission
            {
                player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else if(args[0].equalsIgnoreCase("add"))
        {
            if(StacheStash.has(player, "StacheStash.TimeAdd") || !(sender instanceof Player)) //player has TickTock.Add perm or from console
            {
                if(sender instanceof Player) //sees if it's the player typing the command
                {
                    if(args[1].matches("^[0-9]+$")) //check args[1] is all numbers
                    {
                        if(args.length < 3) //no world specified
                        {
                            World pWorld = player.getWorld(); //get player's current world
                            pWorld.setTime(pWorld.getTime() + Long.parseLong(args[1])); //cast args[1] to long from String and add time
                        }
                        else //world specified
                        {
                            World poWorld = Bukkit.getServer().getWorld(args[2]); //gets world based on args[2]
                            if(!(poWorld==null)) //make sure it's not a null world
                            {
                                poWorld.setTime(poWorld.getTime() + Long.parseLong(args[1])); //cast args[1] to long from String and add time
                            }
                            else
                            {
                                player.sendMessage(ChatColor.GREEN + "You need to specify an existing world. '/time add ## " + ChatColor.RED + "[worldname]'");
                            }
                        }
                        
                    }
                    else
                    {
                        player.sendMessage(ChatColor.GREEN + "Please enter a numeric value for the time. '/time add ##'");
                    }
                }
                else //happens if the console types the command
                {
                    if(args[1].matches("^[0-9]+$")) //check args[1] is all numbers
                    {
                        if(args.length == 3) //checks to make sure a world name was given
                        {
                            World cWorld = Bukkit.getServer().getWorld(args[2]); //gets world name from server
                            if(!(cWorld==null)) //makes sure it's not a null world
                            {
                                cWorld.setTime(cWorld.getTime() + Long.parseLong(args[1])); //cast args[1] to long from String and add time
                            }
                            else
                            {
                                log.info("You need to specify an existing world. 'time add ## worldname'");
                            }
                        }
                        else
                        {
                            log.info("You need to specify a world. 'time add ## worldname'");
                        }
                    }
                    else
                    {
                        log.info("Please enter a numeric value for the time. 'time add ## worldname'");
                    }
                }
            }
            else //player doesn't have permission
            {
                player.sendMessage(ChatColor.RED + "You don't have Permission to do that");
            }
        }
        else
        {
            if(sender instanceof Player) //player improperly typed command
            {
                player.sendMessage(ChatColor.RED + "That command isn't recognized.");
                showTime(player);
                
            }
            else
            {
                log.info("That command isn't recognized.");
                showTimeConsole();
            }
        }
    }
}