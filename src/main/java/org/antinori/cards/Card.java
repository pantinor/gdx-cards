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
	private boolean damagingSpell = false;
	private boolean targetable = false;
	private boolean wall = false;

	@Override
	public String toString() {
		return String.format("%s type=%s attack=%s life=%s cost=%s spell=%s wall=%s desc%s", cardname, type, attack, life, cost, spell, wall, desc);
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
		c.setOriginalLife(this.originalLife);
		c.setOriginalAttack(this.originalAttack);
		c.setSelfInflictingDamage(this.selfInflictingDamage);
		c.setCardname(this.cardname);
		c.setCost(this.cost);
		c.setDesc(this.desc);
		c.setSpell(this.spell);
		c.setWall(this.wall);
		c.setTargetable(this.targetable);
		c.setDamagingSpell(this.damagingSpell);
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
		return targetable;
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

	public boolean isDamagingSpell() {
		return damagingSpell;
	}

	public void setDamagingSpell(boolean damagingSpell) {
		this.damagingSpell = damagingSpell;
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

	
}