package org.antinori.cards.characters;

import java.util.List;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.Player;

public class GoblinShaman extends BaseCreature {
	public GoblinShaman(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();

		for (CardType type : Player.TYPES) {
			List<CardImage> cards = opposingPlayer.getCards(type);
			for (CardImage ci : cards) {
				if (!ci.getCard().isSpell()) continue;
				int cost = ci.getCard().getCost();
				ci.getCard().setCost(cost + 1);
			}
		}

	}

	public void onDying() throws GameOverException {
		super.onDying();
		for (CardType type : Player.TYPES) {
			List<CardImage> cards = opposingPlayer.getCards(type);
			for (CardImage ci : cards) {
				if (!ci.getCard().isSpell()) continue;
				int cost = ci.getCard().getCost();
				ci.getCard().setCost(cost - 1);
			}
		}
	}
}
