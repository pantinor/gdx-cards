package org.antinori.cards;

import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int attack;
	private int life = 0;
	private int cost;
	private String cardname;
	private String desc;
	private CardType type;
	private boolean spell = false;
	private boolean damagingSpell = false;
	private boolean targetable = false;
	private boolean wall = false;
	private CardListener listener;

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
		c.setAttack(this.attack, false);
		c.setLife(this.life, false);
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
	
	public void setAttack(int attack, boolean notify) {
		this.attack = attack;
		if (notify && listener != null) listener.setAttack(this, attack);
	}
	
	public void incrementAttack(int inc, boolean notify) {
		if (wall) return;
		this.attack += inc;
		if (notify && listener != null) listener.setAttack(this, attack);

	}
	public void decrementAttack(int dec, boolean notify) {
		if (wall) return;
		this.attack -= dec;
		if (notify && listener != null) listener.setAttack(this, attack);
	}

	public void setLife(int life, boolean notify) {		
		this.life = life;
		if (notify && listener != null) listener.setLife(this, this.life);
	}
	
	public void incrementLife(int inc, boolean notify) {
		this.life += inc;
		if (notify && listener != null) listener.incrementLife(this, inc);

	}
	public void decrementLife(int dec, boolean notify) {
		this.life -= dec;
		if (notify && listener != null) listener.decrementLife(this, dec);
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

	public CardListener getListener() {
		return listener;
	}

	public void setListener(CardListener listener) {
		this.listener = listener;
	}

	
}