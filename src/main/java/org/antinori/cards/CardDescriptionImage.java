package org.antinori.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardDescriptionImage extends Actor {

	Sprite img;
	Texture frame;
	Card card;
	BitmapFont font;
	
	public CardDescriptionImage(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public CardDescriptionImage(Sprite img, Card info) {
		this.img = img;
		this.card = info;
	}

	public CardDescriptionImage(Sprite img, Texture frame, BitmapFont font, Card info, float x, float y) {
		this.img = img;
		this.frame = frame;
		this.card = info;
		this.font = font;
		setX(x);
		setY(y);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if (img == null || frame == null || card == null || font == null) return;

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		float x = getX();
		float y = getY();
		batch.draw(img, x, y);
		batch.draw(frame, x -11, y - 12);
		
		int at = card.getAttack();
		int co = card.getCost();
		int li = card.getLife();
		
		if (!card.isSpell()) {
			font.draw(batch, "" + at, (at>9?x+5:x+7), y+3);
			font.draw(batch, "" + co,(co>9?x+132:x+130), y+3);
			font.draw(batch, "" + li, (li>9?x+131:x+134), y+133);
		} else {
			font.draw(batch, "" + co,(co>9?x+132:x+130), y+3);
		}
		
		font.draw(batch, card.getCardname(), x+190, y);
		font.drawWrapped(batch, card.getDesc(), x+190, y+40, 240);

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

}
