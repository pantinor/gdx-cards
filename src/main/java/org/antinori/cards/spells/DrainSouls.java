package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.characters.BaseCreature;

public class DrainSouls extends BaseSpell {
	public DrainSouls(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		
		super.onCast();
		
		int count = 0;
		for (int index = 0; index < 6; index ++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null) continue;
			BaseCreature bc = (BaseCreature) ci.getCreature();
			disposeCardImage(owner, bc.slotIndex);
			count ++;
		}
		for (int index = 0; index < 6; index ++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null) continue;
			BaseCreature bc = (BaseCreature) ci.getCreature();
			disposeCardImage(opponent, bc.slotIndex);
			count ++;
		}
		int heal = count * 2;
		this.owner.incrementLife(heal, game);
		
		
		
		changeSpellCard("RageofSouls");

	}
}
