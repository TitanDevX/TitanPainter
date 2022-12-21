package me.titan.painter.canvas.managers;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.util.MouseManager;
import me.titan.painter.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class SelectionManager {

	BufferedImage borderImg;
	BufferedImage selectedImg;
	CanvasScreen canvasScreen;
	BufferedImage selectedImg0;

	boolean rotating;

	public SelectionManager(CanvasScreen canvasScreen){
		this.canvasScreen = canvasScreen;
	}

	public void updateSelectionBorder(Point start, int width, int height){
		if(start == null || width <= 0 || height <= 0){
			borderImg = null;
		}else{
			borderImg = new BufferedImage(canvasScreen.getWidth(),canvasScreen.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = borderImg.createGraphics();
			g.setColor(Color.WHITE);
			g.drawRect(start.x-1, start.y-1, width+1,height+1);
			g.dispose();
		}

		canvasScreen.clearAndPaint();
	}
	public boolean transformSelection(Point start, int width, int height, Point to, boolean place){

		boolean firstTime = selectedImg == null;
		if(selectedImg == null){
			selectedImg = canvasScreen.getGraphicsConfiguration().createCompatibleImage(canvasScreen.getWidth(),canvasScreen.getHeight(),Transparency.TRANSLUCENT);
		}

		Graphics2D g = selectedImg.createGraphics();
		g.setBackground(canvasScreen.getBackground());
		BufferedImage sub= null;

		try{
			if(firstTime){
				sub = Util.childDeepCopy(canvasScreen.getImage(), canvasScreen.getSubImg(start,width,height));
			}else{
				sub = Util.childDeepCopy(selectedImg, selectedImg.getSubimage(start.x,start.y,width, height));
			}

		}catch (RasterFormatException ex){
			//to.x -= 100;
			//to.y -= 100;
			//return transformSelection(start,width,height,to, place);
			return false;
		}


		if(firstTime){
			canvasScreen.erase(MouseManager.toCompX(start.x),MouseManager.toCompY(start.y),width,height);
		}else{
			if(!place) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				g.clearRect(MouseManager.toCompX(start.x), MouseManager.toCompY(start.y), width, height);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			}
		}
		if(place){
			canvasScreen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			BufferedImage small = selectedImg.getSubimage(start.x,start.y,width,height);
			small = Util.childDeepCopy(selectedImg,small);
			BufferedImage mainImg= canvasScreen.getImage();
			for(int x = 0;x<small.getWidth();x++){
				for(int y = 0;y<small.getHeight();y++){
					int nx = to.x+x;
					int ny = to.y+y;
					int rgp = small.getRGB(x,y);
					if(rgp != 0 && rgp != canvasScreen.getBackground().getRGB()){
						mainImg.setRGB(nx,ny,small.getRGB(x,y));
					}
				}
			}
			canvasScreen.fromImage(mainImg);
			//canvasScreen.drawImg(to.x,to.y,small);
			selectedImg = null;
			borderImg = null;
			canvasScreen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			canvasScreen.clearAndPaint();
		}else{
			g.drawImage(sub, to.x,to.y,canvasScreen);

			g.dispose();
			updateSelectionBorder(to, width,height);
		}

		return true;
	}
	public void reset(){
		selectedImg = null;
		selectedImg0 = null;
		rotating = false;
		borderImg = null;
		placeLastRotatedImg();
		canvasScreen.clearAndPaint();
	}





//	public void beginRotate(){
//		rotating = true;
//	}

	public Rectangle getRotationSelection(Point startingPoint, int width, int height){
		if(selectedImg == null){
			selectedImg = canvasScreen.getGraphicsConfiguration().createCompatibleImage(canvasScreen.getWidth(),canvasScreen.getHeight(),Transparency.TRANSLUCENT);
			BufferedImage sub = Util.childDeepCopy(canvasScreen.getImage(), canvasScreen.getImage().getSubimage(startingPoint.x,startingPoint.y,width,height));
			Graphics2D g = selectedImg.createGraphics();
			g.drawImage(sub,startingPoint.x,startingPoint.y,canvasScreen);
			g.dispose();
		}
		return Util.getActualPaintedRect(selectedImg,startingPoint.x-(width/2),startingPoint.y-(height/2),width*2,height*2);
	}
	BufferedImage lastRotatedImg;
	public Rectangle rotateSelected(Point startingPoint,int width, int height, double theta, boolean place) {

		System.out.println("ERSD");
		if(selectedImg == null){
			selectedImg = canvasScreen.getGraphicsConfiguration().createCompatibleImage(canvasScreen.getWidth(),canvasScreen.getHeight(),Transparency.TRANSLUCENT);
			BufferedImage sub = Util.childDeepCopy(canvasScreen.getImage(), canvasScreen.getImage().getSubimage(startingPoint.x,startingPoint.y,width,height));
			Graphics2D g = selectedImg.createGraphics();
			g.drawImage(sub,startingPoint.x,startingPoint.y,canvasScreen);
			g.dispose();
			canvasScreen.erase(startingPoint.x,startingPoint.y,width,height);
		}
		if(selectedImg0 == null){
			selectedImg0 = Util.deepCopy(selectedImg);
		}
		BufferedImage si = null;
		if(place){
			si = selectedImg0;
		}else{
			si = selectedImg;
		}
		BufferedImage rotatedImg = canvasScreen.getGraphicsConfiguration().createCompatibleImage(canvasScreen.getWidth(),canvasScreen.getHeight(),Transparency.TRANSLUCENT);
		BufferedImage e = Util.childDeepCopy(si, si.getSubimage(startingPoint.x,startingPoint.y,width,height));
		//int max = Math.max(width,height);
		//BufferedImage sub = canvasScreen.getGraphicsConfiguration().createCompatibleImage(max,max,Transparency.TRANSLUCENT);//
		Graphics2D subG = rotatedImg.createGraphics();
		Rectangle rect = new Rectangle(startingPoint,new Dimension(width,height));
		subG.rotate(theta, rect.getCenterX(),rect.getCenterY());

		subG.drawImage(e,startingPoint.x,startingPoint.y,null);
//		Util.saveImage(new File("SUBe.png"),e,"png");
//		Util.saveImage(new File("SUB.png"),rotatedImg,"png");
		//BufferedImage r = Util.rotateSimple(sub,startingPoint, theta);

		//sub = r;
	//	//System.out.println("RO " + r.getOrigin() + " " + startingPoint);
		Graphics2D g = selectedImg.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(startingPoint.x,startingPoint.y,width,height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		canvasScreen.erase(startingPoint.x,startingPoint.y,width,height);
		if(!place){
			lastRotatedImg = rotatedImg;
			g.drawImage(rotatedImg, 0,0,canvasScreen);
		}else{
			canvasScreen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			canvasScreen.drawImg(0,0,rotatedImg);
			selectedImg = null;
			borderImg = null;
		}
		if(place){
			selectedImg0 = null;
			lastRotatedImg = null;
		}
		canvasScreen.clearAndPaint();

		if(!place){
			return Util.getActualPaintedRect(selectedImg, startingPoint.x-(width),  startingPoint.y-(height), (int) (4*width),4* height);
		}
		return null;
	}
	public void placeLastRotatedImg(){
		if(lastRotatedImg == null) return;
		canvasScreen.drawImg(0,0,lastRotatedImg);
		selectedImg = null;
		borderImg = null;
		lastRotatedImg = null;
		canvasScreen.clearAndPaint();
	}

//		public void rotateSelected2(SelectorTool t, double theta, int w0, int h0, boolean reset){
//
//
//		boolean WAS_NULL = selectedImg == null;
//		BufferedImage bi=null;
//		if(WAS_NULL){
//			bi = mainImg.getSubimage(t.getFrom().x,t.getFrom().y,t.getWidth(),t.getHeight());
//		}else{
//			bi = selectedImg.getSubimage(t.getFrom().x,t.getFrom().y,t.getWidth(),t.getHeight());
//		}
//		//Util.saveImage(new File("selectedImg1.png"),bi,"png");
//		selectedImg = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
//		shouldClear = true;
//		paint(getGraphics());
//		bi = Util.rotate(bi,theta);//Util.rotateImageByDegrees(bi,theta,bi.getWidth(),bi.getHeight());
//		Graphics2D g = selectedImg.createGraphics();
//		g.drawImage(bi,t.getFrom().x,t.getFrom().y,this);
//		//Util.saveImage(new File("bi.png"),bi,"png");
//		//Util.saveImage(new File("selectedImg2.png"),selectedImg,"png");
//		if(WAS_NULL){
//			System.out.println("CLEAR RECT");
//			imageGraphics.clearRect(t.getFrom().x,t.getFrom().y,t.getWidth(),t.getHeight());
//		}else{
//
//		}
//		if(reset)
//			t.clear();
//		else
//			t.setSelectedRect(bi.getWidth(),bi.getHeight(),selectedImg);
//		combine();
//	}

	public void paint(Graphics g){

		if(borderImg != null){
			g.drawImage(borderImg,0,0,canvasScreen);
		}
		if(selectedImg != null){
			g.drawImage(selectedImg,0,0,canvasScreen);
		}

	}

}
