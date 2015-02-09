package com.github.pterolatypus.sourcers.server.util.xml.item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.github.pterolatypus.sourcers.server.Config;

public class ItemLoader {
	
	SAXBuilder	builder	= new SAXBuilder();
	Document	doc;
	String		path;
	
	public ItemLoader(String path) {
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
	
	public Element getItem(int id) {
		return doc.getRootElement().getChildren().get(id);
	}
	
	public int size() {
		return doc.getRootElement().getChildren().size();
	}
	
	public void save() throws IOException {
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		File file = new File(Config.PATH_ITEM_XML);
		if (!file.exists()) {
			file.createNewFile();
		}
		out.output(doc, new FileOutputStream(file));
	}
	
}
