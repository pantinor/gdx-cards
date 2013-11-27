package org.antinori.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SlotImage extends Image {
	
	private boolean bottomSlots;
	private int index = 0;
	private boolean occupied;
	
	public SlotImage(Texture texture, int index, boolean isBottom) {
		super(texture);
		this.bottomSlots = isBottom;
		this.index = index;
	}

	public boolean isBottomSlots() {
		return bottomSlots;
	}

	public void setBottomSlots(boolean bottomSlots) {
		this.bottomSlots = bottomSlots;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public int getIndex() {
		return index;
	}
	

}
