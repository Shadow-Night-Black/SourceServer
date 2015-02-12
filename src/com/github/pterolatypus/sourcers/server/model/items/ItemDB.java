package com.github.pterolatypus.sourcers.server.model.items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.github.pterolatypus.sourcers.server.Config;

public class ItemDB {
	
	public static String	PARAM_ROOT_ELEMENT	= "items";
	public static String	PARAM_ELEMENT_NAME	= "item";
	public static String	PARAM_ID			= "itemid";
	public static String	PARAM_NAME			= "name";
	public static String	PARAM_DESCRIPTION	= "description";
	public static String	PARAM_VALUE			= "value";
	public static String	PARAM_LO_ALCH		= "lowAlch";
	public static String	PARAM_HI_ALCH		= "hiAlch";
	public static String	PARAM_BONUS_ROOT	= "bonus";
	public static String	PARAM_STACK_SIZE	= "stacksize";
	
	SAXBuilder				builder				= new SAXBuilder();
	Document				doc;
	String					path;
	Map<Integer, Item>		itemList			= new HashMap<Integer, Item>(
														Config.ITEM_LIMIT);
	private static ItemDB	database;
	
	private ItemDB(String path) {
		this.path = path;
		reloadConfig();
	}
	
	public static boolean init(String path) {
		if (database == null) {
			database = new ItemDB(path);
			return true;
		}
		return false;
	}
	
	public static ItemDB getDatabase() {
		return database;
	}
	
	public Item getItemInList(int i) {
		return (itemList.containsKey(i)) ? itemList.get(i) : new Item(true);
	}
	
	public boolean hasItemInList(int i) {
		return itemList.containsKey(i);
	}
	
	public void addItemInList(int i, Item item) {
		if (!itemList.containsKey(i)) {
			itemList.put(i, item);
		}
	}
	
	public void reloadConfig() {
		try {
			doc = builder.build(new File(path));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Element e : doc.getRootElement().getChildren()) {
			int id = Integer.parseInt(e.getChildText(PARAM_ID));
			String name = e.getChildText(PARAM_NAME);
			String desc = e.getChildText(PARAM_DESCRIPTION);
			int val = Integer.parseInt(e.getChildText(PARAM_VALUE));
			int lo = Integer.parseInt(e.getChildText(PARAM_LO_ALCH));
			int hi = Integer.parseInt(e.getChildText(PARAM_HI_ALCH));
			int[] bonuses = new int[12];
			for (int i = 0; i < 12; i++) {
				bonuses[i] = Integer.parseInt(e.getChildText(PARAM_BONUS_ROOT
						+ (i + 1)));
			}
			int stack = Integer.parseInt(e.getChildText(PARAM_STACK_SIZE));
			
			addItemInList(id, new Item(id, name, desc, val, lo, hi, bonuses,
					stack));
		}
		
		doc = null;
	}
	
	public void save() throws IOException {
		doc = new Document().setRootElement(new Element(PARAM_ROOT_ELEMENT));
		for (Item el : itemList.values()) {
			doc.getRootElement().addContent(el.getConfigElement());
		}
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		File file = new File(Config.PATH_ITEM_XML);
		if (!file.exists()) {
			file.createNewFile();
		}
		out.output(doc, new FileOutputStream(file));
	}
	
}
