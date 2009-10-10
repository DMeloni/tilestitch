/**
 * 
 */
package com.smike.tileStitch;

public class Tile {
	private int zoom;
	private int col;
	private int row;
	
	public Tile(int level, int col, int row) {
		super();
		this.zoom = level;
		this.col = col;
		this.row = row;
	}

	public int getZoom() {
		return zoom;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public String toString() {
		return "[Z:" + getZoom() + ", X:" + getCol() + ", Y:" + getRow() + "]";
	}
}