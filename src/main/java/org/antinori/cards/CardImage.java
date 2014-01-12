package org.antinori.cards;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardImage extends Actor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Sprite img;
	private Texture frame;
	
	private static Texture stunned;
	private static Texture healthBox;
	private TextureRegion healthBar;
	
	private Card card;
	private BitmapFont font;
	private boolean enabled;
	private boolean isHighlighted;
	private Creature creature;
	
	public CardImage() {
	}
	
	public CardImage(Sprite img, Card info) {
		this.img = img;
		this.card = info;
		setName(card.getName());
	}
	
	private static void initTextures() {
		Pixmap p = new Pixmap(63, 4, Pixmap.Format.RGBA8888);
		p.setColor(Color.valueOf("105410"));
		p.fill();
		healthBox = new Texture(p);
		
		stunned = new Texture(Gdx.files.classpath("images/stunned.png"));
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if (healthBox == null) {
			initTextures();
		}
		
		if (this.healthBar == null) {
			healthBar = new TextureRegion(healthBox, 0, 0, 63, 4);
		}
		
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		float x = getX();
		float y = getY();
		batch.draw(img, x, y);
		
		if (creature != null) {
			if (creature.mustSkipNextAttack()) {
				batch.draw(stunned, x, y);
			}
		}
		
		batch.draw(frame, x - 3, y - 12);
		
		int at = card.getAttack();
		int co = card.getCost();
		int li = card.getLife();

		if (!card.isSpell()) {
			if (li > 0) {
				font.draw(batch, "" + at, (at>9?x:x+3), y+5);
				font.draw(batch, "" + co, (co>9?x+66:x+69), y+85);
				font.draw(batch, "" + li, (li>9?x+66:x+69), y+5);
			}
		} else {
			font.draw(batch, "" + co, (co>9?x+66:x+69), y+77);
		}
		
		if (creature != null) {
			batch.draw(healthBar, x, y+82);
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
	
	public boolean decrementLife(BaseFunctions attacker, int value, Cards game) throws GameOverException {
		
		creature.onAttacked(attacker, value);
				
		int remainingLife = card.getLife();
		boolean died = (remainingLife < 1);
		
		double percent = (double) remainingLife/ (double) card.getOriginalLife();
		double bar = percent * (double) 63;
		if (remainingLife < 0) bar = 0;
		if (bar > 63) bar = 63; 
		if (healthBar != null) healthBar.setRegion(0, 0, (int) bar, 4);
		
		return died;
	}
	
	public void incrementLife(int value, Cards game) {
		card.incrementLife(value);
		game.animateHealingText(value, this);
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
		return String.format("%s enabled=%s, isHighlighted=%s", card, enabled, isHighlighted);
	}

	
	
}
