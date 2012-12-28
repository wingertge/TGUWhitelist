package com.TGU.WhitelistPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.TGU.Config;
import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;
import com.TGU.WhitelistPlugin.Abstract.IWhiteListFactory;
import com.TGU.WhitelistPlugin.Commands.BanPlayerCommand;
import com.TGU.WhitelistPlugin.Commands.CommandHandler;
import com.TGU.WhitelistPlugin.Commands.PermBanCommand;
import com.TGU.WhitelistPlugin.Commands.UnbanPlayerCommand;
import com.TGU.WhitelistPlugin.Factories.FileBanListFactory;
import com.TGU.WhitelistPlugin.Factories.FileWhiteListFactory;
import com.TGU.WhitelistPlugin.Factories.MySqlBanListFactory;

public class WhitelistPlugin extends JavaPlugin
{
	public static boolean WhiteList;
	public static boolean ForceWhiteList;
	public static boolean MergeBans;
	public static WhitelistPlugin Instance;
	
	private static String _remoteBansUrl;
	private static String _remoteWhitelistUrl;
	
	private IBanListFactory _banListFactory;
	private IWhiteListFactory _whiteListFactory;
	
	public List<String> LocalBans;
	
	public WhitelistPlugin()
	{
		Instance = this;
	}
	
	public void onEnable()
	{
		Config.loadSettings();
		WhiteList = Config.getBool("WhiteList", true);
		ForceWhiteList = Config.getBool("ForceWhiteList", true);
		MergeBans = Config.getBool("MergeBans", true);
		_remoteBansUrl = Config.getString("RemoteBansUrl", "file:///plugins/TGUWhiteList/remoteBans.ini");
		_remoteWhitelistUrl = Config.getString("RemoteWhitelistUrl", "file:///plugins/TGUWhiteList/remoteWhitelist.ini");
		
		LocalBans = new ArrayList<String>();
		LoadLocalBans();
		
		if(_remoteBansUrl.startsWith("mysql:"))
			_banListFactory = new MySqlBanListFactory(_remoteBansUrl.replace("mysql:", ""));
		else _banListFactory = new FileBanListFactory(Paths.get(_remoteBansUrl.replace("file:///", "")));
		_whiteListFactory = new FileWhiteListFactory(Paths.get(_remoteWhitelistUrl.replace("file:///", "")));
		
		CommandHandler.Commands.add(new BanPlayerCommand());
		CommandHandler.Commands.add(new UnbanPlayerCommand());
		CommandHandler.Commands.add(new PermBanCommand());
		
		_banListFactory.Add("test1", "evilMod", 1, "-1");
		_banListFactory.Add("test2", "evenMoreEvilMod", 1, "127.0.0.1");
		_banListFactory.Add("test3", "superEvilMod", 1, "noob", "No apparent reason");
		
		_banListFactory.Sync();
		
		_whiteListFactory.Add("test1");
		_whiteListFactory.Add("test2");
		_whiteListFactory.Add("test3");
		
		_whiteListFactory.Sync();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		return CommandHandler.HandleCommand(sender, command, commandLabel, args);
	}
	
	public IBanListFactory GetBanList()
	{
		return _banListFactory;
	}
	
	public IWhiteListFactory GetWhiteList()
	{
		return _whiteListFactory;
	}
	
	public void LoadLocalBans()
	{
		try
		{
			String localBansFileName = "plugins/TGUWhiteList/localBans.ini";
			
			Path path = Paths.get(localBansFileName);
			if(!Files.exists(path))
				Files.createFile(path);
		
			LocalBans = Files.readAllLines(path, StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void SaveLocalBans()
	{
		try
		{
			String localBansFileName = "plugins/TGUWhiteList/localBans.ini";
			
			Path path = Paths.get(localBansFileName);
			if(Files.exists(path)) Files.delete(path);
			Files.createFile(path);
			
			Files.write(path, LocalBans, StandardCharsets.UTF_8);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
