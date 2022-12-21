package me.titan.painter.tool.tools;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;
import me.titan.painter.tool.ToolsPanel;
import me.titan.painter.tool.listener.ToolChangeListener;
import me.titan.painter.util.MouseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class SelectorTool extends Tool implements MouseListener, MouseMotionListener, ToolChangeListener {

	CanvasScreen canvasScreen;
	ToolsPanel toolsPanel;
	JButton button;

	Point startingPoint;
	Point endPoint;
	int width, height;

	boolean selecting;
	boolean hasSelection;
	boolean moving;
	public SelectorTool(ToolsPanel toolsPanel, CanvasScreen canvasScreen){
		this.toolsPanel = toolsPanel;
		this.canvasScreen = canvasScreen;
		EnumTools.register(EnumTools.SELECTOR, this);

		button = new JButton("Selector");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumTools.setCurrentTool(EnumTools.SELECTOR);
			}
		});
		toolsPanel.addButton(button);

	}

	public Rectangle2D getSelectedAreaRect(){
		return new Rectangle(actualStartingPoint,new Dimension(width,height));
	}
	public boolean selectionContains(Point p){
		return getSelectedAreaRect().contains(p);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point a = MouseInfo.getPointerInfo().getLocation();
		MouseManager.mouseDifX = e.getX()-a.x;
		MouseManager.mouseDifY =e.getY()-a.y;
		//System.out.println(canvasScreen.mouseDifX + " " + canvasScreen.mouseDifY);
		if(EnumTools.getCurrentTool() == EnumTools.SELECTOR){

			Point actual = e.getPoint();
			if(startingPoint != null && selectionContains(actual)){
				moving = true;
				return;
			}
			if(startingPoint == null&& !selecting){
				startingPoint = actual;
				selecting = true;
			}else if(startingPoint != null && !selecting){
				startingPoint = null;
				endPoint = null;
				width = 0;
				height = 0;
				canvasScreen.getSelectionManager().reset();
			}

		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.SELECTOR){

			if(moving){
				if(canvasScreen.getSelectionManager().transformSelection(actualStartingPoint,width,height,e.getPoint(), false)){
					startingPoint = e.getPoint();
					actualStartingPoint = startingPoint;
					endPoint = assumeEndPoint(startingPoint,width,height);
				}
				return;
			}
			if(startingPoint != null && selecting ){
				endPoint = e.getPoint();
				updateSelection();
			}
		}

	}
	public Point assumeEndPoint(Point startingPoint, int width, int height){

		Point p = (Point) startingPoint.clone();
		p.x += width;
		p.y += height;
		return p;

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.SELECTOR) {
			if(moving){
				moving = false;
				canvasScreen.getSelectionManager().transformSelection(actualStartingPoint,width,height,e.getPoint(), true);
				startingPoint = null;
				actualStartingPoint = null;
				endPoint = null;

				return;
			}
			if (endPoint != null && selecting) {
				endPoint = e.getPoint();
				updateSelection();
				selecting = false;
				hasSelection = true;
			}
		}

	}
	Point actualStartingPoint;
	private void updateSelection(){
		if(startingPoint != null && endPoint != null) {
			Point p1 = (Point) startingPoint.clone();
			Point p2 = (Point) endPoint.clone();
			int difX = (p2.x - p1.x);
			int difY = (p2.y - p1.y);

			if (difX < 0) {
				p1.x += difX;
				p2.x -= difX;
			}
			if (difY < 0) {
				p1.y += difY;
				p2.y -= difY;
			}
			difX = (p2.x - p1.x);
			difY = (p2.y - p1.y);
			width = Math.abs(difX);
			height = Math.abs(difY);
			actualStartingPoint = p1;
			canvasScreen.getSelectionManager().updateSelectionBorder(p1,width,height);
		}else{
			canvasScreen.getSelectionManager().updateSelectionBorder(startingPoint,width,height);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}



	@Override
	public void mouseMoved(MouseEvent e) {

	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void onToolChange(EnumTools old, EnumTools n) {

//		if(old == EnumTools.SELECTOR && n != EnumTools.SELECTOR){
//			if(n == EnumTools.ROTATE){
//				selectionImage = canvasScreen.getCurrentSelectedImg(this);
//			}else{
//				canvasScreen.combineSelectorLayers();
//			}
//
//		}
	}
}
