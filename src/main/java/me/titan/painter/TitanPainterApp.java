package me.titan.painter;

import me.titan.painter.canvas.CanvasDrawer;
import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.canvas.memory.CanvasMemory;
import me.titan.painter.project.PainterProject;
import me.titan.painter.tool.ToolsPanel;
import me.titan.painter.tool.tools.*;

public class TitanPainterApp {

	public static TitanPainterApp instance;
	public static void main(String[] args) {
		instance = new TitanPainterApp();
	}

	private PainterProject currentProject;
	ScreenManager screen;

	CanvasDrawer canvasDrawer;
	CanvasScreen canvasScreen;


	public TitanPainterApp() {


		canvasScreen = new CanvasScreen();
		ToolsPanel toolsPanel = new ToolsPanel();
		new BasicTools(toolsPanel);
		SelectorTool st = new SelectorTool(toolsPanel,canvasScreen);
		RotateTool rt = new RotateTool(st,canvasScreen);
		FillTool ft = new FillTool(canvasScreen,toolsPanel);

		CanvasMemory canvasMemory = new CanvasMemory();
		canvasDrawer = new CanvasDrawer(canvasScreen, canvasMemory);


		screen = new ScreenManager(toolsPanel,canvasScreen);
		AnimatorTool at = new AnimatorTool(canvasScreen,screen);

		setCurrentProject( new PainterProject("test"));

		canvasScreen.addKeyListener(new ShortcutsTool(canvasMemory,canvasScreen));
		canvasScreen.addMouseListener(st);
		canvasScreen.addMouseMotionListener(st);

		canvasScreen.addKeyListener(rt);
		canvasScreen.addKeyListener(at);
		canvasScreen.addMouseMotionListener(rt);
		canvasScreen.addMouseListener(rt);
		canvasScreen.addMouseListener(ft);
		canvasScreen.addMouseListener(canvasDrawer);
		canvasScreen.addMouseMotionListener(canvasDrawer);

//		new Thread(){
//			@Override
//			public void run() {
//
//				for(int i = 0;i<10;i++){
//					StringBuilder pass = new StringBuilder();
//					int ran = i;
//					for(int o =0;o<4;o++){
//						//for(int n =0;n<=9;n++){
//						pass.append(ran);
//						//}
//					}
//					System.out.println(" " + pass.toString());
//					Util.test("abd_s",pass.toString());
//				}
//				for(int i =0;i<100;i++){
//					StringBuilder str = new StringBuilder();
//					int count = 5;
//					for(int i2 = 0;i2<count;i2++){
//						int cha = new Random().nextInt(255);
//
//						while(!isLetter((char) cha)){
//							cha = new Random().nextInt(255);
//						}
//						char charr = (char) cha;
//						str.append((char) cha);
//					}
//
//				}
//
//			}
//		}.start();


	}
	private static boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z');
	}

	public ScreenManager getScreen() {
		return screen;
	}

	public PainterProject getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(PainterProject currentProject) {
		this.currentProject = currentProject;
		screen.setTitle("Titan Painter - " + currentProject.getId());
		canvasScreen.reset();
	}
}
