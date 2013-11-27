package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Armageddon extends BaseSpell {

	public Armageddon(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}
	
	public void onCast() {
		
		int value = 8 + ownerPlayer.getStrengthFire();
		damageAll(false, value);
		damagePlayerSpellcasting(value);

	}

}
