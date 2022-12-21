package me.titan.painter.tool.tools;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.canvas.memory.CanvasMemory;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class ShortcutsTool extends Tool implements KeyListener {

	CanvasMemory memory;
	CanvasScreen screen;
	public ShortcutsTool(CanvasMemory memory, CanvasScreen screen) {
		this.memory = memory;
		this.screen = screen;
		EnumTools.register(EnumTools.SHORTCUTS, this);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e);
		if(e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()){

//			if(!memory.getCurrentDrawing().isReversed()){
//				System.out.println("REV2");
//				screen.reverse(memory.getCurrentDrawing(), memory.getCurrentTool());
//				memory.getCurrentDrawing().setReversed(true);
//			}else if(!memory.getPreviousDrawing().isReversed()){
//				System.out.println("REV3");
//				screen.reverse(memory.getPreviousDrawing(),memory.getPreviousTool());
//				memory.getPreviousDrawing().setReversed(true);
//			}
			BufferedImage image = memory.getCurrentImage();
			System.out.println("CTRLZ");
			if(image != null){
				screen.fromImage(image);
				memory.previousImage();
			}



		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println(e);
	}
}
