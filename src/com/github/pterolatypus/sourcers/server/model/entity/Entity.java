package com.github.pterolatypus.sourcers.server.model.entity;

import java.awt.geom.Point2D;

public abstract class Entity {
	
	private int	worldX;
	private int	worldY;
	
	public Point2D getPos() {
		return new Point2D.Double(this.worldX, this.worldY);
	}
	
}
