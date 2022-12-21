package me.titan.painter.externalframes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;

public class ColorChooserFrame extends JFrame {
	private JColorChooser colorChooser;
	public ColorChooserFrame(Consumer<Color> onExit){
		setTitle("Color Chooser");
		setSize(500,500);
		colorChooser = new JColorChooser();

		add(colorChooser);

		colorChooser.setBounds(0,0,500,500);
		setVisible(true);

		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				onExit.accept(colorChooser.getColor());
			}

			@Override
			public void windowClosed(WindowEvent e) {


			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
	}

}
