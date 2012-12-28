package com.TGU.WhitelistPlugin.Factories;

import com.TGU.WhitelistPlugin.Abstract.IBanListFactory;

public class MySqlBanListFactory implements IBanListFactory
{
	public MySqlBanListFactory(String url)
	{
		
	}
	
	public void Add(String playerName, String bannedBy, int duration, String ip) {
		
		
	}

	
	public void Add(String playerName, String bannedBy, int duration, String ip, String reason) {
		
		
	}

	
	public void Remove(String playerName) {
		
		
	}

	
	public boolean IsBanned(String playerName) {
		
		return false;
	}

	
	public String GetBanReason(String playerName) {
		
		return null;
	}

	
	public void Sync() {
		
		
	}

	
	public void Sync(boolean forceOverwrite) {
		
		
	}

	
	public void DiscardChanges() {
		
		
	}

	public void ResetBanDuration(String playerName, int duration) {
		
	}
}
