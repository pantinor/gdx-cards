package org.antinori.cards;

public class Card {
	
	String name;
	int attack;
	int life = 0;
	int cost;
	String cardname;
	String desc;
	CardType type;
	boolean spell = false;
	boolean targetable = false;

	@Override
	public String toString() {
		return String.format("%s type=%s attack=%s life=%s cost=%s spell=%s desc%s", cardname, type, attack, life, cost, spell, desc);
		//return String.format("<card cardname=\"%s\" type=\"%s\" name=\"%s\" attack=\"%s\" life=\"%s\" cost=\"%s\" spell=\"%s\" desc=\"%s\" />", cardname, type, name, attack, life, cost, spell, desc);
	}

	public Card(CardType type) {
		this.type = type;
	}
	
	public Card clone() {
		Card c = new Card(this.type);
		c.setName(this.name);
		c.setAttack(this.attack);
		c.setLife(this.life);
		c.setCardname(this.cardname);
		c.setCost(this.cost);
		c.setDesc(this.desc);
		c.setSpell(this.spell);
		c.setTargetable(this.targetable);
		return c;
	}

	public String getName() {
		return name;
	}

	public int getAttack() {
		return attack;
	}

	public int getLife() {
		return life;
	}

	public String getCardname() {
		return cardname;
	}

	public CardType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public boolean isSpell() {
		return spell;
	}

	public void setSpell(boolean spell) {
		this.spell = spell;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isTargetable() {
		return targetable;
	}

	public void setTargetable(boolean targetable) {
		this.targetable = targetable;
	}

	
}