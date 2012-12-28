package com.TGU.WhitelistPlugin.Abstract;

public interface IBanListFactory
{
	void Add(String playerName, String bannedBy, int duration, String ip);
	void Add(String playerName, String bannedBy, int duration, String ip, String reason);
	void ResetBanDuration(String playerName, int duration);
	void Remove(String playerName);
	boolean IsBanned(String playerName);
	String GetBanReason(String playerName);
	
	void Sync();
	void Sync(boolean forceOverwrite);
	void DiscardChanges();
}
