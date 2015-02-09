package com.github.pterolatypus.sourcers.server.model.items;

public class Item {

	private int itemID = -1;
	private String itemName = "Null";
	private String description = "Null item, you shouldn't have one of these!";
	private double shopValue = 0;
	private double lowAlchValue = 0;
	private double hiAlchValue = 0;
	private int[] bonuses = new int[12];

	public Item() throws InstantiationException {
		throw new InstantiationException("You can't call the default constructor for this class; pass an arbitrary boolean to it to confirm you want a blank instance!");
	}
	
	public Item(boolean bNull) {
		super();
	}
	
	public Item(int itemID, String itemName, String description, double val,
			double loA, double hiA, int[] bonuses) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.description = description;
		this.shopValue = val;
		this.lowAlchValue = loA;
		this.hiAlchValue = hiA;
		this.bonuses = bonuses;
	}

	public int getItemID() {
		return itemID;
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

}
