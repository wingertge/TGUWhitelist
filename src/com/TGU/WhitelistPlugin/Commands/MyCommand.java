package com.TGU.WhitelistPlugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class MyCommand
{
	public String Name;
	public List<String> Aliases;
	public String Help;
	public String Description;
	public String Permission;
	public int MinArgs;
	public int MaxArgs;
	
	public MyCommand(String name, String permission, String help, String description, String... aliases)
	{
		this(name, permission, help, description, -1, -1, aliases);
	}
	
	public MyCommand(String name, String permission, String help, String description, int minArgs, int maxArgs, String... aliases)
	{
		Name = name;
		Help = help;
		Description = description;
		Permission = permission;
		Aliases = new ArrayList<String>(Arrays.asList(aliases));
		MinArgs = minArgs;
		MaxArgs = maxArgs;
	}
	
	public abstract boolean Exec(CommandSender sender, String label, String[] args);
}
