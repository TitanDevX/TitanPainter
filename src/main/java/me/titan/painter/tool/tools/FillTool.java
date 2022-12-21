package me.titan.painter.tool.tools;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;
import me.titan.painter.tool.ToolsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FillTool extends Tool implements MouseListener {

	CanvasScreen canvasScreen;
	ToolsPanel toolsPanel;

	JButton button;

	public FillTool(CanvasScreen canvasScreen, ToolsPanel toolsPanel) {
		this.canvasScreen = canvasScreen;
		this.toolsPanel = toolsPanel;
		EnumTools.register(EnumTools.FILL, this);
		button = new JButton("Fill");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumTools.setCurrentTool(EnumTools.FILL);
			}
		});
		toolsPanel.addButton(button);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if(EnumTools.getCurrentTool() == EnumTools.FILL){

			System.out.println("FILL");
			canvasScreen.getFillManager().fill(e.getX(),e.getY(),((BasicTools)EnumTools.BASIC_TOOLS.get()).getChosenColor());
		}

	}



	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
