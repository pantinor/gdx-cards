package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class IceGuard extends BaseCreature {
	public IceGuard(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		owner.setReceivedDamageModifier(0.50f);
		owner.setReceivedSpellDamageModifier(0.50f);
	}

	public void onAttack() {
		super.onAttack();
	}

	public void onDying() {
		owner.setReceivedDamageModifier(0);
		owner.setReceivedSpellDamageModifier(0);
	}
}
