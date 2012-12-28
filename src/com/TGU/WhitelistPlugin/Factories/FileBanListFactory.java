package com.TGU.WhitelistPlugin.Factories;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.TGU.WhitelistPlugin.WhitelistPlugin;
import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;
import com.TGU.WhitelistPlugin.DataTypes.BanList;
import com.TGU.WhitelistPlugin.DataTypes.PlayerBanRecord;

public class FileBanListFactory implements IBanListFactory
{
	private Path _path;
	private BanList _bans;
	
	public FileBanListFactory(Path path)
	{
		_path = path;
		
		_bans = new BanList();
		
		LoadFile();
	}
	
	public void Add(String playerName, String bannedBy, int duration, String ip)
	{
		Add(playerName, bannedBy, duration, ip, "None");
	}

	public void Add(String playerName, String bannedBy, int duration, String ip, String reason)
	{
		if(IsBanned(playerName)) return;
		PlayerBanRecord record =
				new PlayerBanRecord(playerName, Calendar.getInstance().getTime(), duration, bannedBy, "Minecraft", ip, reason);
		_bans.Add(record);
		WhitelistPlugin.Instance.LocalBans.add(playerName);
		WhitelistPlugin.Instance.SaveLocalBans();
	}

	
	public void Remove(String playerName)
	{
		List<PlayerBanRecord> records = _bans.GetBanRecords();
		for(int i=0; i<records.size(); i++)
		{
			if(records.get(i).PlayerName.equalsIgnoreCase(playerName))
				records.remove(i);
		}
		if(WhitelistPlugin.Instance.LocalBans.contains(playerName)) WhitelistPlugin.Instance.LocalBans.remove(playerName);
		WhitelistPlugin.Instance.SaveLocalBans();
		_bans.SetRecords(records);
	}
	
	public void ResetBanDuration(String player, int duration)
	{
		List<PlayerBanRecord> records = _bans.GetBanRecords();
		for(int i=0; i<records.size(); i++)
		{
			if(records.get(i).PlayerName.equalsIgnoreCase(player))
			{
				PlayerBanRecord record = records.get(i);
				record.Duration = duration;
				records.set(i, record);
			}
		}
		_bans.SetRecords(records);
	}
	
	public boolean IsBanned(String playerName)
	{	
		for(PlayerBanRecord record : _bans.GetBanRecords())
		{
			if(record.PlayerName.equalsIgnoreCase(playerName))
				return true;
		}
		
		return false;
	}

	
	public String GetBanReason(String playerName)
	{		
		if(!IsBanned(playerName)) return "None";
		for(PlayerBanRecord record : _bans.GetBanRecords())
		{
			if(record.PlayerName.equals(playerName))
				return record.Reason;
		}
		
		return "None";
	}

	
	public void Sync()
	{		
		Sync(true);
	}

	
	public void Sync(boolean forceOverwrite)
	{	
		if(!forceOverwrite) return;
		
		try {
			Files.delete(_path);
			Files.createFile(_path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<String> lines = new ArrayList<String>();
		
		for(PlayerBanRecord record : _bans.GetBanRecords())
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			lines.add(record.PlayerName);
			lines.add(formatter.format(record.TimeStamp));
			lines.add(record.Duration + "");
			lines.add(record.ModName);
			lines.add(record.SourceGame);
			lines.add(record.Ip);
			lines.add(record.Reason);
			lines.add("");
		}
		
		try {
			Files.write(_path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void DiscardChanges()
	{		
		_bans = new BanList();
		LoadFile();
		WhitelistPlugin.Instance.LoadLocalBans();
	}
	
	private void LoadFile()
	{
		System.out.println("loading file ban list");
		try
		{
			if(!Files.exists(_path))
			{
				Files.createFile(_path);
				return;
			}
			
			Files.readAllLines(_path, StandardCharsets.UTF_8);
			
		    try
		    {   
		        List<String> lines = Files.readAllLines(_path, StandardCharsets.UTF_8);
		        Iterator<String> iterate = lines.iterator();
		        while(iterate.hasNext())
		        {
		        	PlayerBanRecord record =
		        			new PlayerBanRecord(iterate.next(), iterate.next(),
		        								Integer.parseInt(iterate.next()),
		        								iterate.next(), iterate.next(),
		        								iterate.next(), iterate.next());
		        	_bans.Add(record);
		        	iterate.next();
		        	System.out.println("Name:" + record.PlayerName + " BanDate:" + record.TimeStamp + " Duration:" + record.Duration + " BannedBy:" + record.ModName + " Game:" + record.SourceGame + " Reason:" + record.Reason + " Ip:" + record.Ip);
		        }
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
