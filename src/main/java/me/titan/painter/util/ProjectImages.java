package me.titan.painter.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProjectImages implements Serializable {
		transient Map<Integer, BufferedImage> images;


	public ProjectImages(Map<Integer, BufferedImage> images) {
		this.images = images;
	}

	public Map<Integer, BufferedImage> getImages() {
		return images;
	}

	public void setImages(Map<Integer, BufferedImage> images) {
		this.images = images;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
			out.defaultWriteObject();
			out.writeInt(images.size()); // how many images are serialized?
			for (Map.Entry<Integer, BufferedImage> eachImage : images.entrySet()) {
				ImageIO.write(eachImage.getValue(), "png", out); // png is lossless
			}
		}

		private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
			in.defaultReadObject();
			final int imageCount = in.readInt();
			images = new HashMap<>();
			for (int i=0; i<imageCount; i++) {
				images.put(i,ImageIO.read(in));
			}
		}

}
