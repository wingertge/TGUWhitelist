package com.TGU.WhitelistPlugin.DataTypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerWhitelistRecord
{
	public String PlayerName;
	public Date TimeStamp;
	
	public PlayerWhitelistRecord(String playerName, Date timeStamp)
	{
		PlayerName = playerName;
		TimeStamp = timeStamp;
	}
	
	public PlayerWhitelistRecord(String playerName, String timeStamp)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			TimeStamp = formatter.parse(timeStamp);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		PlayerName = playerName;
	}
}
