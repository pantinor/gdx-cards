package org.antinori.cards;

import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private int originalAttack = 0;
	private int attack;
	
	private int life = 0;
	private int originalLife = 0;
	
	private int cost;
	private int selfInflictingDamage = 0;
	
	private String cardname;
	private String desc;
	private CardType type;
	private boolean spell = false;
	
	public static enum TargetType {OWNER, OPPONENT, ANY};
	
	private TargetType targetType = TargetType.OWNER;
	
	private boolean targetable = false;
	private boolean targetableOnEmptySlotOnly = false;

	private boolean wall = false;
	
	private String mustBeSummoneOnCard;


	public Card(CardType type) {
		this.type = type;
	}
	
	public Card clone() {
		Card c = new Card(this.type);
		c.setName(this.name);
		c.setAttack(this.attack);
		c.setLife(this.life);
		c.setOriginalLife(this.originalLife);
		c.setOriginalAttack(this.originalAttack);
		c.setSelfInflictingDamage(this.selfInflictingDamage);
		c.setCardname(this.cardname);
		c.setCost(this.cost);
		c.setDesc(this.desc);
		c.setSpell(this.spell);
		c.setWall(this.wall);
		c.setTargetable(this.targetable);
		c.setTargetableOnEmptySlotOnly(this.targetableOnEmptySlotOnly);
		c.setTargetType(this.targetType);
		c.setMustBeSummoneOnCard(this.mustBeSummoneOnCard);
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
	
	public void incrementAttack(int inc) {
		if (wall) return;
		this.attack += inc;

	}
	public void decrementAttack(int dec) {
		if (wall) return;
		this.attack -= dec;
	}

	public void setLife(int life) {		
		this.life = life;
	}
	
	public void incrementLife(int inc) {
		this.life += inc;

	}
	public void decrementLife(int dec) {
		this.life -= dec;
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
		return this.targetable;
	}

	public void setTargetable(boolean targetable) {
		this.targetable = targetable;
	}

	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public int getOriginalLife() {
		return originalLife;
	}

	public void setOriginalLife(int originalLife) {
		this.originalLife = originalLife;
	}

	public int getOriginalAttack() {
		return originalAttack;
	}

	public void setOriginalAttack(int originalAttack) {
		this.originalAttack = originalAttack;
	}

	public int getSelfInflictingDamage() {
		return selfInflictingDamage;
	}

	public void setSelfInflictingDamage(int selfInflictingDamage) {
		this.selfInflictingDamage = selfInflictingDamage;
	}

	public String getMustBeSummoneOnCard() {
		return mustBeSummoneOnCard;
	}

	public void setMustBeSummoneOnCard(String mustBeSummoneOnCard) {
		this.mustBeSummoneOnCard = mustBeSummoneOnCard;
	}

	public boolean isTargetableOnEmptySlotOnly() {
		return targetableOnEmptySlotOnly;
	}

	public void setTargetableOnEmptySlotOnly(boolean targetableOnEmptySlotOnly) {
		this.targetableOnEmptySlotOnly = targetableOnEmptySlotOnly;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public static TargetType fromTargetTypeString(String text) {
		if (text != null) {
			for (TargetType c : TargetType.values()) {
				if (c.toString().equalsIgnoreCase(text)) {
					return c;
				}
			}
		}
		return TargetType.OWNER;
	}
	
	@Override
	public String toString() {
		return String.format("%s\t\t\t%s\tattack=%s\tlife=%s\tcost=%s\tspell=%s", cardname, type, attack, life, cost, spell);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}