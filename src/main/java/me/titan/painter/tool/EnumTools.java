package me.titan.painter.tool;

import me.titan.painter.tool.listener.ToolChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumTools {

	BASIC_TOOLS(false), PAINT(true), ERASER(true),COLOR_PICKER(true),
	SHORTCUTS(false), SELECTOR(false), ROTATE(false), ANIMATOR(false),
	FILL(false);

	private static Map<EnumTools, Tool> tools = new HashMap<>();
	private static EnumTools currentTool;

	private static List<ToolChangeListener> toolChangeListenerList = new ArrayList<>();
	boolean child;



	EnumTools(boolean child) {
		this.child = child;
	}

	public boolean isChild() {
		return child;
	}
	public <T extends Tool> T get(){
		return (T) tools.get(this);
	}
	public static void register(EnumTools type, Tool t){
		tools.put(type,t);
	}

	public static EnumTools getCurrentTool() {
		return currentTool;
	}
	public static void addToolChangeListener(ToolChangeListener l){
		toolChangeListenerList.add(l);
	}

	public static void setCurrentTool(EnumTools currentTool) {
		for(ToolChangeListener l : toolChangeListenerList){
			l.onToolChange(EnumTools.getCurrentTool(),currentTool);
		}
		EnumTools.currentTool = currentTool;
	}
	//	public static void init(){
//		for (EnumTools t : EnumTools.values()) {
//
//
//
//		}
//	}
}
