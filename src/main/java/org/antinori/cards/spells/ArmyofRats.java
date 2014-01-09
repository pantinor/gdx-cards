package org.antinori.cards.spells;

import java.util.ArrayList;
import java.util.List;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Dice;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class ArmyofRats extends BaseSpell {
	public ArmyofRats(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		damageAll(opponent, adjustDamage(12));
		
		List<CardImage> cards = new ArrayList<CardImage>();
		for (int index = 0; index<6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null) continue;
			cards.add(ci);
		}
		
		if (cards.size() == 0) return;
		CardImage ci = null;
		if (cards.size() == 1) ci = cards.get(0);
		Dice dice = new Dice(1, cards.size());
		ci = cards.get(dice.roll()-1);
		
		damageSlot(ci, ci.getCreature().getIndex(), owner, adjustDamage(12));
	}
}
