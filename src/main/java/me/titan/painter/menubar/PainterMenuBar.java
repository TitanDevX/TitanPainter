package me.titan.painter.menubar;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.menubar.elements.EditMenu;
import me.titan.painter.menubar.elements.FileMenu;

import javax.swing.*;

public class PainterMenuBar extends JMenuBar {

	CanvasScreen canvasScreen;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;

	public PainterMenuBar(CanvasScreen canvasScreen){
		this.canvasScreen = canvasScreen;
		fileMenu = new FileMenu(canvasScreen);
		editMenu = new EditMenu(canvasScreen);

		add(fileMenu);
		add(editMenu);


	}

}
