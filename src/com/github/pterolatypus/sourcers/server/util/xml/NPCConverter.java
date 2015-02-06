package com.github.pterolatypus.sourcers.server.util.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class NPCConverter {

	private static NPCConverter instance;
	Document npcs = new Document();
	
	public static NPCConverter getInstance() {
		if (instance == null) {
			return instance = new NPCConverter();
		}
		return null;
	}
	
	public NPCConverter() {
		npcs.addContent(new Element("npcs"));
	}
	
	public void abandon() {
		instance = null;
	}

	public void addNpc(int id, String name, int combat, int health) {
		
		Element item = new Element("npc");
		
		item.addContent(new Element("npcid").addContent(String.valueOf(id)));
		item.addContent(new Element("name").addContent(name.replaceAll("_", " ")));
		item.addContent(new Element("combat").addContent(String.valueOf(combat)));
		item.addContent(new Element("health").addContent(String.valueOf(health)));
		
		npcs.getRootElement().addContent(item);
	}
	
	public void save() {
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		try {
			File file = new File("./Data/cfg/npcs.xml");
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			out.output(npcs, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
