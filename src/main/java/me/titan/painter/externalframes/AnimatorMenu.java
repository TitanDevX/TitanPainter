package me.titan.painter.externalframes;

import me.titan.painter.menubar.MenuButton;
import me.titan.painter.tool.tools.AnimatorTool;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AnimatorMenu extends JPopupMenu implements KeyListener, MouseListener {

	List<MenuButton> buttons = new ArrayList<>();
	public AnimatorMenu(AnimatorTool animatorTool){

		super("Animator");
		buttons.add(new MenuButton("Record Animation"){
			@Override
			public void onClick(ActionEvent event) {
				animatorTool.startRecording();
			}
		});
		buttons.add(new MenuButton("Add animation layer", animatorTool.isRecording()){
			@Override
			public void onClick(ActionEvent event) {
				animatorTool.addLayer();
			}

			@Override
			public boolean shouldEnable() {
				return animatorTool.isRecording();
			}
		});
		buttons.add(new MenuButton("Remove animation layer", false){
			@Override
			public void onClick(ActionEvent event) {
				super.onClick(event);
			}
			@Override
			public boolean shouldEnable() {
				return animatorTool.isRecording();
			}
		});
		buttons.add(new MenuButton("Play Current Animation", false){
			@Override
			public void onClick(ActionEvent event) {
				animatorTool.playAnimation();
			}

			@Override
			public boolean shouldEnable() {
				return !animatorTool.isPlayingAnimation() && animatorTool.isRecording();
			}
		});
		buttons.add(new MenuButton("Stop Playing", false){
			@Override
			public void onClick(ActionEvent event) {
				animatorTool.stopAnimation();
			}

			@Override
			public boolean shouldEnable() {
				return animatorTool.isPlayingAnimation();
			}
		});
		for(MenuButton b : buttons){
			add(b);
		}
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		for(MenuButton bb : buttons){
			bb.updateEnabled();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			setVisible(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		setVisible(false);
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
}
