package com.github.pterolatypus.sourcers.server.model.items;

import com.github.pterolatypus.sourcers.server.model.Identifiable;

public class Item implements Identifiable {

	private int itemID = -1;
	private String itemName = "Null";
	private String description = "Null item, you shouldn't have one of these!";
	private int shopValue = 0;
	private int lowAlchValue = 0;
	private int hiAlchValue = 0;
	private int[] bonuses = new int[12];
	int stackSize = 1;

	public Item() throws InstantiationException {
		throw new InstantiationException(
				"You can't call the default constructor for this class; pass an arbitrary boolean to it to confirm you want a blank instance!");
	}

	public Item(boolean bNull) {
		super();
	}

	public Item(int itemID, String itemName, String description, int val,
			int loA, int hiA, int[] bonuses, int stackSize) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.description = description;
		this.shopValue = val;
		this.lowAlchValue = loA;
		this.hiAlchValue = hiA;
		this.bonuses = bonuses;
		this.stackSize = (stackSize == 0) ? 1 : stackSize;
	}

	public int getItemID() {
		return itemID;
	}

	public int getId() {
		return this.getItemID();
	}

	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}

	public int getShopValue() {
		return (int) shopValue;
	}

	public int getLowAlchValue() {
		return (int) lowAlchValue;
	}

	public int getHiAlchValue() {
		return (int) hiAlchValue;
	}

	public int[] getBonuses() {
		return bonuses;
	}

	public int getStackSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
