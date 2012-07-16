package com.hawkfalcon.fieryinferno;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class fieryinferno extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		final File f = new File(getDataFolder(), "config.yml");
		if (!f.exists()){
			saveDefaultConfig();
		}
	}
    @Override
	public void onDisable()
	{
		this.saveConfig();
	}
	
    public int spreadLimit = 2;
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		Block b = event.getBlock();
		Location loc = b.getLocation();
		scanForIgnitables(loc);
	}

	private void scanForIgnitables(Location loc)
	{
		World w = loc.getWorld();
		Block b = w.getBlockAt(loc);
		spreadLimit = getConfig().getInt("spread_speed");

		for (int y = 1; y > -2; y--)
		{
			int count = 0;
			for (int x = 1; x > -2; x--)
			{
				for (int z = 1; z > -2; z--)
				{
					b = w.getBlockAt((int)loc.getX() + x, (int)loc.getY() + y, (int)loc.getZ() + z);
					if (b.getTypeId() != 0) {
						continue;
					}
					if ((b.getRelative(0, -1, 0).getTypeId() != 0) && (b.getRelative(0, -1, 0).getTypeId() != 51) && (count < this.spreadLimit))
					{
						b.setTypeId(51);
						count++;
					}
					if ((b.getRelative(-1, 0, 0).getTypeId() != 0) && (b.getRelative(0, -1, 0).getTypeId() != 51) && (count < this.spreadLimit))
					{
						b.setTypeId(51);
						count++;
					}
					if ((b.getRelative(1, 0, 0).getTypeId() != 0) && (b.getRelative(0, -1, 0).getTypeId() != 51) && (count < this.spreadLimit))
					{
						b.setTypeId(51);
						count++;
					}
					if ((b.getRelative(0, 0, -1).getTypeId() != 0) && (b.getRelative(0, -1, 0).getTypeId() != 51) && (count < this.spreadLimit))
					{
						b.setTypeId(51);
						count++;
					}
					if ((b.getRelative(0, 0, 1).getTypeId() == 0) || (b.getRelative(0, -1, 0).getTypeId() == 51) || (count >= this.spreadLimit))
					{
						continue;
					}
					b.setTypeId(51);
					count++;
				}
			}
		}
	}
}