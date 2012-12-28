package com.TGU.WhitelistPlugin.DataTypes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerBanRecord
{
	public String PlayerName;
	public Date TimeStamp;
	public String ModName;
	public String SourceGame;
	public String Reason;
	public String Ip;
	public int Duration;
	
	public PlayerBanRecord(String playerName, String date, int duration, String modName, String game, String reason, String ip)
	{
		PlayerName = playerName;
		ModName = modName;
		SourceGame = game;
		Reason = reason;
		Ip = ip;
		Duration = duration;
		
		try
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeStamp = formatter.parse(date);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public PlayerBanRecord(String playerName, Date date, int duration, String modName, String game, String reason)
	{
		this(playerName, date, duration, modName, game, reason, "-1");
	}
	
	public PlayerBanRecord(String playerName, Date date, int duration, String modName, String game, String reason, String ip)
	{
		PlayerName = playerName;
		TimeStamp = date;
		Duration = duration;
		ModName = modName;
		SourceGame = game;
		Reason = reason;
		Ip = ip;
	}
}
