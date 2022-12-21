package me.titan.painter.canvas.managers;

import me.titan.painter.canvas.CanvasScreen;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DrawManager {

	CanvasScreen canvasScreen;

	Map<Point, Rectangle> rects = new HashMap<>();

	int size = -1;

	public DrawManager(CanvasScreen canvasScreen) {
		this.canvasScreen = canvasScreen;
	}
	public void calcRects(int size){
		if(this.size == size) return;
		this.size = size;
		for(int x = 0;x<canvasScreen.getWidth();x+=size){
			for(int y = 0; y<canvasScreen.getHeight();y+=size){
				Point p = new Point(x,y);
				Rectangle rect = new Rectangle(p,new Dimension(size,size));
				rects.put(p,rect);
			}
		}

	}
	public Rectangle getRect(int x, int y){


		int xi = 0;
		while(x-size>xi){
			xi+=size;
		}
		int yi = 0;
		while(y-size>yi){
			yi+=size;
		}

		//System.out.println(rects);
		return rects.get(new Point(xi,yi));

	}

	public Map<Point, Rectangle> getRects() {
		return rects;
	}
}
