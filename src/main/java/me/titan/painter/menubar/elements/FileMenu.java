package me.titan.painter.menubar.elements;

import me.titan.painter.TitanPainterApp;
import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.externalframes.NewProjectWindow;
import me.titan.painter.menubar.MenuButton;
import me.titan.painter.util.Util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class FileMenu extends JMenu {

	CanvasScreen canvasScreen;

	List<MenuButton> buttons = new ArrayList<>();

	public FileMenu(CanvasScreen canvasScreen) {
		this.canvasScreen = canvasScreen;
		setText("File");
		buttons.add(new MenuButton("New Project"){
			@Override
			public void onClick(ActionEvent event) {
				new NewProjectWindow(canvasScreen);
			}
		});
		buttons.add(new MenuButton("Save"){
			@Override
			public void onClick(ActionEvent event) {

				TitanPainterApp.instance.getCurrentProject().save(FileMenu.this,canvasScreen);
			}
		});
		buttons.add(new MenuButton("Load"){
			@Override
			public void onClick(ActionEvent event) {

					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					jfc.setFileFilter(new FileNameExtensionFilter("Titan paint file (.tpaint)","tpaint"));

					if(jfc.showOpenDialog(FileMenu.this) == JFileChooser.APPROVE_OPTION){
						TitanPainterApp.instance.getCurrentProject().setDir(jfc.getSelectedFile());


					}
				TitanPainterApp.instance.getCurrentProject().load(canvasScreen);

			}
		});
		JMenu exportMenu = new JMenu("Export as..");
		exportMenu.add(new MenuButton("PNG") {
			@Override
			public void onClick(ActionEvent event) {

				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileFilter(new FileNameExtensionFilter("PNG file (.png)","png"));

				if(jfc.showOpenDialog(FileMenu.this) == JFileChooser.APPROVE_OPTION){
					Util.saveImage(jfc.getSelectedFile(),canvasScreen.getFinalImage(),"png");
				}
			}

		});
		for (MenuButton button : buttons) {
			add(button);
		}
		add(exportMenu);


	}
}
