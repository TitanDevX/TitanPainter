package me.titan.painter.canvas.managers;

import me.titan.painter.canvas.CanvasScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FillManager {

	CanvasScreen canvasScreen;

	public FillManager(CanvasScreen canvasScreen) {
		this.canvasScreen = canvasScreen;
	}
	public int testPixel(BufferedImage mainImg, int x, int y, Color color, Color background){
		//for(int i =0;i<4;i++) {
			//int addX = 0, addY = 0;
//			if (i == 1) {
//				addX = 1;
//			} else if (i == 2) {
//				addY = 1;
//			} else if (i == 3) {
//				addX = 1;
//				addY = 1;
//			}
			//System.out.println("F " + x + " "+ y + " "+ x + " " + yi);
			int ax = x;// - addX;
			int ay = y; //- addY;
			if (ax >= canvasScreen.getWidth() || ay >= canvasScreen.getHeight()) return -1;
			int rgp = mainImg.getRGB(ax, ay);
			if (rgp != 0 && rgp != background.getRGB()) {
				return -1;
			}

		return 1;
	}
	private boolean fillOnY(BufferedImage mainImg, int x, int y, Color color, Color background){

		for(int yi = y+1;yi<canvasScreen.getHeight();yi++) {
			int test = testPixel(mainImg,x,yi,color, background);
			if(test == 1){
				mainImg.setRGB(x, yi,color.getRGB());
			}else if(test == 0){
				mainImg.setRGB(x, yi,color.getRGB());
				//break;
			}else{
				break;
			}

		}
		for(int yi = y-1;yi>0;yi--) {
			int test = testPixel(mainImg,x,yi,color, background);
			if(test == 1){
				mainImg.setRGB(x, yi,color.getRGB());
			}else if(test == 0){
				mainImg.setRGB(x, yi,color.getRGB());
				//break;
			}else{
				break;
			}
		}
		return true;
	}
	public void fill(int x, int y, Color color){

		BufferedImage mainImg = canvasScreen.getImage();
		Color background = new Color(mainImg.getRGB(x,y));
		for(int xi = x;xi<canvasScreen.getWidth();xi++){
			int test = testPixel(mainImg,xi,y,color,background);
			if(test == 1){
				mainImg.setRGB(xi, y,color.getRGB());
			}else if(test == 0){
				mainImg.setRGB(xi, y,color.getRGB());
				//break;
			}else{
				break;
			}
			fillOnY(mainImg,xi,y,color,background);
		}
		for(int xi = x-1;xi>0;xi--){
			int test = testPixel(mainImg,xi,y,color,background);
			if(test == 1){
				mainImg.setRGB(xi, y,color.getRGB());
			}else if(test == 0){
				mainImg.setRGB(xi, y,color.getRGB());
				//break;
			}else{
				break;
			}
			fillOnY(mainImg,xi,y,color,background);
		}
		canvasScreen.fromImage(mainImg);

	}
}
