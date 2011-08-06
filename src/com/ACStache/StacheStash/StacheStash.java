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
        
        log.info("Loading " + info.getName() + " Configuration...");
        //StacheStashConfig.loadConfig();
        log.info("Configuration loaded!");
        
      //register event listener by (type, typeListener, priority, this)
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        
        //set up Perms 3.1.x
        setupPermissions();

        //Display plugin name & version on successful enable
        log.info(info.getName() + " " + info.getVersion() + " is Enabled! (By: ACStache)");
    }
    
    public void onDisable()
    {
        log.info(info.getName() + " " + info.getVersion() + "disabled!");
    }
    
    private void setupPermissions()
    {
        if (permissionHandler != null)
            return;
    
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        if (permissionsPlugin == null) return;
        
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
    }
    
    // Permissions stuff
    public static boolean has(Player p, String s)
    {
        //return (permissionHandler != null && permissionHandler.has(p, s));
        return (permissionHandler == null || permissionHandler.has(p, s));
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("time"))
        {
            return StacheTime.TimeChanger(sender, command, label, args);
        }
        else if(command.getName().equalsIgnoreCase("motd"))
        {
            if(args.length < 1)
            {
                try
                {
                    StacheMotD.showMotD(sender);
                }
                catch (IOException e)
                {   
                    e.printStackTrace();
                }
            }
            else if(args[0].equalsIgnoreCase("reload"))
            {
                if(!(sender instanceof Player) || has((Player)sender, "StacheStash.MotDreload"))
                {
                    try
                    {
                        StacheMotD.loadMotD();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    ((Player)sender).sendMessage("You do not have permission to do that");
                }
            }
            else
            {
                
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}