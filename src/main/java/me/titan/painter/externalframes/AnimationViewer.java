package me.titan.painter.externalframes;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.canvas.managers.AnimationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class AnimationViewer extends JFrame {

	CanvasScreen canvasScreen;
	public AnimationViewer(CanvasScreen canvasScreen){
		this.canvasScreen = canvasScreen;
		setTitle("Animation Viewer");
		setFocusable(true);
		setSize(canvasScreen.getWidth(),canvasScreen.getHeight());
		setVisible(true);



	}
	BufferStrategy bufferStrategy;
	Graphics graphics;
	boolean b = true;
	public boolean preRender(){
		if (b) {
			if (getBufferStrategy() != null)
				getBufferStrategy().dispose();
			createBufferStrategy(2);
			b = false;
		}
		//b = true;
		bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) return false;
		graphics = bufferStrategy.getDrawGraphics();
		//graphics.clearRect(0, 0, getWidth(),getHeight());
		return true;
	}
	public void drawAnimation(){
		preRender();
		AnimationManager animationManager = canvasScreen.getAnimationManager();
		if(animationManager.getAnimationPlayer().isPlaying() ){
			graphics.clearRect(0,0,getWidth(),getHeight());
			graphics.drawImage(animationManager.getAnimationPlayer().current(), 0,0,this);

		}
		graphics.dispose();
		bufferStrategy.show();
	}



}
