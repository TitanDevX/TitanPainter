package me.titan.painter;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.menubar.PainterMenuBar;
import me.titan.painter.tool.ToolsPanel;

import javax.swing.*;
import java.awt.*;

public class ScreenManager extends JFrame {


	CanvasScreen canvasScreen;

	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static ScreenManager instance;
	public ScreenManager(ToolsPanel basicTools, CanvasScreen canvasScreen){
		this.canvasScreen = canvasScreen;
		instance = this;
		setTitle("Titan Paint");
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(null);
		setFocusable(true);
		setJMenuBar(new PainterMenuBar(canvasScreen));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(screenSize);
		add(canvasScreen);
		add(basicTools);

		setVisible(true);
		//preRender();
		pack();
	}

//	@Override
//	public void paintAll(Graphics g) {
//
//		System.out.println("PAINT ALl");
//
//
//	}
//
//	@Override
//	public void paint(Graphics g) {
//		preRender();
//		super.paint(graphics);
//		graphics.dispose();
//		bufferStrategy.show();
//		System.out.println("PAINT ");
//	}

	public void addMenu(JMenu menu){
		add(menu);
		setVisible(true);

	}

}
