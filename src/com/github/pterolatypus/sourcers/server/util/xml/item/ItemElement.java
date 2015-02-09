package com.github.pterolatypus.sourcers.server.util.xml.item;

import org.jdom2.Element;

import com.github.pterolatypus.sourcers.server.model.items.Item;

public class ItemElement extends Element {
	
	private static final long serialVersionUID = 1412502660908144082L;
	
	public static String PARAM_ROOT_ELEMENT = "items";
	public static String PARAM_ELEMENT_NAME = "item";
	public static String PARAM_ID = "itemid";
	public static String PARAM_NAME = "name";
	public static String PARAM_DESCRIPTION = "description";
	public static String PARAM_VALUE = "value";
	public static String PARAM_LO_ALCH = "lowAlch";
	public static String PARAM_HI_ALCH = "hiAlch";
	public static String PARAM_BONUS_ROOT = "bonus";
	public static String PARAM_STACK_SIZE = "stacksize";

	public ItemElement(int itemID, String itemName,
			String description, int j, int k, int l,
			int[] bonuses, int stackSize) {
		super(PARAM_ELEMENT_NAME);

		this.addContent(new Element(PARAM_ID).addContent(String.valueOf(itemID)));
		this.addContent(new Element(PARAM_NAME).addContent(itemName.replaceAll("_",
				" ")));
		this.addContent(new Element(PARAM_DESCRIPTION).addContent(description
				.replaceAll("_", " ")));
		this.addContent(new Element(PARAM_VALUE).addContent(String
				.valueOf(j)));
		this.addContent(new Element(PARAM_LO_ALCH).addContent(String
				.valueOf(k)));
		this.addContent(new Element(PARAM_HI_ALCH).addContent(String
				.valueOf(l)));
		for (int i = 1; i <= bonuses.length; i++) {
			this.addContent(new Element(PARAM_BONUS_ROOT + i).addContent(String
					.valueOf(bonuses[i - 1])));
		}
		this.addContent(new Element(PARAM_STACK_SIZE).addContent(String.valueOf(stackSize)));
	}
	
	public ItemElement(Item item) {
		this(item.getItemID(), item.getItemName(), item.getDescription(), item.getShopValue(), item.getLowAlchValue(), item.getHiAlchValue(), item.getBonuses(), item.getStackSize());
	}

}
