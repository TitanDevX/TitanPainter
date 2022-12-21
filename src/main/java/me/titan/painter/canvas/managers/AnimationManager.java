package me.titan.painter.canvas.managers;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.externalframes.AnimationViewer;
import me.titan.painter.util.Task;
import me.titan.painter.util.Util;

import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AnimationManager {

	Map<Integer, BufferedImage> frames = new HashMap<>();
	int LAST_INDEX = -1;
	private CanvasScreen canvasScreen;

	AnimationPlayer animationPlayer;

	public AnimationManager(CanvasScreen canvasScreen) {
		this.canvasScreen = canvasScreen;

		animationPlayer = new AnimationPlayer(this);
	}

	public void setCurrentFrame(int i) {
		BufferedImage current = canvasScreen.getFinalImage();
		current = Util.deepCopy(current);

		Util.saveImage(new File("test.png"),current,"png");
		if(frames.containsKey(i-1)){
			Util.saveImage(new File("test2.png"),frames.get(i-1),"png");
		}
		frames.put(i, current);

		LAST_INDEX = i;
	}
	public void addCurrentFrame(){
		setCurrentFrame(++LAST_INDEX);
	}
	public void removeLastFrame(){
		removeFrame(LAST_INDEX--);
	}
	public void removeFrame(int ind){
		Map<Integer, BufferedImage> nframes = new HashMap<>();
		for(int i =0;i<frames.size();i++){

			if(!frames.containsKey(i)) continue;
			if(i == ind){
				i--;
				continue;
			}
			nframes.put(i,frames.get(i));

		}
	}
	public void clearFrames(){
		frames.clear();
		getAnimationPlayer().reset();
		LAST_INDEX = -1;
	}

	public AnimationPlayer getAnimationPlayer() {
		return animationPlayer;
	}

	public class AnimationPlayer {

		AnimationManager animationManager;

		int currentFrame = -1;
		boolean playing;

		public AnimationPlayer(AnimationManager animationManager) {
			this.animationManager = animationManager;
		}

		public boolean isPlaying() {
			return playing;
		}

		public void setPlaying(boolean playing) {
			this.playing = playing;
		}
		public void startPlaying(CanvasScreen canvasScreen){
			reset();

			playing = true;
			AnimationViewer animationViewer = new AnimationViewer(canvasScreen);
			animationViewer.addWindowListener(new Util.SimpleWindowListener() {
				@Override
				public void windowClosing(WindowEvent e) {
					reset();
				}
			});
			animationViewer.drawAnimation();
			new Task() {
				@Override
				public void run() {
					if(!isPlaying()) {
						cancel();
						return;
					}


					if(!hasNext()){

						currentFrame = -1;
						//reset();
						//cancel();
					}
					next();
					System.out.println(currentFrame + " E " + frames.size());
					animationViewer.drawAnimation();
				}
			}.runTaskTimer(500,500, TimeUnit.MILLISECONDS);

		}

		public BufferedImage current(){

			System.out.println("current " + animationManager.frames.get(currentFrame) + " " + currentFrame);
			return animationManager.frames.get(currentFrame);
		}
		public BufferedImage next(){

			BufferedImage bi = animationManager.frames.get(++currentFrame);

			return bi;
		}
		public BufferedImage previous(){
			return animationManager.frames.get(--currentFrame);
		}
		public boolean hasNext(){
			return currentFrame < animationManager.frames.size()-1;
		}
		public void reset(){
			currentFrame = -1;
			playing = false;
		}
		public boolean isFirst(){
			return currentFrame == 0;
		}
	}

}
