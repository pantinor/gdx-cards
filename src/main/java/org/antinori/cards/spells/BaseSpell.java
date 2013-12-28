package org.antinori.cards.spells;import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.Spell;

public class BaseSpell extends BaseFunctions implements Spell {
	
	CardImage targetedCardImage;
		
	public BaseSpell(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		
		this.isSpell = true;
		
		this.game = game;
		this.card = card;
		this.cardImage = cardImage;
		
		this.owner = owner;
		this.opponent = opponent;
		
		this.opposingPlayer = opponent.getPlayerInfo();
		this.ownerPlayer = owner.getPlayerInfo();
		
	}
	
	public void setTargeted(CardImage target) {
		targetedCardImage = target;
	}

	public void onCast() throws GameOverException {
				
		ownerPlayer.decrementStrength(card.getType(), card.getCost());
		
		Cards.logScrollPane.add(this.owner.getPlayerInfo().getPlayerClass().getTitle() + " casts " + this.card.getCardname());

		game.moveCardActorOnMagic(cardImage, owner);

	}

	
	protected int adjustDamage(int currentDamageValue) {
		int qualifiedDamageValue = currentDamageValue;
		CardImage[] teamCards = owner.getSlotCards();
		for (CardImage ci : teamCards) {
			if (ci == null) continue;
			if (ci.getCard().getName().equalsIgnoreCase("FaerieApprentice")) {
				qualifiedDamageValue = qualifiedDamageValue + 1;
			}
		}
		return qualifiedDamageValue;
	}
	

}
