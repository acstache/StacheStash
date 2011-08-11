package com.ACStache.StacheStash;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class StacheStash extends JavaPlugin
{
    private final StacheStashPlayerListener playerListener = new StacheStashPlayerListener(this);
    private static PermissionHandler permissionHandler;
    private Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile info;
    
    public void onEnable()
    {
        //Get description of plugin from plugin.yml
        info = getDescription();
        
        log.info("[StacheStash] Loading Configuration...");

        if(StacheMotD.getFile().exists()) //if a MotD file exists
        {
            try
            {
                StacheMotD.loadMotD(); //load it
                log.info("[StacheStash] Configuration loaded!");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else //otherwise
        {
            log.info("[StacheStash] No MotD file found. Creating a default MotD file");
            try
            {
                StacheMotD.createMotD(); //create it
                log.info("[StacheStash] MotD file created successfully!");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }            
        }
        
        //register event listener by (type, typeListener, priority, this)
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        
        //set up Perms 3.1.x
        setupPermissions();

        //Display plugin name & version on successful enable
        log.info("[StacheStash] v" + info.getVersion() + " is Enabled! (By: ACStache)");
    }
    
    public void onDisable()
    {
        log.info("[StacheStash] v" + info.getVersion() + "disabled!");
    }
    
    private void setupPermissions()
    {
        if (permissionHandler != null)
            return;
    
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        if (permissionsPlugin == null) return;
        
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
    }
    
    // Permissions checker
    public static boolean has(Player p, String s)
    {
        return (permissionHandler == null || permissionHandler.has(p, s));
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("time"))
        {
            StacheTime.TimeChanger(sender, args);
            return true;
        }
        else if(command.getName().equalsIgnoreCase("weather"))
        {
            StacheWeather.weatherChanger(sender, args);
            return true;
        }
        else if(command.getName().equalsIgnoreCase("who") || command.getName().equalsIgnoreCase("list"))
        {
            StacheList.showOnline(sender);
            return true;
        }
        else if(command.getName().equalsIgnoreCase("motd"))
        {
            try
            {
                StacheMotD.showMotD(sender, args);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}