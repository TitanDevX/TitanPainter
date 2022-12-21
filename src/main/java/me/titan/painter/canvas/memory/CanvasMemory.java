package me.titan.painter.canvas.memory;

import me.titan.painter.tool.EnumTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasMemory {

	BufferedImage[] lastImages = new BufferedImage[5];
	int currentImage = 0;

	EnumTools previousTool;
	EnumTools currentTool;
	public CanvasMemory(){



	}

	public EnumTools getCurrentTool() {
		return currentTool;
	}

	public BufferedImage[] getLastImages() {
		return lastImages;
	}
	public void addImage(BufferedImage im){
		// 4
		// 5
		int n = currentImage+1;
		if(n >=  lastImages.length){
			for(int i =0;i<lastImages.length;i++){

				int ni = i-1;
				if(ni < 0) continue;
				lastImages[ni] = lastImages[i];
			}
			currentImage--;
		}
		// 3
		lastImages[++currentImage] = im;
	}
	public BufferedImage getCurrentImage(){
		if(currentImage < 0){
			currentImage =0;
		}
		return lastImages[currentImage];
	}
	public void previousImage(){

		if(currentImage == 0) return;
		currentImage--;
	}

	public EnumTools getPreviousTool() {
		return previousTool;
	}

	public void setPreviousTool(EnumTools previousTool) {
		this.previousTool = previousTool;
	}

	public void setCurrentTool(EnumTools currentTool) {
		this.currentTool = currentTool;
	}

	public void onPaintColorChange(int x, int y, Color nc){

		//currentDrawing.

	}

}
