package com.TGU.WhitelistPlugin.Factories;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.TGU.WhitelistPlugin.Abstract.IWhiteListFactory;
import com.TGU.WhitelistPlugin.DataTypes.PlayerWhitelistRecord;
import com.TGU.WhitelistPlugin.DataTypes.WhiteList;

public class FileWhiteListFactory implements IWhiteListFactory
{
	private Path _path;
	private WhiteList _whiteListed;
	
	public FileWhiteListFactory(Path path)
	{
		_path = path;
		
		_whiteListed = new WhiteList();
		
		LoadFile();
	}

	public void Add(String playerName)
	{
		PlayerWhitelistRecord record =
				new PlayerWhitelistRecord(playerName, Calendar.getInstance().getTime());
		if(_whiteListed.Contains(record)) return;
		_whiteListed.Add(record);
	}

	
	public void Remove(String playerName)
	{
		List<PlayerWhitelistRecord> records = _whiteListed.GetWhitelistRecords();
		for(int i=0; i<records.size(); i++)
		{
			if(records.get(i).PlayerName.equalsIgnoreCase(playerName))
				records.remove(i);
		}
		
		_whiteListed.SetRecords(records);
	}

	
	public boolean IsWhiteListed(String playerName)
	{	
		for(PlayerWhitelistRecord record : _whiteListed.GetWhitelistRecords())
		{
			if(record.PlayerName.equalsIgnoreCase(playerName))
				return true;
		}
		
		return false;
	}
	
	public void Sync()
	{		
		Sync(true);
	}

	
	public void Sync(boolean forceOverwrite)
	{	
		if(!forceOverwrite) return;
		List<String> lines = new ArrayList<String>();
		
		for(PlayerWhitelistRecord record : _whiteListed.GetWhitelistRecords())
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			lines.add(record.PlayerName);
			lines.add(formatter.format(record.TimeStamp));
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
		_whiteListed = new WhiteList();
		LoadFile();
	}
	
	private void LoadFile()
	{
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
		        String playerName = "";
		        String timeStamp = "";
		        
		        for(String line : Files.readAllLines(_path, StandardCharsets.UTF_8)) {
		        	if(line.equals(""))
			        	continue;
		        	
		        	if(playerName.equals(""))
		        	{
		        		playerName = line;
		        		continue;
		        	}
		        	
		        	if(timeStamp.equals(""))
		        	{
		        		timeStamp = line;
		        		_whiteListed.Add(new PlayerWhitelistRecord(playerName, timeStamp));
		        		continue;
		        	}
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
