package com.smike.tileStitch;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class StitchMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File outputFile = new File("complete.jpg");
			int xMin = 11, xMax = 22;
			int yMin = 51, yMax = 62;
			int width = 247, height = 247;
			
			int numX = xMax - xMin + 1; 
			int numY = yMax - yMin + 1;
			
			
			BufferedImage outputBufferedImage = new BufferedImage(width*numX, height*numY, BufferedImage.TYPE_INT_RGB); 
			for(int x = xMin; x <= xMax; x++) {
				for (int y = yMin; y <= yMax; y++) {
					int xIndex = x - xMin;
					int yIndex = yMax - y;
					File tileFile = new File(x + "" + y + ".gif");
					System.out.println("StitchMap.main(): " + tileFile.getCanonicalPath());
					
					
					BufferedImage tileBufferedImage = ImageIO.read(tileFile);
					
					int[] rgbArray = new int[width*height];
					tileBufferedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
					outputBufferedImage.setRGB(width*xIndex, height*yIndex, width, height, rgbArray, 0, width);
				}
			}
			ImageIO.write(outputBufferedImage, "jpg", outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
