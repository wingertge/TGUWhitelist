package com.TGU;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.TGU.WhitelistPlugin.WhitelistPlugin;

public class Config
{
	private static String _configFileName;
	private static HashMap<String, String> _settings;
	
	static
	{
		_settings = new HashMap<String, String>();
	}
	
	public static void loadSettings()
	{
		try
		{
			_configFileName = "plugins/TGUWhiteList/config.ini";
			File dir = new File("plugins/TGUWhiteList");
			dir.mkdirs();
			File f = new File(_configFileName);
			if(!f.exists())
			{
				f.createNewFile();
				copyDefaults();
				return;
			}
			
			List<String> lines = new ArrayList<String>();
			Path path = Paths.get(_configFileName);
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		    
		    for(String s : lines)
		    {
		    	s = s.replace(" ", "");
		    	String key = s.split("=")[0];
		    	String value = s.split("=")[1];
		    	_settings.put(key, value);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void copyDefaults()
	{
		try
		{
			InputStream inputStream = WhitelistPlugin.class.getResourceAsStream("/config.ini");
			Scanner s = new Scanner(inputStream);
			Scanner s1 = s.useDelimiter("\\A");
			String contents = s1.hasNext() ? s1.next() : "";
			List<String> test = new ArrayList<String>();
			test.add(contents);
			Path path = Paths.get("plugins/TGUWhiteList/config.ini");
			Files.write(path, test, StandardCharsets.UTF_8);
			s.close();
			s1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getString(String key, String fallback)
	{
		return _settings.containsKey(key) ? _settings.get(key) : fallback;
	}
	
	public static boolean getBool(String key, boolean fallback)
	{
		try
		{
			return _settings.containsKey(key) ? Boolean.parseBoolean(_settings.get(key)) : fallback;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return fallback;
		}
	}
	
	public static int getInt(String key, int fallback)
	{
		try
		{
			return _settings.containsKey(key) ? Integer.parseInt(_settings.get(key)) : fallback;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return fallback;
		}
	}
	
	public static HashMap<String, String> GetSettings()
	{
		return _settings;
	}
}
