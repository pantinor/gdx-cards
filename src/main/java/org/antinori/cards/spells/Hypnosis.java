package org.antinori.cards.spells;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Arrays;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class Hypnosis extends BaseSpell {
	public Hypnosis(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		

		int[] attacks = {0,0,0,0,0,0};

		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			attacks[index] = ci.getCard().getAttack();
		}
		
		Arrays.sort(attacks); //sort ascending
		
		CardImage[] hypnotizedCards = new CardImage[2];
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			if (attacks[5] == ci.getCard().getAttack()) {
				hypnotizedCards[0] = ci;
			}
			if (attacks[4] == ci.getCard().getAttack()) {
				hypnotizedCards[1] = ci;
			}
		}
		
		for (CardImage ci : hypnotizedCards) {
			if (ci == null)	continue;
			//TODO get this to work
			ci.addAction(sequence(scaleTo(1.2f, 1.2f, 0.65f), scaleTo(1.0f, 1.0f, 0.65f)));
			damagePlayer(opponent, ci.getCard().getAttack());
		}
		
	}
}
