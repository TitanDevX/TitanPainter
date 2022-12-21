package me.titan.painter.canvas;

import me.titan.painter.canvas.managers.*;
import me.titan.painter.util.ProjectImages;
import me.titan.painter.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CanvasScreen extends Canvas {


	Graphics2D imageGraphics;
	BufferedImage mainImg;
	BufferedImage selectorImg;
	BufferedImage selectedImg;

	TextDrawManager textDrawManager;
	AnimationManager animationManager;

	SelectionManager selectionManager;

	DrawManager drawManager;
	FillManager fillManager;


	JMenu menu;
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int sX, sY;
	public CanvasScreen(){
		setSize(new Dimension((screenSize.width-200),screenSize.height));
		//setLayout(null);

		setFocusable(true);
		setLocation(200,0);
		setVisible(true);

		mainImg = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		imageGraphics = mainImg.createGraphics();
		imageGraphics.setBackground(getBackground());

		textDrawManager = new TextDrawManager(this);
		animationManager = new AnimationManager(this);
		selectionManager = new SelectionManager(this);
		drawManager = new DrawManager(this);
		drawManager.calcRects(1);

		fillManager = new FillManager(this);

	}


	boolean shouldClear;

	public void reset(){
		imageGraphics.clearRect(0,0,getWidth(),getHeight());
		paint(getGraphics());
		mainImg = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		imageGraphics = mainImg.createGraphics();
		imageGraphics.setBackground(getBackground());

		paint(getGraphics());
	}
	public void drawString(DrawText text){

		textDrawManager.addText(text);

	}

	public TextDrawManager getTextDrawManager() {
		return textDrawManager;
	}
	public static BufferStrategy bufferStrategy;
	public static Graphics2D graphics;
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
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		//graphics.clearRect(0, 0, getWidth(),getHeight());
		return true;
	}
	@Override
	public void paint(Graphics g) {

		preRender();

		g = graphics;
		//super.paint(g);
//		System.out.println("===============================");
//		Arrays.stream(Thread.currentThread().getStackTrace()).forEach((s) -> {
//
//			System.out.println("STACK " + s.getClassName() + ":" + s.getMethodName() + " [" + s.getLineNumber() + "]");
//		});
//		System.out.println("===============================");
		if(shouldClear){
			g.clearRect(0,0,getWidth(),getHeight());
			shouldClear = false;
		}
//		if(animationManager.getAnimationPlayer().isPlaying() ){
//			g.clearRect(0,0,getWidth(),getHeight());
//			g.drawImage(animationManager.getAnimationPlayer().current(), 0,0,this);
//			return;
//		}
		//


		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		selectionManager.paint(graphics);
		g.drawImage(mainImg,0,0,null);


//		if(selectorImg != null){
//			g.drawImage(selectorImg,200,0,null);
//		}
//
//		if(selectedImg != null){
//			g.drawImage(selectedImg,200,0,null);
//		}
		//g.drawImage(textDrawManager.getTextImg(),0,0,null);


		g.dispose();
	    bufferStrategy.show();

	}
	public BufferedImage getSubImg(Point p, int width, int height){
		return mainImg.getSubimage(p.x,
				p.y,
				width,height);
	}

	public void setMenu(JMenu menu) {
		this.menu = menu;
	}

	public void clearAndPaint(){
		shouldClear = true;
		paint(getGraphics());
	}
	public void paint(){
		paint(getGraphics());
	}

	public Color getColor(int x, int y){
		BufferedImage img = getFinalImage();
		int pixel = img.getRGB(x,y);
		int r = img.getColorModel().getRed(pixel);
		int g = img.getColorModel().getGreen(pixel);
		int b = img.getColorModel().getBlue(pixel);
		return new Color(r,g,b);
	}

	public void combineSelectorLayers(){
		BufferedImage mc = Util.deepCopy(mainImg);
		Graphics g = mc.createGraphics();
		if(selectedImg != null){
			g.drawImage(selectedImg,0,0,null);
		}

		selectedImg = null;
		selectorImg = null;
		mainImg = mc;
		imageGraphics = mainImg.createGraphics();
		shouldClear = true;
		paint(getGraphics());
	}
	public BufferedImage getFinalImage(){

		BufferedImage mc = Util.deepCopy(mainImg);
		Graphics g = mc.createGraphics();
		if(selectedImg != null){
			g.drawImage(selectedImg,0,0,null);
		}
		if(selectorImg != null){
			g.drawImage(selectorImg,0,0,null);
		}
		for(BufferedImage img : getImages().getImages().values()){
			g.drawImage(img,0,0,null);
		}
		return mc;

	}
	public void paintPixel(int x, int y, int width, int height, Color color){
		imageGraphics.setColor(color);
		imageGraphics.fillRect(x,y,width,height);
		paint(getGraphics());
	}

	public void combine(){

		if(selectedImg != null){
			imageGraphics.drawImage(selectedImg,0,0,this);
		}
		selectedImg = null;
		//paint(getGraphics());
	}
	public ProjectImages getImages(){

		Map<Integer, BufferedImage> map = new HashMap<>();
		map.put(0,mainImg);
		return new ProjectImages(map);

	}
	public void setImages(ProjectImages images){
		mainImg = images.getImages().get(0);
		imageGraphics = mainImg.createGraphics();
		paint(getGraphics());

	}

	public FillManager getFillManager() {
		return fillManager;
	}

	public void drawImg(int x, int y, BufferedImage img){
		imageGraphics.drawImage(img,x,y,null);
	}
	public void setComposite(Composite comp){
		imageGraphics.setComposite(comp);
	}
	public boolean erase(int x, int y, int width, int height){
		imageGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		imageGraphics.setColor(getBackground());
		imageGraphics.fillRect(x,y,width,height);
		imageGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		clearAndPaint();
		return true;
	}

	
	public BufferedImage getImage(){
		return Util.deepCopy(mainImg);
	}
	public void fromImage(BufferedImage image){
		mainImg = image;
		imageGraphics = (Graphics2D) mainImg.getGraphics();
		clearAndPaint();
	}


	public AnimationManager getAnimationManager() {
		return animationManager;
	}

	//	int oldX,oldY;
//	public void updateMousePos(int x, int y){
//
//
//			getGraphics().clearRect(oldX,oldY,20,20);
//		if(painted[x][y] == 1){
//			getGraphics().fillRect(oldX,oldY, TitanPainterApp.instance.getBasicTools().getBrushSize(), TitanPainterApp.instance.getBasicTools().getBrushSize());
//		}
//		getGraphics().drawRect(x, y, 10,10);
//		oldX = x;
//		oldY = y;
//
//	}


	public DrawManager getDrawManager() {
		return drawManager;
	}

	public SelectionManager getSelectionManager() {
		return selectionManager;
	}

	public void dispose(){
		getGraphics().dispose();
	}
}
