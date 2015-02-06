package com.github.pterolatypus.sourcers.server.util.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DoorConverter {

	private static DoorConverter instance;
	Document doors = new Document();
	
	public static DoorConverter getInstance() {
		if (instance == null) {
			return instance = new DoorConverter();
		}
		return null;
	}
	
	public DoorConverter() {
		doors.addContent(new Element("doors"));
	}
	
	public void abandon(){
		instance = null;
	}

	public void addDoor(String x, String y, String height, String face, String state) {
		
		Element door = new Element("door");
		
		door.addContent(new Element("x").addContent(String.valueOf(x)));
		door.addContent(new Element("y").addContent(String.valueOf(y)));
		door.addContent(new Element("height").addContent(String.valueOf(height)));
		door.addContent(new Element("face").addContent(String.valueOf(face)));
		door.addContent(new Element("state").addContent(String.valueOf(state)));
		
		doors.getRootElement().addContent(door);
	}
	
	public void save() {
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		try {
			File file = new File("./Data/cfg/doors.xml");
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			out.output(doors, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
