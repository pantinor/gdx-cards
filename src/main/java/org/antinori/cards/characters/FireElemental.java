package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class FireElemental extends BaseCreature {

	public FireElemental(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		damageAll(opponent, 3);
		damagePlayer(opponent, 3);
	}

	@Override
	public void startOfTurnCheck() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrength(CardType.FIRE));
	}
}
