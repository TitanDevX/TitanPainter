package me.titan.painter.tool.tools;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.canvas.managers.DrawText;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class RotateTool extends Tool implements KeyListener, MouseListener, MouseMotionListener {

	SelectorTool selectorTool;
	CanvasScreen canvasScreen;

	Point startingPoint;
	double thetaTotal;

	int width0, height0;
	public RotateTool(SelectorTool selectorTool, CanvasScreen canvasScreen){
		this.selectorTool = selectorTool;
		this.canvasScreen = canvasScreen;
		EnumTools.register(EnumTools.ROTATE,this);
	}

	boolean shouldReset;

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		//canvasScreen.drawString(55,55, " ee e e");

		if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R){
			EnumTools.setCurrentTool(EnumTools.ROTATE);
			this.width0 = selectorTool.width;
			this.height0 = selectorTool.height;
//			Rectangle rect = canvasScreen.getSelectionManager()
//					.rotateSelected(selectorTool.actualStartingPoint,
//							selectorTool.width,selectorTool.height,Math.toRadians(60),
//							false);
//			BufferedImage bf = canvasScreen.getImage();
//
//
//			bf.createGraphics().drawRect(rect.x,rect.y,rect.width, rect.height);
//			canvasScreen.fromImage(bf);
//			canvasScreen.clearAndPaint();
//
//			new Task(){
//				@Override
//				public void run() {
//					Rectangle rect2 = canvasScreen.getSelectionManager()
//							.rotateSelected(rect.getLocation(),
//									rect.width,rect.height,Math.toRadians(60),
//									false);
//					BufferedImage bf = canvasScreen.getImage();
//
//
//					bf.createGraphics().drawRect(rect2.x,rect2.y,rect2.width, rect2.height);
//					canvasScreen.fromImage(bf);
//					canvasScreen.clearAndPaint();
//				}
//			}.runTask(5, TimeUnit.SECONDS);

			Rectangle rectt = canvasScreen.getSelectionManager().getRotationSelection(selectorTool.actualStartingPoint,
					selectorTool.width, selectorTool.height);
			selectorTool.actualStartingPoint = rectt.getLocation();
			selectorTool.width = rectt.getBounds().width;
			selectorTool.height = rectt.getBounds().height;
			canvasScreen.getSelectionManager().updateSelectionBorder(selectorTool.actualStartingPoint,selectorTool.width,selectorTool.height);

			System.out.println("ROT TOOL");
//			selectorTool.actualStartingPoint.y -= selectorTool.height/2;
//			selectorTool.height += selectorTool.height/2;
//			canvasScreen.getSelectionManager().updateSelectionBorder(selectorTool.actualStartingPoint,selectorTool.width,selectorTool.height);
//
//			new Task(){
//				int i =0;
//				@Override
//				public void run() {
//
//
//					Rectangle2D rectt = canvasScreen.getSelectionManager()
//							.rotateSelected(selectorTool.actualStartingPoint,
//									selectorTool.width,selectorTool.height,Math.toRadians(30),
//									false);
//					selectorTool.width = rectt.getBounds().width;
//					selectorTool.height = rectt.getBounds().height;
//					canvasScreen.getSelectionManager().updateSelectionBorder(selectorTool.actualStartingPoint,selectorTool.width,selectorTool.height);
//
//
////					if(true){
////
////						cancel();
////						return;
////					}
//					if(i == 3){
//						canvasScreen.getSelectionManager()
//								.rotateSelected(selectorTool.actualStartingPoint,
//										selectorTool.width,selectorTool.height,Math.toRadians(30),
//										true);
//						cancel();
//					}
//					i++;
//
//				}
//
//			}.runTaskTimer(1,100, TimeUnit.MILLISECONDS);
		}
		if(EnumTools.getCurrentTool() == EnumTools.ROTATE && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A){
			this.thetaTotal = 0;

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		if(EnumTools.getCurrentTool() == EnumTools.ROTATE){
			startingPoint = e.getPoint();

			//System.out.println("ROT START " + startingPoint);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.ROTATE && thetaTotal != 0){
			if(Double.isNaN(thetaTotal)){
				thetaTotal = 0;
				return;
			}
			//System.out.println("ROTATE TOTAL " + thetaTotal);
			thetaTotal -= thetaTotal % (Math.PI/6);
			System.out.println(thetaTotal);
			canvasScreen.getSelectionManager()
					.rotateSelected(selectorTool.actualStartingPoint,
							selectorTool.width,selectorTool.height,thetaTotal,
							true);
			selectorTool.width = 0;
			selectorTool.height = 0;
			selectorTool.startingPoint =null;
			selectorTool.endPoint = null;
			selectorTool.actualStartingPoint = null;
			selectorTool.selecting = false;
			thetaTotal = 0;
			canvasScreen.getTextDrawManager().removeText(1);

		}
	}
	int tempTheta;
	@Override
	public void mouseDragged(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.ROTATE && startingPoint != null){
			Rectangle2D rect = selectorTool.getSelectedAreaRect();

			if(rect == null) return;
			Point center = new Point((int) rect.getX(),(int) rect.getY());
			Line2D l1 = new Line2D.Double(startingPoint,center);
			Line2D l2 = new Line2D.Double(e.getPoint(),center);

			double m1 = (l1.getY2()-l1.getY1())/(l1.getX2()-l1.getX1());
			double m2 = (l2.getY2()-l2.getY1())/(l2.getX2()-l2.getX1());

			double theta = Math.atan((m1-m2)/(1+m1*m2))/4;
			if(Double.isNaN(theta)) return;

			if(theta < Math.toRadians(10)){
				tempTheta += theta;
				if(tempTheta >= Math.toRadians(10)){
					theta = tempTheta;
				}else{
					return;
				}
			}
			thetaTotal+=theta;
			int tt = (int)Math.toDegrees(thetaTotal);
			tt -= tt % 30;

			//canvasScreen.rotateSelected(selectorTool,thetaTotal,width0,height0, false);
			DrawText text = new DrawText(1,(int) startingPoint.x,(int)startingPoint.y, Color.BLACK, tt + "");
			canvasScreen.drawString(text);

			Rectangle2D rectt = canvasScreen.getSelectionManager()
					.rotateSelected(selectorTool.actualStartingPoint,
							selectorTool.width,selectorTool.height,theta,
							false);
			selectorTool.width = rectt.getBounds().width;
			selectorTool.height = rectt.getBounds().height;
			canvasScreen.getSelectionManager().updateSelectionBorder(selectorTool.actualStartingPoint,selectorTool.width,selectorTool.height);


			//System.out.println("ROTATE " + theta + " " + thetaTotal);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
