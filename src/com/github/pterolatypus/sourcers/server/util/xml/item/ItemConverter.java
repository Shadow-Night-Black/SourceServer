package com.github.pterolatypus.sourcers.server.util.xml.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ItemConverter {

	private static ItemConverter instance;
	Document items = new Document();
	
	public static ItemConverter getInstance() {
		if (instance == null) {
			return instance = new ItemConverter();
		}
		return null;
	}
	
	public ItemConverter() {
		items.addContent(new Element("items"));
	}
	
	public void abandon() {
		instance = null;
	}

	public void addItem(int id, String name, String desc, Double shopVal,
			Double lowAlch, Double hiAlch, int[] bonuses) {
		
		items.getRootElement().addContent(new ItemElement(id, name, desc, shopVal, lowAlch, hiAlch, bonuses));
	}
	
	public void save() {
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		try {
			File file = new File("./Data/cfg/items.xml");
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
