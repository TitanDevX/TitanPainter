package me.titan.painter.util;

public class MouseManager {

	/**
	 * A = actual mouse loc
	 * C = comp mouse loc
	 *
	 * mouseDif(*) = C.(*) - A.(*)
	 */
	public static int mouseDifX, mouseDifY;

	public static int toCompX(int aX){
		return aX; //+ mouseDifX;
	}
	public static int toCompY(int aY){
		return aY;// + mouseDifY;
	}
	public static int toActualX(int cX){
		return cX - mouseDifX;
	}
	public static int toActualY(int cY){
		return cY - mouseDifY;
	}
}
