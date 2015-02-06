package com.github.pterolatypus.sourcers.server.util.xml.item;

import org.jdom2.Element;

import com.github.pterolatypus.sourcers.server.model.items.Item;

public class ItemElement extends Element {
	
	public static String PARAM_ROOT_ELEMENT = "items";
	public static String PARAM_ELEMENT_NAME = "item";
	public static String PARAM_ID = "itemid";
	public static String PARAM_NAME = "name";
	public static String PARAM_DESCRIPTION = "description";
	public static String PARAM_VALUE = "value";
	public static String PARAM_LO_ALCH = "lowAlch";
	public static String PARAM_HI_ALCH = "hiAlch";
	public static String PARAM_BONUS_ROOT = "bonus";
	public static String PARAM_TYPE = "type";

	private int id;
	private double val;
	private double loA;
	private double hiA;
	private int[] bonuses;

	public ItemElement(int itemID, String itemName, String description,
			Double shopVal, Double lowAlch, Double hiAlch, int[] bonuses) {
		this(null, itemID, itemName, description, shopVal, lowAlch, hiAlch,
				bonuses);
	}

	public ItemElement(String type, int itemID, String itemName,
			String description, Double shopVal, Double lowAlch, Double hiAlch,
			int[] bonuses) {
		super(PARAM_ELEMENT_NAME);
		if (type != null) {
			this.setAttribute(PARAM_TYPE, type);
		}

		id = itemID;
		val = shopVal;
		loA = lowAlch;
		hiA = hiAlch;
		this.bonuses = bonuses;

		this.addContent(new Element(PARAM_ID).addContent(String.valueOf(itemID)));
		this.addContent(new Element(PARAM_NAME).addContent(name.replaceAll("_",
				" ")));
		this.addContent(new Element(PARAM_DESCRIPTION).addContent(description
				.replaceAll("_", " ")));
		this.addContent(new Element(PARAM_VALUE).addContent(String
				.valueOf(shopVal)));
		this.addContent(new Element(PARAM_LO_ALCH).addContent(String
				.valueOf(lowAlch)));
		this.addContent(new Element(PARAM_HI_ALCH).addContent(String
				.valueOf(hiAlch)));
		for (int i = 1; i <= bonuses.length; i++) {
			this.addContent(new Element(PARAM_BONUS_ROOT + i).addContent(String
					.valueOf(bonuses[i - 1])));
		}
	}

	private static final long serialVersionUID = 1412502660908144082L;

	public Item constructItem() {
		Item item = null;
		if (!this.hasAttributes()) {
			item = new Item(id, this.getChildText(PARAM_NAME),
					this.getChildText(PARAM_DESCRIPTION), val, loA, hiA,
					bonuses);
		} else {
			String type = this.getAttributeValue(PARAM_TYPE);
		}
		return item;
	}

}
