package org.antinori.cards;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class OpponentCardWindow extends Window {

	Cards game;
	Player opponent;

	public OpponentCardWindow(String title, Player opponent, Cards game, Skin skin) {
		super(title, skin);
		this.game = game;
		this.opponent = opponent;

		defaults().padTop(2);
		defaults().padBottom(2);
		defaults().padLeft(2);
		defaults().padRight(2);


		try {
			
			add().space(3);
			for (int i=0;i<5;i++) {
				Label l = new Label(game.topStrengthLabels[i].getText(), Cards.whiteStyle);
				add(l);
			}
			row();
			
			
			List<CardImage> fire = opponent.getCards(CardType.FIRE);
			List<CardImage> air = opponent.getCards(CardType.AIR);
			List<CardImage> water = opponent.getCards(CardType.WATER);
			List<CardImage> earth = opponent.getCards(CardType.EARTH);
			List<CardImage> special = opponent.getCards(CardType.OTHER);

			for (int i = 0; i < 4; i++) {
				
				Actor a1 = getCard(fire,i);
				Actor a2 = getCard(air,i);
				Actor a3 = getCard(water,i);
				Actor a4 = getCard(earth,i);
				Actor a5 = getCard(special,i);

				add().space(3);
				add(a1);
				add(a2);
				add(a3);
				add(a4);
				add(a5);
				
				row();
			}
			
			row();
			add().space(6);


			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private Actor getCard(List<CardImage> cards, int index) {
		
		if (cards == null || cards.size() < index + 1) 
			return getEmptySlotImage();
		
		CardImage ci = cards.get(index);
		if (ci == null) {
			return getEmptySlotImage();
		}
		
		CardImage clone = ci.clone();
		clone.setEnabled(cards.get(index).isEnabled());
		clone.setColor(cards.get(index).getColor());
		return clone;
	}
	
	private Image getEmptySlotImage() {
		Pixmap p = new Pixmap(89, 100, Pixmap.Format.RGBA8888);
		p.setColor(Color.CLEAR);
		p.fill();
		Texture texture = new Texture(p);
		return new Image(texture);
	}


}
