//package me.titan.painter.tool.tools;
//
//import me.titan.painter.canvas.CanvasScreen;
//import me.titan.painter.tool.EnumTools;
//import me.titan.painter.tool.Tool;
//import me.titan.painter.tool.ToolsPanel;
//import me.titan.painter.tool.listener.ToolChangeListener;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//
//public class ESelectorTool extends Tool implements MouseListener, MouseMotionListener, ToolChangeListener {
//
//
//	ToolsPanel toolsPanel;
//
//	//Rectangle selectRect;
//	Point from;
//	int width, height;
//
//	boolean active;
//
//	JButton button;
//	CanvasScreen canvasScreen;
//	boolean selected;
//	BufferedImage selectionImage;
//
//	public SelectorTool(ToolsPanel toolsPanel, CanvasScreen canvasScreen){
//		this.toolsPanel = toolsPanel;
//		this.canvasScreen = canvasScreen;
//		EnumTools.register(EnumTools.SELECTOR, this);
//
//		button = new JButton("Selector");
//		button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				active = !active;
//				if(active){
//					EnumTools.setCurrentTool(EnumTools.SELECTOR);
//				}
//			}
//		});
//		toolsPanel.addButton(button);
//
//	}
//	public void setSelectedRect(int width, int height, BufferedImage img){
//		this.selectionImage = img;
//
//		this.width = width;
//		this.height = height;
//
//		canvasScreen.updateSelectorImg(this);
//	}
//	public void clear(){
//		this.from = null;
//		this.width = 0;
//		this.height = 0;
//		this.selectionImage =null;
//		canvasScreen.updateSelectorImg(this);
//	}
//
//	public boolean isActive() {
//		return EnumTools.getCurrentTool() == EnumTools.SELECTOR;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//
//	public int getWidth() {
//		return width;
//	}
//
//	public int getHeight() {
//		return height;
//	}
//
//	public Point getFrom() {
//		return from;
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		System.out.println("CLICK " + e.getPoint() + " " + MouseInfo.getPointerInfo().getLocation());
//	}
//
//	public boolean selectedContains(int x, int y){
//		if(from != null && selected){
//			Rectangle rect = new Rectangle(from,new Dimension(width,height));
//			if(rect.contains(x,y)){
//				return true;
//			}
//		}
//		return false;
//
//	}
//	int  moving;
//	@Override
//	public void mousePressed(MouseEvent e) {
//
//		if(isActive() ){
//			System.out.println("MOUSE PRESSED " + selected + " " + moving);
//			Point p = e.getPoint();
//			if(selectedContains(e.getX(),p.y)){
//				moving = 1;
//				System.out.println("CONTAINS " + p.y + " " + MouseInfo.getPointerInfo().getLocation().y);
//				return;
//			}
//			selected = false;
//			from = null;
//			width = 0;
//			height = 0;
//			canvasScreen.updateSelectorImg(this);
//			from = p;
//			canvasScreen.updateSelectorImg(this);
//		}
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//
//
//		if(EnumTools.getCurrentTool() == EnumTools.SELECTOR){
//			System.out.println("MOUSE RELEASE " + selected + " " + moving);
//			if(!selected){
//				canvasScreen.updateSelectorImg(this);
//				selected = true;
//				moving = 0;
//			}else{
//				moving = -1;
//			}
//
//
//		}
//
//		//canvasScreen.transformSelected(this,e.getX()+100,e.getY()+100);
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		if(isActive() && from != null){
//
//			if(selectedContains(e.getX(),e.getY())){
//				if(selected && from != null && moving == 1){
//
//					Point p = MouseInfo.getPointerInfo().getLocation();
//					selectionImage = canvasScreen.transformSelected(this,e.getX(),e.getY(), selectionImage);
//					from = e.getPoint();
//					canvasScreen.updateSelectorImg(this,false);
//
//					return;
//
//				}
//			}
//			width=(int) (e.getX()-from.getX())+1;
//			height=(int) (e.getY()-from.getY())+1;
//			if(width < 0){
//				width = 0;
//			}
//
//			if(height < 0){
//				height = 0;
//			}
//
//			canvasScreen.updateSelectorImg(this);
//		}
//	}
//
//	public Point getSelectedAreaCenter(){
//		selectionImage = canvasScreen.getCurrentSelectedImg(this);
//		if(selectionImage == null || from == null) return null;
//		Rectangle rect = new Rectangle(from,new Dimension(width,height));
//		return new Point((int)rect.getCenterX(),(int)rect.getCenterY());
//
//	}
//	public Rectangle getSelectedAreaRect(){
//		if(from == null) return null;
//		return new Rectangle(from,new Dimension(width,height));
//	}
//	@Override
//	public void mouseMoved(MouseEvent e) {
//
//
//	}
//
//	@Override
//	public void onToolChange(EnumTools old, EnumTools n) {
//
//		if(old == EnumTools.SELECTOR && n != EnumTools.SELECTOR){
//			if(n == EnumTools.ROTATE){
//				//selectionImage = canvasScreen.getCurrentSelectedImg(this);
//			}else{
//				//canvasScreen.combineSelectorLayers();
//			}
//
//		}
//	}
//}
