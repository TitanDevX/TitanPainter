package me.titan.painter.project;

import me.titan.painter.canvas.CanvasScreen;
import me.titan.painter.util.ProjectImages;
import me.titan.painter.util.Util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class PainterProject implements Serializable {

	String id;

	File dir;

	boolean saved;

	public PainterProject(String id) {
		this.id = id;
	}
	public void save(Component owner, CanvasScreen screen) {
		if(dir == null){
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(new FileNameExtensionFilter("Titan paint file (.tpaint)","tpaint"));

			if(jfc.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION){
				dir = jfc.getSelectedFile();
			}
		}
		if(dir == null) return;
		if(!dir.exists()){
			try {
				dir.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		saveTo(dir,screen);
	}
	private void saveTo(File dir, CanvasScreen screen) {

		try(FileOutputStream s = new FileOutputStream(dir)){
			int count = 0;
			byte[] e1 = Util.serialize(this);
			count += e1.length;
			s.write(e1);
			System.out.println("Saved 1 bytes: " + count);
			e1 = Util.serialize(screen.getImages());
			count += e1.length;
			s.write(e1);
			System.out.println("Saved 2 bytes: " + count);
		}catch (IOException ex){
			ex.printStackTrace();
		}
//		for(Map.Entry<Integer, BufferedImage> en : screen.getImages().getImages().entrySet()){
//
//			if(!Util.saveImage(new File(imagesDir,en.getKey() + ".png"), en.getValue(),"png")){
//				System.out.println("Error saving image " + en.getKey() + " " + en.getValue());
//			}
//		}




	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void set(PainterProject p){
		this.id = p.id;
		this.dir = p.dir;
	}
	public void load(CanvasScreen screen){
		loadFrom(dir,screen);
	}

	private void loadFrom(File dir,CanvasScreen screen){


		try {
		FileInputStream s = new FileInputStream(dir);

			ObjectInputStream ob = new ObjectInputStream(s);

			id = null;
			System.out.println(s.available());
			set((PainterProject) ob.readObject());
			System.out.println(id + " " + dir);
			//ob.close();


			ObjectInputStream ob2 = new ObjectInputStream(s);


			ProjectImages p = (ProjectImages) ob2.readObject();
//			for(BufferedImage im : p.getImages().values()){
//				File f = new File("test.png");
//				f.createNewFile();
//				Util.saveImage(new File("test.png"),im,"png");
//			}
			screen.setImages(p);


			s.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}


	}

	public String getId() {
		return id;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public File getDir() {
		return dir;
	}
}
