package com.TGU.WhitelistPlugin.Commands;

import org.bukkit.command.CommandSender;

import com.TGU.WhitelistPlugin.WhitelistPlugin;
import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;

public class UnbanPlayerCommand extends MyCommand
{
	public UnbanPlayerCommand() {
		super("unban", "TGU.LevelIV", "/unban [player]", "Unban a player", 1, 1);
	}

	public boolean Exec(CommandSender sender, String label, String[] args) {
		IBanListFactory banListFactory = WhitelistPlugin.Instance.GetBanList();
		if(!banListFactory.IsBanned(args[0]))
		{
			sender.sendMessage("Player " + args[0] + " is not banned.");
			return false;
		}
		banListFactory.Remove(args[0]);
		banListFactory.Sync();
		sender.sendMessage("Successfully unbanned player " + args[0] + ".");
		return true;
	}
}