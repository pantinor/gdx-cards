package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;

public class BaseCreature extends BaseFunctions implements Creature {

	public BaseCreature(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {

		this.game = game;
		this.card = card;
		this.cardImage = cardImage;
		this.isComputer = isComputer;
		this.slotIndex = slotIndex;

		this.enemyCards = isComputer ? game.getBottomSlotCards() : game.getTopSlotCards();
		this.teamCards = isComputer ? game.getTopSlotCards() : game.getBottomSlotCards();

		this.ownerPlayer = isComputer ? game.opponent.getPlayerInfo() : game.player.getPlayerInfo();
		this.opposingPlayer = isComputer ? game.player.getPlayerInfo() : game.opponent.getPlayerInfo();

		this.owner = isComputer ? game.opponent : game.player;
		this.opponent = isComputer ? game.player : game.opponent;

	}

	public void onSummoned() {

		System.out.println("onSummoned: " + card);

		ownerPlayer.decrementStrength(card.getType(), card.getCost());

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(true, 1);
		}

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

		System.out.println("onAttack: " + card.getName() + " isComputer: " + isComputer + "" + enemyCards[slotIndex]);

		int attack = this.card.getAttack();

		boolean died = false;
		if (enemyCards[slotIndex] != null) {

			// damage opposing creature
			enemyCards[slotIndex].decrementLife(attack, game);

			int remainingLife = enemyCards[slotIndex].getCard().getLife();
			died = (remainingLife < 1);

			if (died) {
				disposeEnemy(enemyCards[slotIndex], slotIndex);
			}

			// TODO add battle description to log

		} else {

			PlayerImage pi = isComputer ? game.player : game.opponent;

			pi.decrementLife(attack, game, false);

			int remainingLife = opposingPlayer.getLife();
			died = (remainingLife < 1);

			if (died) {
				game.handleGameOver();
			}

			// TODO add battle description to log
		}

		moveCardActorOnBattle();

	}

	public void onAttacked() {

		System.out.println("onAttacked: " + card);

	}

	public void onDying() {

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		if (name.equalsIgnoreCase("minotaurcommander")) {
			enhanceAttackAll(true, -1);
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

	protected void damagePlayer(boolean damageOwner, int value) {
		Player player = damageOwner ? ownerPlayer : opposingPlayer;
		PlayerImage pi = damageOwner ? owner : opponent;

		pi.decrementLife(value, game, false);

		if (player.getLife() < 1) {
			game.handleGameOver();
		}
	}

	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {

	}

}
