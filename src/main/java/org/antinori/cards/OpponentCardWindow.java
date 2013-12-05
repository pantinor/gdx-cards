package org.antinori.cards;

import java.util.List;

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
				CardImage clone1 = fire.get(i).clone();
				CardImage clone2 = water.get(i).clone();
				CardImage clone3 = air.get(i).clone();
				CardImage clone4 = earth.get(i).clone();
				CardImage clone5 = special.get(i).clone();
				
				clone1.setEnabled(fire.get(i).isEnabled());
				clone2.setEnabled(water.get(i).isEnabled());
				clone3.setEnabled(air.get(i).isEnabled());
				clone4.setEnabled(earth.get(i).isEnabled());
				clone5.setEnabled(special.get(i).isEnabled());
				
				clone1.setColor(fire.get(i).getColor());
				clone2.setColor(water.get(i).getColor());
				clone3.setColor(air.get(i).getColor());
				clone4.setColor(earth.get(i).getColor());
				clone5.setColor(special.get(i).getColor());
				
				add().space(3);
				add(clone1);
				add(clone2);
				add(clone3);
				add(clone4);
				add(clone5);
				
				row();
			}
			
			row();
			add().space(6);


			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
