package com.github.pterolatypus.sourcers.server.util.xml.item;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ItemLoader {
	
	SAXBuilder builder = new SAXBuilder();
	Document doc;
	String path;

	public ItemLoader(String path) {
		this.path = path;
		reloadConfig();
	}

	public void reloadConfig() {
		try {
			doc = builder.build(new File(path));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

	public ItemElement getItem(int id) {
		return (ItemElement) doc.getRootElement().getChildren().get(id);
	}
	
	public int size() {
		return doc.getRootElement().getChildren().size();
	}

}
