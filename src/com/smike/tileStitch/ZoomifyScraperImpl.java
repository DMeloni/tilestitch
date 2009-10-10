package com.smike.tileStitch;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class ZoomifyScraperImpl extends Scraper {
	private static final String IMAGE_PROPERTIES_NAME = "/ImageProperties.xml";
	private static final int NUM_TILES_PER_GROUP = 256;
	
	private Dimension size;
	private int numTiles; //TODO: Can this be used somehow to calculate things more efficiently?
	private int numImages; //TODO: Not used yet. Always expected to be 1.
	private String version; //TODO: Not used yet. Check for version compatibility.
	private int tileSize;
	
	public ZoomifyScraperImpl(String baseUrl) throws ParserConfigurationException, SAXException, IOException {
		super(baseUrl, null);
		init();
	}
	
	private void init() throws ParserConfigurationException, SAXException, IOException {
		String imagePropertiesUrlString = getBaseUrl() + IMAGE_PROPERTIES_NAME;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(imagePropertiesUrlString);
		Element element = doc.getDocumentElement();
		
		String widthString = element.getAttribute("WIDTH");
		int width = Integer.parseInt(widthString);
		
		String heightString = element.getAttribute("HEIGHT");
		int height = Integer.parseInt(heightString);
		
		size = new Dimension(width, height);
		
		String numTilesString = element.getAttribute("NUMTILES");
		numTiles = Integer.parseInt(numTilesString);
		
		String numImagesString = element.getAttribute("NUMIMAGES");
		numImages = Integer.parseInt(numImagesString);
		
		version = element.getAttribute("VERSION");
		
		String tileSizeString = element.getAttribute("TILESIZE");
		tileSize = Integer.parseInt(tileSizeString);
	}
	
	protected Dimension getTileSize() {
		return new Dimension(tileSize, tileSize);
	}
	
	public int calculateMaxLevel() {
		double maxDimension = Math.max(size.width, size.height);
		return (int)Math.ceil(Math.log(maxDimension/tileSize)/Math.log(2));
	}
	
	private Dimension calculateTilesDim(int level) {
		int maxLevel = calculateMaxLevel();
		
		double widthAtLevel = size.width / Math.pow(2, maxLevel - level);
		double heightAtLevel = size.height / Math.pow(2, maxLevel - level);
		
		int numCols = (int)Math.ceil(widthAtLevel/tileSize);
		int numRows = (int)Math.ceil(heightAtLevel/tileSize);
		Dimension tileSizeAtLevel = new Dimension(numCols, numRows);
		
		return tileSizeAtLevel;
	}
	
	private int calculateTileGroup(Tile tile) {
		int tileNum = 0;
		
		for(int level = 0; level <= tile.getZoom(); level++) {
			Dimension tilesDim = calculateTilesDim(level);
			if(level == tile.getZoom()) {
				tileNum += tile.getCol() + tilesDim.width*tile.getRow();
			} else {
				tileNum += tilesDim.width * tilesDim.height;
			}
		}

		return tileNum / NUM_TILES_PER_GROUP;
	}

	protected String constructUrl(Tile tile) {
		return getBaseUrl() + "/TileGroup" + calculateTileGroup(tile) + "/"
				+ constructFileName(tile);
	}

	protected String constructFileName(Tile tile) {
		return tile.getZoom() + "-" + tile.getCol() + "-" + tile.getRow()
				+ ".jpg";
	}

	protected void saveTiles(int level, File outputDir) throws IOException {
		Dimension tilesDim = calculateTilesDim(level);

		super.saveTiles(level, new Point(0, 0), tilesDim, outputDir);
	}

	public void saveCompleteImage(int level, File outputDir) throws IOException {
		Dimension dimension = calculateTilesDim(level);
		super.saveCompleteImage(level, new Point(0, 0), dimension, outputDir);
	}
}
