package me.titan.painter.canvas;

import me.titan.painter.canvas.memory.CanvasMemory;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.tools.BasicTools;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CanvasDrawer implements MouseListener, MouseMotionListener {


	CanvasScreen canvasScreen;
	CanvasMemory memory;

	public CanvasDrawer(CanvasScreen canvasScreen, CanvasMemory memory) {
		this.canvasScreen = canvasScreen;
		this.memory = memory;
	}


	private BasicTools getBasicTools(){
		return (BasicTools) EnumTools.BASIC_TOOLS.get();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.COLOR_PICKER){
			getBasicTools().setChosenColor(canvasScreen.getColor(e.getX(),e.getY()));
		}
	}

	
	
	@Override
	public void mousePressed(MouseEvent e) {
		memory.setCurrentTool(EnumTools.getCurrentTool());
		memory.addImage(canvasScreen.getImage());
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		memory.setPreviousTool(memory.getCurrentTool());
		memory.setCurrentTool(null);
		//memory.setLastImage(canvasScreen.getImage());
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(EnumTools.getCurrentTool() == EnumTools.PAINT){

			canvasScreen.getDrawManager().calcRects(getBasicTools().getBrushSize());
			Rectangle rect = canvasScreen.getDrawManager().getRect(e.getX(),e.getY());
			//System.out.println(canvasScreen.getDrawManager().getRects());
			if(rect == null) return;
			canvasScreen.paintPixel(rect.x,rect.y, getBasicTools().getBrushSize(),getBasicTools().getBrushSize(), getBasicTools().getChosenColor());


		}else if(EnumTools.getCurrentTool() == EnumTools.ERASER){
		 canvasScreen.erase(e.getX(),e.getY(), getBasicTools().getBrushSize(),getBasicTools().getBrushSize());
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		//canvasScreen.updateMousePos(e.getX(),e.getY());
	}
}
