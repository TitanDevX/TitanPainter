package me.titan.painter.tool;

import javax.swing.*;
import java.awt.*;

public class ToolsPanel extends JPanel {

	int lastY = 20;
	int lastX = 0;
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public ToolsPanel(){
		setSize (new Dimension(200, screenSize.height));
		setLayout (null);
		setFocusable(false);







		setVisible(true);


		//setLocation(0,0);
	}
	public void addButton(JButton button){
		add(button);
		button.setBounds(lastX,lastY,95,45);

		button.setFocusable(false);
		if(lastX == 0){
			lastX = 95;
		}else{
			lastX = 0;
			lastY+=45;
		}

	}
	public void addComp(Component c, int width, int height){
		add(c);
		c.setBounds(0,lastY,width,height);
		lastY+=height;
		c.setFocusable(false);
	}
}
