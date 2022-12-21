package me.titan.painter.canvas.managers;

import java.awt.*;

public class DrawText {

	int id;
	int x,y;
	Color color;
	String text;

	public DrawText(int id, int x, int y, Color color, String text) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
