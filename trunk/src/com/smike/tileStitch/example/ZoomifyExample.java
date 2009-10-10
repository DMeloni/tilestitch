package com.smike.tileStitch.example;

import java.io.File;

import com.smike.tileStitch.ZoomifyScraperImpl;

public class ZoomifyExample {
	public static void main(String[] args) {
		try {
			ZoomifyScraperImpl zoomifyScraperImpl = new ZoomifyScraperImpl(
					"http://zoomify.com/images/folders/parisSatellite");
			int maxLevel = zoomifyScraperImpl.calculateMaxLevel();
			File outputDir = new File("output/parisSatellite");
			zoomifyScraperImpl.saveCompleteImage(maxLevel, outputDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
