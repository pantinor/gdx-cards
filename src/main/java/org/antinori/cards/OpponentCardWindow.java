package org.antinori.cards;

import java.util.List;

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
				add(clone1);
				add(clone2);
				add(clone3);
				add(clone4);
				add(clone5);
				row();
			}
			
			row().space(10).padBottom(10);


			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
