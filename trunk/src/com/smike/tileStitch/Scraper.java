package com.smike.tileStitch;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class Scraper {
	private String baseUrl;
	private Dimension tileSize;

	public Scraper(String baseUrl, Dimension tileSize) {
		this.baseUrl = baseUrl;
		if(tileSize != null) {
			this.tileSize = new Dimension(tileSize);
		}
	}

	protected String getBaseUrl() {
		return baseUrl;
	}

	protected Dimension getTileSize() {
		return tileSize;
	}

	protected abstract String constructUrl(Tile tile);

	protected abstract String constructFileName(Tile tile);

	protected InputStream getWebInputStream(Tile tile) throws IOException {
		String urlString = constructUrl(tile);
		System.out.println("Scraper.getInputStream(): " + urlString);
		HttpURLConnection httpUrlConnection = (HttpURLConnection)new URL(urlString).openConnection();
		httpUrlConnection.setDoInput(true);
		httpUrlConnection.setDoOutput(true);
		InputStream inputStream = httpUrlConnection.getInputStream();
		return inputStream;
	}

	private void saveTile(Tile tile, File outputDir) throws IOException {
		if(outputDir.exists() && !outputDir.isDirectory()) {
			throw new FileNotFoundException(outputDir + " is not a directory."); 
		} else {
			outputDir.mkdirs();
		}

		File file = new File(outputDir, constructFileName(tile));
		if(file.exists()) {
			return;
		}

		InputStream inputStream = getWebInputStream(tile);
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		byte[] data = new byte[1024];
		int totalBytesRead = 0;
		for(int bytesRead = inputStream.read(data); bytesRead >= 0; bytesRead = inputStream.read(data)) {
			fileOutputStream.write(data, 0, bytesRead);
			totalBytesRead += bytesRead;
		}
	}

	protected void saveTiles(int zoom, Point startPoint, Dimension dimension, File outputDir) throws IOException {
		for (int row = 0; row < dimension.height; row++) {
			for (int col = 0; col < dimension.width; col++) {
				Tile tile = new Tile(zoom, startPoint.x + col, startPoint.y + row);

				saveTile(tile, outputDir);
			}
		}
	}

	public void saveCompleteImage(int zoom, Point startPoint, Dimension dimension, File outputDir) throws IOException {
		saveTiles(zoom, startPoint, dimension, outputDir);

		File outputFile = new File(outputDir, "complete.png");
		BufferedImage outputBufferedImage = 
			new BufferedImage(getTileSize().width * dimension.width, 
					getTileSize().height * dimension.height, 
					BufferedImage.TYPE_INT_RGB); 

		Dimension realTotalSize = new Dimension(0, 0);
		for (int row = 0; row < dimension.height; row++) {
			for (int col = 0; col < dimension.width; col++) {
				Tile tile = new Tile(zoom, startPoint.x + col, startPoint.y + row);
				File file = new File(outputDir, constructFileName(tile));
				System.out.println("Scraper.saveCompleteImage():" +  tile);
				BufferedImage tileBufferedImage = ImageIO.read(file);
				int width = tileBufferedImage.getWidth();
				int height = tileBufferedImage.getHeight();
				int[] rgbArray = new int[width*height];
				tileBufferedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
				outputBufferedImage.setRGB(getTileSize().width*col, getTileSize().height*row, 
						width, height, rgbArray, 0, width);

				if (col == 0) {
					realTotalSize.height += height;
				}
				if (row == 0) {
					realTotalSize.width += width;
				}
			}
		}
		BufferedImage trimmedBufferedImage = 
			outputBufferedImage.getSubimage(0, 0, realTotalSize.width, realTotalSize.height);
		trimmedBufferedImage.setData(outputBufferedImage.getData());
		ImageIO.write(trimmedBufferedImage, "png", outputFile);
	}
}
