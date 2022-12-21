package me.titan.painter.menubar.elements;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.menubar.MenuButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class EditMenu extends JMenu {

	CanvasScreen canvasScreen;

	List<MenuButton> buttons = new ArrayList<>();

	public EditMenu(CanvasScreen canvasScreen) {
		this.canvasScreen = canvasScreen;
		setText("Edit");
		buttons.add(new MenuButton("Save"){
			@Override
			public void onClick(ActionEvent event) {
				System.out.println("Saving...");
			}
		});
		for (MenuButton button : buttons) {
			add(button);
		}
	}
}
