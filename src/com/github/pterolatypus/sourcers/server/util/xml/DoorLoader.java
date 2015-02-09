package com.github.pterolatypus.sourcers.server.util.xml;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class DoorLoader {
	
	SAXBuilder	builder	= new SAXBuilder();
	Document	doc;
	String		path;
	
	public DoorLoader(String path) {
		this.path = path;
		reloadConfig();
	}
	
	public void reloadConfig() {
		try {
			doc = builder.build(new File(path));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Element getDoor(int id) {
		return (Element) doc.getRootElement().getChildren().get(id);
	}
	
	public int size() {
		return doc.getRootElement().getChildren().size();
	}
	
}
