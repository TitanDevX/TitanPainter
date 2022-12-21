package me.titan.painter.menubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButton extends JButton implements ActionListener {

	final String id;

	public MenuButton(String id) {
		super(id);
		this.id = id;
		addActionListener(this);
	}
	public MenuButton(String id, boolean clickable) {
		this(id);
		setEnabled(false);
	}
	public void onClick(ActionEvent event){

	}


	public void updateEnabled(){
		setEnabled(shouldEnable());
	}
	protected boolean shouldEnable(){
		return true;
	}

	public String getId() {
		return id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		onClick(e);
	}
}
