package com.github.pterolatypus.sourcers.server.world;

import com.github.pterolatypus.sourcers.server.model.players.Client;

/**
 * @author Sanity
 */

public class Clan {

	public Clan(Client c, String name) {
		this.owner = c.playerName;
		this.name = name;
	}

	public int[] members = new int[50];
	public String name;
	public String owner;
	public boolean lootshare;
}