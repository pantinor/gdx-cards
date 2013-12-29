package org.antinori.cards;

import org.apache.commons.collections.Predicate;

public class CardPredicate implements Predicate {
	private String name;
	private CardType type;
	private Boolean isSpell;

	public CardPredicate(String name) {
		super();
		this.name = name;
	}
	public CardPredicate(CardType type) {
		super();
		this.type = type;
	}
	public CardPredicate(Boolean isSpell) {
		super();
		this.isSpell = isSpell;
	}

	public boolean evaluate(Object o) {
		Card c = (Card) o;
		if (this.name != null) 
			return c.getName().equalsIgnoreCase(this.name);
		if (this.type != null) 
			return c.getType().equals(this.type);
		if (this.isSpell != null) 
			return c.isSpell() == this.isSpell;
		return false;
	}
}
