package com.TGU.WhitelistPlugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TGU.Config;
import com.TGU.WhitelistPlugin.WhitelistPlugin;
import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;

public class BanPlayerCommand extends MyCommand
{
	public BanPlayerCommand()
	{
		super("ban", "TGU.LevelIII", "/ban [player] [duration] [reason]", "Ban a player", 3, 20, "ipban");
	}

	public boolean Exec(CommandSender sender, String label, String[] args)
	{
		try
		{
			String player = args[0];
			String ip = "-1";
			String reason = args[2];
			if(args.length > 3)
			{
				for(int i=3; i<args.length; i++)
				{
					reason += " " + args[i];
				}
			}
			
			IBanListFactory banList = WhitelistPlugin.Instance.GetBanList();
			if(banList.IsBanned(player))
			{
				sender.sendMessage("Player " + player + " is already banned.");
				return false;
			}
			int duration = Integer.parseInt(args[1]);
			if(duration > Config.getInt("MaxBanDuration", 72))
			{
				sender.sendMessage("You are not allowed to ban a player for that long. Use permban to permanently ban a player.");
				return false;
			}
			
			for(Player p : WhitelistPlugin.Instance.getServer().getOnlinePlayers())
			{
				if(p.getName().equalsIgnoreCase(player))
				{
					ip = p.getAddress().getHostString();
					p.kickPlayer("You have been banned for " + duration + " hours!");
					break;
				}
			}
			
			banList.Add(player, sender.getName(), duration, ip, reason);
			banList.Sync();
			sender.sendMessage("Successfully banned " + player + " for " + duration + " hours.");
			return true;
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage("Argument 2 must be an integer!");
		}
		return false;
	}	
}
