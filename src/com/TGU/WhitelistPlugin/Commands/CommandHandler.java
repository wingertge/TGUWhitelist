package com.TGU.WhitelistPlugin.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler
{
	public static List<MyCommand> Commands = new ArrayList<MyCommand>();
	
	public static boolean HandleCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		List<MyCommand> myCommands = new ArrayList<MyCommand>();
		
		for(MyCommand cmd : Commands)
			if(cmd.Name.equalsIgnoreCase(command.getName()) || cmd.Aliases.contains(command.getName().toLowerCase()))
				myCommands.add(cmd);
		
		if(myCommands.size() == 0) return false;
		
		for(MyCommand cmd : myCommands)
		{
			if(!sender.hasPermission(cmd.Permission) && sender instanceof Player)
			{
				sender.sendMessage("You don't have permission to do that!");
				continue;
			}
			if(!(cmd.MinArgs == -1 || cmd.MaxArgs == -1) &&
			   args.length < cmd.MinArgs || args.length > cmd.MaxArgs)
			{
				sender.sendMessage(cmd.Help);
				continue;
			}
			if(cmd.Exec(sender, commandLabel, args)) return true;
		}
		
		return false;
	}
}
