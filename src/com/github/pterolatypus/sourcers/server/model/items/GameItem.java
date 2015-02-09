package com.github.pterolatypus.sourcers.server.model.items;

public class GameItem {
	public int id, amount;
	public boolean stackable = false;

	public GameItem(int id, int amount) {
		if (ItemO.itemStackable[id]) {
			stackable = true;
		}
		this.id = id;
		this.amount = amount;
	}
}