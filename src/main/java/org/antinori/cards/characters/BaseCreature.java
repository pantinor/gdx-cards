package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.PlayerImage;

public class BaseCreature extends BaseFunctions implements Creature {

	public BaseCreature(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {

		this.game = game;
		this.card = card;
		this.cardImage = cardImage;

		this.slotIndex = slotIndex;

		this.owner = owner;
		this.opponent = opponent;

		this.ownerPlayer = owner.getPlayerInfo();
		this.opposingPlayer = opponent.getPlayerInfo();

	}

	public void onSummoned() {

		Cards.logScrollPane.add(this.owner.getPlayerInfo().getPlayerClass().getTitle() + " summoned " + cardImage.getCard().getCardname() + ".");

		ownerPlayer.decrementStrength(card.getType(), card.getCost(), false);


		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(owner, 1);
		}

		CardImage[] teamCards = owner.getSlotCards();

		for (int index = 0; index < 6; index++) {
			if (index == slotIndex)
				continue;
			CardImage ci = teamCards[index];
			if (ci == null)
				continue;
			if (ci.getCard().getName().equalsIgnoreCase("minotaurcommander")) {
				this.card.incrementAttack(1);
			}
		}

		if (nl >= 0 && teamCards[nl] != null) {

			String leftNeighbor = teamCards[nl].getCard().getName();

			if (leftNeighbor.equalsIgnoreCase("merfolkoverlord")) {
				onAttack();
			}

			if (leftNeighbor.equalsIgnoreCase("orcchieftain")) {
				this.card.incrementAttack(2);
			}

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nl].getCard().incrementAttack(2);
			}

		}

		if (nr <= 5 && teamCards[nr] != null) {

			String rightNeighbor = teamCards[nr].getCard().getName();

			if (rightNeighbor.equalsIgnoreCase("merfolkoverlord")) {
				onAttack();
			}

			if (rightNeighbor.equalsIgnoreCase("orcchieftain")) {
				this.card.incrementAttack(2);
			}

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nr].getCard().incrementAttack(2);
			}

		}

	}

	public void onAttack() {

		int attack = this.card.getAttack();

		CardImage[] enemyCards = opponent.getSlotCards();

		if (enemyCards[slotIndex] != null) {

			damageSlot(enemyCards[slotIndex], slotIndex, opponent, attack);

		} else {

			damageOpponent(attack);
		}

		game.moveCardActorOnBattle(cardImage, owner);

	}

	public void onAttacked(int damage) {
		
		int nl = slotIndex - 1;
		int nr = slotIndex + 1;
		
		CardImage[] teamCards = owner.getSlotCards();

		if (nl >= 0 && teamCards[nl] != null) {

			String leftNeighbor = teamCards[nl].getCard().getName();

			if (leftNeighbor.equalsIgnoreCase("holyguard")) {
				damage = damage - 2;
			}

		}

		if (nr <= 5 && teamCards[nr] != null) {

			String rightNeighbor = teamCards[nr].getCard().getName();

			if (rightNeighbor.equalsIgnoreCase("holyguard")) {
				damage = damage - 2;
			}

		}
		
		if (damage < 0) damage = 0;

		card.decrementLife(damage);

	}

	public void onDying() {

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		CardImage[] teamCards = owner.getSlotCards();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(owner, -1);
		}

		if (nl >= 0 && teamCards[nl] != null) {

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nl].getCard().decrementAttack(2);
			}
			if (name.equalsIgnoreCase("minotaurcommander")) {
				teamCards[nl].getCard().decrementAttack(1);
			}

		}

		if (nr <= 5 && teamCards[nr] != null) {

			if (name.equalsIgnoreCase("orcchieftain")) {
				teamCards[nr].getCard().decrementAttack(2);
			}
			if (name.equalsIgnoreCase("minotaurcommander")) {
				teamCards[nr].getCard().decrementAttack(1);
			}

		}

	}


	public void startOfTurnCheck() {
		
	}

	public void setNetworkEventFlag(boolean flag) {
		this.remoteEvent = flag;
	}


}
