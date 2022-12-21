package me.titan.painter.tool.tools;

import me.titan.painter.externalframes.ColorChooserFrame;
import me.titan.painter.tool.EnumTools;
import me.titan.painter.tool.Tool;
import me.titan.painter.tool.ToolsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicTools extends Tool {
	private JButton paintButton;
	private JButton eraserButton;
	private JButton colorPickerButton;

	private JSlider sizePicker;

	private JSlider jcomp4;
	private JButton colorChooserButton;
	private JLabel colorDisplay;


	Color chosenColor = Color.BLACK;

	ToolsPanel panel;
	public BasicTools(ToolsPanel panel) {

		this.panel = panel;
			//construct components
			paintButton = new JButton ("paint");
			eraserButton = new JButton ("Eraser");
			sizePicker = new JSlider (1, 20);
			colorChooserButton = new JButton("Color Chooser");
			colorPickerButton = new JButton("Color Picker");

			colorDisplay = new JLabel(){
				@Override
				public void paint(Graphics g) {

					System.out.println(chosenColor);
					g.drawRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
					g.setColor(chosenColor);
					g.fillRect(this.getX()+2,this.getY()+2,this.getWidth()-2,this.getHeight()-2);
				}
			};
			//set components properties
			sizePicker.setOrientation (JSlider.HORIZONTAL);
			sizePicker.setMinorTickSpacing (1);
			sizePicker.setMajorTickSpacing (2);
			sizePicker.setPaintTicks (false);
			sizePicker.setPaintLabels (true);

			//adjust size and set layout


			setupButtons();
			//add components
			panel.addButton (paintButton);
			panel.addButton (eraserButton);
			panel.addComp (sizePicker, 200,50);
			panel.addButton(colorChooserButton);

			panel.addButton(colorPickerButton);
			panel.addComp(colorDisplay,190,50);

			Font smallFont = colorChooserButton.getFont();
			smallFont = new Font(smallFont.getName(),smallFont.getStyle(), (int) (smallFont.getSize()*0.7));
			colorChooserButton.setFont(smallFont);
			colorPickerButton.setFont(smallFont);
			//set component bounds (only needed by Absolute Positioning)
			//paintButton.setBounds (0, 20, 95, 45);
			//eraserButton.setBounds (0, 65, 95, 45);
			//sizePicker.setBounds (0, 110, 100, 50);
			//colorChooserButton.setBounds(0,160,95,45);
			//colorPickerButton.setBounds(0,205,95,45);

			EnumTools.register(EnumTools.BASIC_TOOLS,this);
		}

		public void setupButtons(){
		paintButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumTools.setCurrentTool(EnumTools.PAINT);
			}
		});
		eraserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumTools.setCurrentTool(EnumTools.ERASER);
			}
		});
		colorPickerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				EnumTools.setCurrentTool(EnumTools.COLOR_PICKER);

			}
		});
		colorDisplay.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
								new ColorChooserFrame((c) -> {
					chosenColor = c;
					updateColor();
				});
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
//		colorDisplay.add(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				new ColorChooserFrame((c) -> {
//					chosenColor = c;
//					updateColor();
//				});
//			}
//		});
	}

	public void updateColor(){
		colorDisplay.paint(panel.getGraphics());
	}
	public int getBrushSize(){
		int b = sizePicker.getValue();
		if(b == 0) b = 1;
		return b;
	}



	public Color getChosenColor() {
		return chosenColor;
	}


	public void setChosenColor(Color chosenColor) {
		this.chosenColor = chosenColor;
		updateColor();
	}

}
