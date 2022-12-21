package me.titan.painter.externalframes;

import me.titan.painter.TitanPainterApp;
import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.project.PainterProject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewProjectWindow extends JFrame {

	private JLabel enterNameLabel;
	private JTextField nameField;

	private JLabel chooseFileLabel;
	private JTextField fileTextField;
	private JButton fileChooserButton;

	private  JButton createButton;

	File file;

	public NewProjectWindow(CanvasScreen canvasScreen){

		setTitle("New Project");
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(null);

		enterNameLabel = new JLabel("Project name:");
		nameField = new JTextField();
		chooseFileLabel = new JLabel("Project file (.tpaint):");
		fileTextField = new JTextField();
		fileChooserButton = new JButton("...");
		createButton = new JButton("Create Project");

		fileChooserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileNameExtensionFilter("Titan paint file (.tpaint)","tpaint"));
				if(jfc.showSaveDialog(NewProjectWindow.this) == JFileChooser.APPROVE_OPTION){
					file = jfc.getSelectedFile();
					fileTextField.setText(file.getPath());
				}
			}
		});
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText() == null || nameField.getText().isEmpty()){
					enterNameLabel.setText("This is required!");
					return;
				}

				if(!TitanPainterApp.instance.getCurrentProject().isSaved()){
					setVisible(false);
					SaveProjectDialog.openDialog(TitanPainterApp.instance.getCurrentProject(), canvasScreen, state -> {
						if(state == SaveProjectDialog.CANCEL) return;
						PainterProject p = new PainterProject(nameField.getText());
						TitanPainterApp.instance.setCurrentProject(p);
					});


				}else{
					PainterProject p = new PainterProject(nameField.getText());
					TitanPainterApp.instance.setCurrentProject(p);
				}


			}
		});



		add(enterNameLabel);
		add(nameField);
		add(chooseFileLabel);
		add(fileTextField);
		add(fileChooserButton);
		add(createButton);

		enterNameLabel.setBounds(150,10,200,20);
		nameField.setBounds(150,50,200,20);
		chooseFileLabel.setBounds(150,90,200,20);
		fileTextField.setBounds(150,130,200,20);
		fileChooserButton.setBounds(150,170,200,20);
		createButton.setBounds(150,220,200,20);
	}
}
