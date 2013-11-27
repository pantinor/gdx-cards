package org.antinori.cards;

import org.apache.commons.collections.Predicate;

public class CardPredicate implements Predicate {
	private String name;
	private CardType type;

	public CardPredicate(String name) {
		super();
		this.name = name;
	}
	public CardPredicate(CardType type) {
		super();
		this.type = type;
	}

	public boolean evaluate(Object o) {
		Card c = (Card) o;
		if (this.name != null) 
			return c.getName().equals(this.name);
		if (this.type != null) 
			return c.getType().equals(this.type);
		return false;
	}
}
