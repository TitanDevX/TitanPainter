package me.titan.painter.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotatableObject {

	Point origin;
	BufferedImage image;

	public RotatableObject(Point origin, BufferedImage image) {
		this.origin = origin;
		this.image = image;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
