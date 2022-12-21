package me.titan.painter.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;

public class Util {
	public static boolean saveImage(File file, BufferedImage image, String ext){
		try {
			if (ImageIO.write(image, ext, file))
			{
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static byte[] serialize(Object obj){
		try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
			try(ObjectOutputStream o = new ObjectOutputStream(b)){
				o.writeObject(obj);
			}
			return b.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static BufferedImage rotateImageByDegrees(BufferedImage img, double rads, int w, int h) {
		double sin = Math.abs(Math.sin(rads)) , cos = Math.abs(Math.cos(rads));
		int newWidth = Math.max(w,h);//(int) Math.floor(w * cos + h * sin);
		int newHeight = Math.max(w,h);//(int) Math.floor(h * cos + w * sin);

		saveImage(new File("imgbefore.png"),img,"png");
		BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotated.createGraphics();
		//AffineTransform at = new AffineTransform();
		g2d.translate((newWidth - w) / 2d, (newHeight - h) / 2d);
		int x = w / 2;
		int y = h / 2;
		g2d.rotate(rads, x, y);
		//g2d.setTransform(at);
		g2d.drawImage(img, 0,0, null);
		g2d.dispose();
		return rotated;
	}

	public static Point rotatePoint(Point point, Point center, double angle){

		point = new Point(point);

		//FIRST TRANSLATE THE DIFFERENCE
		int x1 = point.x - center.x;
		int y1 = point.y - center.y;

		//APPLY ROTATION
		x1 = (int) ((double) x1 * Math.cos(angle) - y1 * Math.sin(angle));
		y1 = (int) ((double) x1 * Math.sin(angle) + y1 * Math.cos(angle));

		//TRANSLATE BACK
		point.x = x1 + center.x;
		point.y = y1 + center.y;

		return point;
	}
	public static Rectangle getActualPaintedRect(BufferedImage img,int x0,int y0, int limx, int limy){

		limx += x0;
		limy += y0;
		int xmax = x0,ymax = y0;
		int xmin = limx,ymin = limy;
		img = deepCopy(img);
		int count = 0;
		System.out.println("OR PARAM " + x0 + " " + y0 + " " + limx + " " + limy);
		saveImage(new File("BSS.png"),img,"png");
		for(int x=x0;x<limx;x++){
			for(int y = y0;y<limy;y++){
				//System.out.println("e " + img.getRGB(x,y) + " "  + x + " " + y);
				if(img.getRGB(x,y) == 0) continue;

				count++;
				if(x > xmax){
					//System.out.println("XMAX " + xmax + " " + x);
					xmax = x;

				}
				if(y > ymax){
					//System.out.println("YMAX " + ymax + " " + y);
					ymax = y;
				}
				if(x < xmin){
					//System.out.println("XMIN " + xmin + " " + x);
					xmin = x;
				}
				if(y < ymin){
					//System.out.println("YMIN " + ymin + " " + y);
					ymin = y;
				}
			}
		}
		Point origin = new Point(Math.abs(xmin)-10,Math.abs(ymin)-10);
		int width = Math.abs(xmax-xmin)+20;
		int height = Math.abs(ymax-ymin)+20;

		System.out.println("OR " + origin + " " + width + " " + height + " " + xmax + " " + ymax + " "
				+ xmin + " " + ymin);
		System.out.println("OR COUNT " + count);
		return new Rectangle(origin, new Dimension(width,height));
	}
	public static BufferedImage rotateSimple(BufferedImage org, Point p, double rad){
		int width = Math.max(org.getWidth(),org.getHeight());
		int height = width;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.drawImage(org,0,0,null);
		Rectangle rect =  new Rectangle(new Point(),new Dimension(width,height));
		Point center = new Point((int) rect.getCenterX(),(int) rect.getCenterY());

		int nwidth = width+1, nheight= height+1;
		for(int x = 0;x<width;x++){
			for(int y = 0;y<height;y++){
				if(img.getRGB(x,y) == 0) continue;
				Point pi = new Point(x,y);
				Point rotated = rotatePoint(pi, center, rad);

				if(rotated.x > nwidth){
					nwidth+= (rotated.x - nwidth);
					//System.out.println("INCREASe " + nwidth);
				}
				if(rotated.y > nheight){
					nheight+= (rotated.y - nheight);
				}

			}
		}

		img = new BufferedImage(nwidth,nheight,BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		g.drawImage(org,0,0,null);
		int[][] idk = new int[nwidth][nheight];
		for(int x = 0;x<nwidth;x++){
			for(int y = 0;y<nheight;y++){
				if(idk[x][y] == 1) continue;
				if(img.getRGB(x,y) == 0) continue;
				Point pi = new Point(x,y);
				Point rotated = rotatePoint(pi, center, rad);
				int rgp = img.getRGB(x,y);
				//if(rotated.x > nwidth || rotated.y > nheight){
				//System.out.println("P " + rotated + " " + pi + " " + center);
				//

				if(img.getRGB(rotated.x,rotated.y) == 0){
					idk[rotated.x][rotated.y] = 1;
				}
				img.setRGB(rotated.x,rotated.y,rgp);
				img.setRGB(x,y,0);
			}
		}
		return img;
	}
	public static RotatableObject customRotateSmallAngle(BufferedImage org, Point p, double rad){
		int width = org.getWidth();//Math.max(org.getWidth(),org.getHeight());
		int height = org.getHeight();
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.drawImage(org,0,0,null);
		Rectangle rect =  new Rectangle(p,new Dimension(width,height));
		Point center = new Point((int) rect.getCenterX(),(int) rect.getCenterY());

		int nwidth = width;
		int nheight = height;
		//
	//	int xmax = 0;
		//int ymax = 0;
		int xmin = Integer.MAX_VALUE;
		int ymin = Integer.MAX_VALUE;
		for(int x = 0;x<width;x++){
			for(int y = 0;y<height;y++){
				if(img.getRGB(x,y) == 0) continue;
				Point pi = new Point(x,y);
				Point rotated = rotatePoint(pi, center, rad);

				int vw = nwidth -xmin;
				int vh = nheight -ymin;
				if(rotated.x > vw){
					nwidth+= (rotated.x - vw);
					//System.out.println("INCREASe " + nwidth);
				}
				System.out.println("P0 " + rotated);
				if(rotated.y > vh){
					nheight+= (rotated.y - vh);
				}
				if(rotated.x < xmin){
					xmin = rotated.x;
				}
				if(rotated.y < ymin){
					ymin = rotated.y;
				}
			}
		}
		System.out.println("N1 " + nwidth + " " + nheight + " " + width + " " + height + " " + center);
		nwidth -= xmin;
		nheight -= ymin;
		nwidth += 1;
		nheight += 1;
		img = new BufferedImage(nwidth,nheight,BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		g.drawImage(org,0,0,null);
		Util.saveImage(new File("ggo.png"),img,"png");
		System.out.println("N " + nwidth + " " + nheight + " " + width + " " + height);
		System.out.println("MIN " + xmin + " " + ymin);
		int[][] idk = new int[nwidth][nheight];
		center.x -= xmin;
		center.y -= ymin;
		for(int x = 0;x<nwidth;x++){
			for(int y = 0;y<nheight;y++){
				if(idk[x][y] == 1) continue;
				if(img.getRGB(x,y) == 0) continue;
				Point pi = new Point(x+xmin,y+ymin);
				Point rotated = rotatePoint(pi, center, rad);
				//rotated.x -= xmin;
				//rotated.y -= ymin;
				int rgp = img.getRGB(x,y);


				//if(rotated.x > nwidth || rotated.y > nheight){
					System.out.println("P " + rotated + " " + pi + " " + center);
				//}


				if(img.getRGB(rotated.x,rotated.y) == 0){
					idk[rotated.x][rotated.y] = 1;
				}

				img.setRGB(rotated.x,rotated.y,rgp);
				img.setRGB(x,y,0);
			}
		}

		return new RotatableObject(new Point(xmin-width,ymin-height),img);

	}
	public static void test(String var0, String var1){
		HashMap var2 = new HashMap();
		var2.put("username", var0);
		var2.put("password", var1);


								boolean var10001;
								StringBuilder var42 = new StringBuilder();
								Iterator var3 = null;
									var3 = var2.keySet().iterator();


								while(true) {
									try {
										if (!var3.hasNext()) {
											break;
										}

										var1 = (String)var3.next();
										StringBuilder var4 = new StringBuilder();
										var4.append("&");
										var4.append(var1);
										var4.append("=");
										var42.append(var4.toString());
										var42.append(URLEncoder.encode((String)var2.get(var1), "UTF-8"));

									} catch (IOException var33) {
										var10001 = false;
									} catch (Exception var35) {
										var10001 = false;
									}
								}

								int var5;
								HttpURLConnection var48;
								try {
									URL var46 = new URL("https://movo.delivery/driver_apiv2/mkapi/driver_login.php");
									var48 = (HttpURLConnection) var46.openConnection();
									var48.setRequestMethod("POST");
									var48.setConnectTimeout(1000);
									var48.setDoOutput(true);
									OutputStreamWriter var50 = new OutputStreamWriter(var48.getOutputStream());
									var50.write(var42.toString());
									var50.flush();
									var5 = var48.getResponseCode();

									System.out.println("RES: " + var5);
								} catch (ProtocolException e) {
									throw new RuntimeException(e);
								} catch (MalformedURLException e) {
									throw new RuntimeException(e);
								} catch (IOException e) {
									throw new RuntimeException(e);
								}

	}
	public static BufferedImage rotate(BufferedImage bi, double rad, boolean exact) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		int degree = (int) Math.toDegrees(rad);
		if(degree > 360){

			degree = degree - (360 * ((int) degree/360));
		}
		if(degree > 270){
			degree = 270;
		}
		if(degree < 270 && degree > 180){
			degree = 180;
		}else if(degree < 180 && degree > 90) {
			degree = 90;
		}else if(degree < 90 && degree > 60){
			degree = 60;
		}else if(degree < 60 && degree > 30){
			degree = 30;
		}else if(degree < 30 && degree > 0){
			if(!exact){
				degree = 30;
			}
			//degree = 0;
		}
		if(degree == 0 || degree == 360){
			return  bi;
		}

		BufferedImage biFlip;
		if (degree == 90 || degree == 270)
			biFlip = new BufferedImage(height, width, bi.getType());
		else if (degree == 180)
			biFlip = new BufferedImage(width, height, bi.getType());
		else if (degree == 60 || degree == 30 || degree < 30) {
			biFlip = new BufferedImage(width + (height / 2), height + (width / 2), bi.getType());
		}
		else
			return bi;

		if (degree == 90) {
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					biFlip.setRGB(height - j - 1, i, bi.getRGB(i, j));
		}

		if (degree == 180) {
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					biFlip.setRGB(width - i - 1, height - j - 1, bi.getRGB(i, j));
		}

		if (degree == 270) {
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					biFlip.setRGB(j, width - i - 1, bi.getRGB(i, j));
		}
		if(degree == 30 || degree == 60 || degree < 30){
			biFlip = rotateImageByDegrees(bi,Math.toRadians(degree),bi.getWidth(),bi.getHeight());
		}

		bi.flush();

		return biFlip;
	}
	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();

		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);

		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static BufferedImage childDeepCopy(BufferedImage parent, BufferedImage bi) {
		ColorModel cm = bi.getColorModel();

		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		raster = raster.createWritableChild(parent.getMinX(),parent.getMinY(),bi.getWidth(),bi.getHeight(),bi.getMinX(),bi.getMinY(),null);

		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
		try(ObjectInputStream o = new ObjectInputStream(is)){
			return o.readObject();
		}
	}
	public static class SimpleWindowListener implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {

		}


		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}
	}
}
