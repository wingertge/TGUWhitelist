package com.TGU.WhitelistPlugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TGU.WhitelistPlugin.WhitelistPlugin;
import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;

public class PermBanCommand extends MyCommand
{
	public PermBanCommand() {
		super("permban", "TGU.LevelIV", "/permban [player] [reason]", "Permanently ban a player.", 2, 20);
	}

	public boolean Exec(CommandSender sender, String label, String[] args) {
		String player = args[0];
		String ip = "-1";
		String reason = args[1];
		if(args.length > 2)
		{
			for(int i=2; i<args.length; i++)
			{
				reason += " " + args[i];
			}
		}
		
		for(Player p : WhitelistPlugin.Instance.getServer().getOnlinePlayers())
		{
			if(p.getName().equalsIgnoreCase(player))
			{
				ip = p.getAddress().getHostString();
				p.kickPlayer("You have been permanently banned!");
				break;
			}
		}
		
		IBanListFactory banList = WhitelistPlugin.Instance.GetBanList();
		if(banList.IsBanned(player))
		{
			sender.sendMessage("Player " + player + " is already banned. Setting ban duration to infinite.");
			banList.ResetBanDuration(player, Integer.MAX_VALUE);
			return true;
		}
		banList.Add(player, sender.getName(), Integer.MAX_VALUE, ip, reason);
		banList.Sync();
		sender.sendMessage("Successfully permbanned " + player + ".");
		return true;
	}
}
