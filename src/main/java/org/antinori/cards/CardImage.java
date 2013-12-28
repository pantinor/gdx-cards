package org.antinori.cards;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardImage extends Actor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Sprite img;
	Texture frame;
	Card card;
	BitmapFont font;
	boolean enabled;
	boolean isHighlighted;
	Creature creature;
	
	public CardImage() {
	}
	
	public CardImage(Sprite img, Card info) {
		this.img = img;
		this.card = info;
		setName(card.getName());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		float x = getX();
		float y = getY();
		batch.draw(img, x, y);
		batch.draw(frame, x - 3, y - 12);
		
		int at = card.getAttack();
		int co = card.getCost();
		int li = card.getLife();

		if (!card.isSpell()) {
			font.draw(batch, "" + at, (at>9?x:x+3), y+7);
			font.draw(batch, "" + co, (co>9?x+66:x+69), y+87);
			font.draw(batch, "" + li, (li>9?x+66:x+69), y+7);
		} else {
			font.draw(batch, "" + co, (co>9?x+66:x+69), y+77);
		}

	}
	
	public CardImage clone() {
		CardImage ci = new CardImage();
		ci.setName(card.getName());
		ci.setCard(this.card.clone());
		ci.setImg(this.img);
		ci.setFont(this.font);
		ci.setFrame(this.frame);
		ci.setEnabled(true);
		ci.setBounds(getX(), getY(), this.getWidth(), this.getHeight());
		return ci;
	}

	public Sprite getImg() {
		return img;
	}

	public Texture getFrame() {
		return frame;
	}

	public Card getCard() {
		return card;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setImg(Sprite img) {
		this.img = img;
	}

	public void setFrame(Texture frame) {
		this.frame = frame;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	public boolean decrementLife(int value, Cards game) {
		
		creature.onAttacked(value);
				
		int remainingLife = card.getLife();
		boolean died = (remainingLife < 1);
		
		return died;
	}
	
	public void incrementLife(int value, Cards game) {
		
		card.incrementLife(value);
		
		game.animateHealingText(value, getX() + 60, getY() + 10, getX() + 60, getY() + 69 );
	}
		
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static void sort(List<CardImage> cards) {

	    Collections.sort(cards, new Comparator<CardImage>() {
	        public int compare(CardImage o1, CardImage o2) {

	            int cost1 = o1.getCard().getCost();
	            int cost2 = o2.getCard().getCost();

	            
	            return cost1 < cost2 ? -1
	                    : cost1 > cost2 ? 1
	                    : 0;
	        }
	    });
	}

	public Creature getCreature() {
		return creature;
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(card);
		out.writeBoolean(enabled);
		out.writeBoolean(isHighlighted);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		card = (Card)in.readObject();
		enabled = in.readBoolean();
		isHighlighted = in.readBoolean();
	}

	@Override
	public String toString() {
		return String.format("CardImage [card=%s, enabled=%s, isHighlighted=%s]", card, enabled, isHighlighted);
	}

	
	
}
