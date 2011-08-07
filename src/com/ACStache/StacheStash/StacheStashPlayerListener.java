package com.ACStache.StacheStash;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerListener;

public class StacheStashPlayerListener extends PlayerListener
{
    final StacheStash plugin;
    static Player player;
    
    public StacheStashPlayerListener(StacheStash stachestash)
    {
        plugin = stachestash;
    }

    public void onPlayerJoin(PlayerJoinEvent event)
    { 
        player = event.getPlayer();
        //StacheMotD.showMotD(player); //if used, first line won't show properly
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
                StacheMotD.showMotD(player);
            }
        }, 5); //delays showing the MotD by 5 server ticks (~1/4 of a second)
        
        StacheList.addOnline(player);
    }
    
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        player = event.getPlayer();
        StacheList.removeOnline(player);
    }
}