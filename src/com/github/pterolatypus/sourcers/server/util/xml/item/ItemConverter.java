package com.github.pterolatypus.sourcers.server.util.xml.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.github.pterolatypus.sourcers.server.Config;

public class ItemConverter {

	private static ItemConverter instance;
	private Document items;
	
	public static ItemConverter getInstance() {
		if (instance == null) {
			return instance = new ItemConverter();
		}
		return null;
	}
	
	public ItemConverter() {
		items = new Document();
		items.addContent(new Element(ItemElement.PARAM_ROOT_ELEMENT));
	}
	
	public void abandon() {
		instance = null;
	}

	public void addItem(int id, String name, String desc, int shopVal,
			int lowAlch, int hiAlch, int[] bonuses, int stackSize) {
		
		items.getRootElement().addContent(new ItemElement(id, name, desc, shopVal, lowAlch, hiAlch, bonuses, stackSize));
	}
	
	public void save() {
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		try {
			File file = new File(Config.PATH_ITEM_XML);
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			out.output(items, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
