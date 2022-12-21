package me.titan.painter.externalframes;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.project.PainterProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class SaveProjectDialog extends JDialog {

	private JLabel infoLabel;
	private JButton saveButton;
	private JButton saveAndExitButton;
	private JButton cancelButton;

	int state;

	public static int EXIT = 1;
	public static int SAVE_AND_EXIT = 2;
	public static int CANCEL = 0;

	Consumer<Integer> listener;

	public SaveProjectDialog(PainterProject project, CanvasScreen canvasScreen){
		setTitle("Save Project?");
		setSize(800,150);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
		infoLabel = new JLabel("The project is unsaved, do you want to save?");
		saveButton = new JButton("Exit");
		saveAndExitButton = new JButton("Save And Exit");
		cancelButton = new JButton("Cancel");

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = EXIT;
				setVisible(false);
				if(listener != null){
					listener.accept(state);
				}
			}
		});
		saveAndExitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = SAVE_AND_EXIT;
				project.save(SaveProjectDialog.this,canvasScreen);
				setVisible(false);
				if(listener != null){
					listener.accept(state);
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = CANCEL;
				setVisible(false);

				if(listener != null){
					listener.accept(state);
				}
			}
		});
		infoLabel.setBounds(200,10,300,20);
		saveButton.setBounds(0,50,100,30);
		saveAndExitButton.setBounds(300,50,150,30);
		cancelButton.setBounds(600,50,100,30);
		add(infoLabel);
		add(saveButton);
		add(saveAndExitButton);
		add(cancelButton);
	}
	public static void openDialog(PainterProject project, CanvasScreen canvasScreen, Consumer<Integer> result){

			SaveProjectDialog d = new SaveProjectDialog(project,canvasScreen);
			d.listener = result;


	}


	public int getState() {
		return state;
	}
}
