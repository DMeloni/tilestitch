package com.smike.tileStitch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetTiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String baseURL = "http://www.jerusalemmap.com/images/map/";
			int xMin = 11, xMax = 22;
			int yMin = 51, yMax = 62;

			for(int x = xMin; x <= xMax; x++) {
				for (int y = yMin; y <= yMax; y++) {
					String urlString = baseURL + x + "" + y + ".gif";
					File outputFile = new File(x + "" + y + ".gif");
					if(outputFile.exists()) { 
						continue;
					}
					
					System.out.println("GetMap.main(): " + urlString);
					HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(urlString).openConnection();
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
//					URLImageSource urlImageSource = (URLImageSource)httpURLConnection.getContent();
					InputStream inputStream = httpURLConnection.getInputStream();
					FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
					
					byte[] data = new byte[1024];
					int totalBytesRead = 0;
					for(int bytesRead = inputStream.read(data); bytesRead >= 0; bytesRead = inputStream.read(data)) {
						fileOutputStream.write(data, 0, bytesRead);
						totalBytesRead += bytesRead;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
