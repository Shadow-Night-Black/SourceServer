package com.github.pterolatypus.sourcers.server.error;

import com.github.pterolatypus.sourcers.server.model.Identifiable;

public class IDMismatchException extends Exception {

	private static final long serialVersionUID = 3434641525373914982L;
	
	String kind;
	
	public IDMismatchException(String kind, Identifiable cause) {
		super("Trying to add a new "+kind+" at index "+cause.getId()+" failed, the slot is already occupied.");
	}
	
}
