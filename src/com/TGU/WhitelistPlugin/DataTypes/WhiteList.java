package com.TGU.WhitelistPlugin.DataTypes;

import java.util.ArrayList;
import java.util.List;

public class WhiteList
{
	private List<PlayerWhitelistRecord> _whitelistRecords = new ArrayList<PlayerWhitelistRecord>();
	
	public void Add(PlayerWhitelistRecord record)
	{
		_whitelistRecords.add(record);
	}
	
	public void Remove(PlayerWhitelistRecord record)
	{
		_whitelistRecords.remove(record);
	}
	
	public boolean Contains(PlayerWhitelistRecord record)
	{
		return _whitelistRecords.contains(record);
	}
	
	public List<PlayerWhitelistRecord> GetWhitelistRecords()
	{
		return _whitelistRecords;
	}
	
	public void SetRecords(List<PlayerWhitelistRecord> records)
	{
		_whitelistRecords = records;
	}
}
