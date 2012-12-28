package com.TGU.WhitelistPlugin.DataTypes;

import java.util.ArrayList;
import java.util.List;

public class BanList
{
	private List<PlayerBanRecord> _banRecords = new ArrayList<PlayerBanRecord>();
	
	public void Add(PlayerBanRecord record)
	{
		_banRecords.add(record);
	}
	
	public void Remove(PlayerBanRecord record)
	{
		_banRecords.remove(record);
	}
	
	public boolean Contains(PlayerBanRecord record)
	{
		return _banRecords.contains(record);
	}
	
	public List<PlayerBanRecord> GetBanRecords()
	{
		return _banRecords;
	}
	
	public void SetRecords(List<PlayerBanRecord> records)
	{
		_banRecords = records;
	}
}
