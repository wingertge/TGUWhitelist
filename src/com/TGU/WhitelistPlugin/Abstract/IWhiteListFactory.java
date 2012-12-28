package com.TGU.WhitelistPlugin.Abstract;

public interface IWhiteListFactory
{
	void Add(String playerName);
	void Remove(String playerName);
	boolean IsWhiteListed(String playerName);
	
	void Sync();
	void Sync(boolean forceOverwrite);
	void DiscardChanges();
}
