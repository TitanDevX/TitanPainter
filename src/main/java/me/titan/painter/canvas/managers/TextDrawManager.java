package me.titan.painter.canvas.managers;

import me.titan.painter.canvas.CanvasScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextDrawManager {

	BufferedImage textImg;
	Graphics2D textGraphics;

	private  CanvasScreen canvasScreen;
	Map<Integer, DrawText> texts = new HashMap<>();

	public TextDrawManager(CanvasScreen canvasScreen){
		this.canvasScreen = canvasScreen;

		textImg = new BufferedImage(canvasScreen.getWidth(),canvasScreen.getHeight(),BufferedImage.TYPE_INT_ARGB);
		textGraphics = textImg.createGraphics();
		textGraphics.setBackground(canvasScreen.getBackground());
	}
	private void drawString(DrawText text){
		//textGraphics.clearRect(text.getX(),text.getY()-10,text.getText().length()*10,text.getText().length()*10);
		textGraphics.setColor(text.getColor());
		textGraphics.drawString(text.getText(),text.getX(),text.getY());
	}
	public BufferedImage getTextImg(){
		textImg = new BufferedImage(canvasScreen.getWidth(),canvasScreen.getHeight(),BufferedImage.TYPE_INT_ARGB);
		textGraphics = textImg.createGraphics();
		textGraphics.setBackground(canvasScreen.getBackground());
		for(DrawText t : getTexts().values()){
			drawString(t);
		}
		//Util.saveImage(new File("texts.png"),textImg,"png");
		return textImg;
	}


	public void addText(DrawText text){
		texts.put(text.id, text);
		canvasScreen.clearAndPaint();
	}
	public void removeText(int id){

		if(!texts.containsKey(id)) return;
		DrawText text = texts.get(id);
		//textGraphics.clearRect(text.getX()-1,text.getY()-1,text.getText().length()*50,text.getText().length()*10);
		texts.remove(id);
		canvasScreen.clearAndPaint();
	}

	public Map<Integer, DrawText> getTexts() {
		return texts;
	}
}

