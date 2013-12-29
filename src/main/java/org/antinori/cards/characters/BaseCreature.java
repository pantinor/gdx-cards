package org.antinori.cards.characters;

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.Sound;
import org.antinori.cards.Sounds;

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

	public void onSummoned() throws GameOverException {
		
		Cards.logScrollPane.add(this.owner.getPlayerInfo().getPlayerClass().getTitle() + " summoned " + cardImage.getCard().getCardname() + ".");

		ownerPlayer.decrementStrength(card.getType(), card.getCost());
		
		if (card.getSelfInflictingDamage() > 0) {
			Cards.logScrollPane.add(cardImage.getCard().getCardname() + " inflicts " + card.getSelfInflictingDamage() + " damage to owner.");
			owner.decrementLife(card.getSelfInflictingDamage(), game, false);
		}


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

	public void onAttack() throws GameOverException {
		int attack = this.card.getAttack();
		CardImage[] enemyCards = opponent.getSlotCards();
		if (enemyCards[slotIndex] != null) {
			damageSlot(enemyCards[slotIndex], slotIndex, opponent, attack);
		} else {
			damageOpponent(attack);
		}
		game.moveCardActorOnBattle(cardImage, owner);
	}

	public int onAttacked(int damage) throws GameOverException {
		
		int nl = slotIndex - 1;
		int nr = slotIndex + 1;
		
		CardImage[] teamCards = owner.getSlotCards();
		CardImage[] opposingCards = opponent.getSlotCards();
		
		if (opposingCards[slotIndex] != null) {
			String opposingCardName = opposingCards[slotIndex].getCard().getName();
			if (opposingCardName.equalsIgnoreCase("justicar")) {
				damage = damage * 2;
			}
		}

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
		
		game.animateDamageText(damage, cardImage.getX() + 60, cardImage.getY() + 10, cardImage.getX() + 60, cardImage.getY() + 69 );
		
		return damage;

	}

	public void onDying() throws GameOverException {

		int nl = slotIndex - 1;
		int nr = slotIndex + 1;

		String name = this.card.getName();

		CardImage[] teamCards = owner.getSlotCards();
		CardImage[] opposingCards = opponent.getSlotCards();

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
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = opposingCards[index];
			if (ci == null) continue;
			if (ci.getCard().getName().equalsIgnoreCase("ghoul")) {
				ci.getCard().incrementAttack(1);
				Sounds.play(Sound.POSITIVE_EFFECT);
			}
			if (ci.getCard().getName().equalsIgnoreCase("KeeperofDeath")) {
				BaseCreature bc = (BaseCreature)ci.getCreature();
				bc.ownerPlayer.incrementStrength(CardType.DEATH, 1);
				Sounds.play(Sound.POSITIVE_EFFECT);
			}
		}
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = teamCards[index];
			if (ci == null) continue;
			if (ci.getCard().getName().equalsIgnoreCase("KeeperofDeath")) {
				BaseCreature bc = (BaseCreature)ci.getCreature();
				bc.ownerPlayer.incrementStrength(CardType.DEATH, 1);
				Sounds.play(Sound.POSITIVE_EFFECT);
			}
		}

	}


	public void startOfTurnCheck() throws GameOverException {
		
	}

}
