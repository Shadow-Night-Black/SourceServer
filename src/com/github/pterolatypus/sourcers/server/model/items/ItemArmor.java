package com.github.pterolatypus.sourcers.server.model.items;

public class ItemArmor extends Item {

	public ItemArmor(int itemID, String itemName, String description,
			int shopValue, int lowAlchValue, int hiAlchValue, int[] bonuses, int stackSize) {
		super(itemID, itemName, description, shopValue, lowAlchValue, hiAlchValue,
				bonuses, stackSize);
	}

}
