package me.titan.painter.tool.tools;

import me.titan.painter.ScreenManager;
import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.externalframes.AnimatorMenu;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnimatorTool extends Tool implements KeyListener {

	private CanvasScreen canvasScreen;

	boolean recording;
	AnimatorMenu menu;

	public AnimatorTool(CanvasScreen canvasScreen, ScreenManager screen) {
		this.canvasScreen = canvasScreen;

		menu = new AnimatorMenu(this);
		screen.add(menu);
		screen.addKeyListener(menu);
		screen.addMouseListener(menu);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_M && !e.isAltDown()){
			EnumTools.setCurrentTool(EnumTools.ANIMATOR);




			menu.setLocation(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
			menu.setVisible(true);
			//canvasScreen.paint();

			//canvasScreen.getAnimationManager().addCurrentFrame();
			//canvasScreen.drawString(new DrawText(2, canvasScreen.getWidth()/2,canvasScreen.getHeight()/2, Color.BLACK,"Added Animation Frame"));
		}else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_M && e.isAltDown()){
			System.out.println("PLAYINh");
			canvasScreen.getAnimationManager().getAnimationPlayer().startPlaying(canvasScreen);
		}

	}
	public void startRecording(){
		menu.setVisible(false);
		canvasScreen.getAnimationManager().clearFrames();
		setRecording(true);
	}
	public void addLayer(){
		menu.setVisible(false);
		canvasScreen.getAnimationManager().addCurrentFrame();
	}
	public void removeLayer(){
		menu.setVisible(false);
		canvasScreen.getAnimationManager().removeLastFrame();
	}
	public void playAnimation(){
		if(!isRecording()) return;
		menu.setVisible(false);
		canvasScreen.getAnimationManager().getAnimationPlayer().startPlaying(canvasScreen);

	}
	public void stopAnimation(){
		if(!isPlayingAnimation()) return;
		menu.setVisible(false);
		canvasScreen.getAnimationManager().getAnimationPlayer().reset();
	}
	public boolean isPlayingAnimation(){
		return canvasScreen.getAnimationManager().getAnimationPlayer().isPlaying();
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}
}
