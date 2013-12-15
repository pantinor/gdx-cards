package org.antinori.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.Color;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static CardType[] TYPES = {CardType.FIRE, CardType.AIR, CardType.WATER, CardType.EARTH, CardType.OTHER};
		
	private String imgName = "face1";
	private Specializations playerClass = Specializations.Cleric;
	private int life = 60;
	private String id = UUID.randomUUID().toString();
	
	private int strengthFire = 0;
	private int strengthAir = 0;
	private int strengthEarth = 0;
	private int strengthWater = 0;
	private int strengthSpecial = 0;
	
	private transient PlayerListener listener;
	
	private List<CardImage> fireCards = new ArrayList<CardImage>();
	private List<CardImage> airCards = new ArrayList<CardImage>();
	private List<CardImage> waterCards = new ArrayList<CardImage>();
	private List<CardImage> earthCards = new ArrayList<CardImage>();
	private List<CardImage> specialCards = new ArrayList<CardImage>();

	
	public Player() {
				
		Dice dice = new Dice(1,6);
		strengthFire = dice.roll();
		strengthAir = dice.roll();
		strengthEarth = dice.roll();
		strengthWater = dice.roll();
		strengthSpecial = dice.roll();
	}
	
	public String getImgName() {
		return imgName;
	}
	public Specializations getPlayerClass() {
		return playerClass;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public void setPlayerClass(Specializations playerClass) {
		this.playerClass = playerClass;
	}
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {		
		this.life = life;
	}
	
	public void incrementLife(int inc, boolean notify) {
		this.life += inc;
		if (notify && listener != null) listener.incrementLife(inc);

	}
	public void decrementLife(int dec, boolean notify) {
		this.life -= dec;
		if (notify && listener != null) listener.decrementLife(dec);
	}
	
	public int getStrengthFire() {
		return strengthFire;
	}
	public int getStrengthAir() {
		return strengthAir;
	}
	public int getStrengthEarth() {
		return strengthEarth;
	}
	public int getStrengthWater() {
		return strengthWater;
	}
	public int getStrengthSpecial() {
		return strengthSpecial;
	}
	public void setStrengthFire(int strengthFire) {
		this.strengthFire = strengthFire;
	}
	public void setStrengthAir(int strengthAir) {
		this.strengthAir = strengthAir;
	}
	public void setStrengthEarth(int strengthEarth) {
		this.strengthEarth = strengthEarth;
	}
	public void setStrengthWater(int strengthWater) {
		this.strengthWater = strengthWater;
	}
	public void setStrengthSpecial(int strengthSpecial) {
		this.strengthSpecial = strengthSpecial;
	}
	
	
	
	public List<CardImage> getFireCards() {
		return fireCards;
	}

	public List<CardImage> getAirCards() {
		return airCards;
	}

	public List<CardImage> getWaterCards() {
		return waterCards;
	}

	public List<CardImage> getEarthCards() {
		return earthCards;
	}

	public List<CardImage> getSpecialCards() {
		return specialCards;
	}
	
	public int getStrength(CardType type) {
		switch (type) {
		case FIRE:return this.strengthFire;
		case AIR:return this.strengthAir;
		case WATER:return this.strengthWater;
		case EARTH:return this.strengthEarth;
		default: return this.strengthSpecial;
		}
	}

	public void incrementStrengthAll(int incr, boolean notify) {
		strengthFire += incr;
		strengthAir += incr;
		strengthEarth += incr;
		strengthWater += incr;
		strengthSpecial += incr;
		
		if (notify && listener != null) listener.incrementStrengthAll(incr);
	}
	
	public void decrementStrength(CardType type, int cost, boolean notify) {
		switch (type) {
		case FIRE:this.strengthFire -= cost;break;
		case AIR:this.strengthAir -= cost;break;
		case WATER:this.strengthWater -= cost;break;
		case EARTH:this.strengthEarth -= cost;break;
		default:this.strengthSpecial -= cost;break;
		}
		
		if (notify && listener != null) listener.decrementStrength(cost, type);

	}
	
	public void incrementStrength(CardType type, int cost, boolean notify) {
		switch (type) {
		case FIRE:this.strengthFire += cost;break;
		case AIR:this.strengthAir += cost;break;
		case WATER:this.strengthWater += cost;break;
		case EARTH:this.strengthEarth += cost;break;
		default:this.strengthSpecial += cost;break;
		}
		if (notify && listener != null) listener.incrementStrength(cost, type);

	}
	
	public void enableDisableCards(CardType type) {
		int pstr = getStrength(type);
		List<CardImage> cards = getCards(type);
		for (CardImage card : cards) {
			if (card.getCard().getCost() <= pstr) {
				card.setEnabled(true);
				card.setColor(Color.WHITE);
			} else {
				card.setEnabled(false);
				card.setColor(Color.DARK_GRAY);
			}
		}
	}
	
	public void setCards(CardType type, List<CardImage> cards) {
		switch(type) {
		case FIRE:fireCards = cards;break;
		case AIR:airCards = cards;break;
		case WATER:waterCards = cards;break;
		case EARTH:earthCards = cards;break;
		default:specialCards = cards;break;
		}
	}
	
	public List<CardImage> getCards(CardType type) {
		List<CardImage> cards;
		switch(type) {
		case FIRE:cards = fireCards;break;
		case AIR:cards = airCards;break;
		case WATER:cards = waterCards;break;
		case EARTH:cards = earthCards;break;
		default:cards = specialCards;break;
		}
		return cards;
	}
	
	public CardImage pickBestEnabledCard() {
		CardImage pick = null;
		for (CardType type : TYPES) {
			CardImage c = pickBestEnabledCard(type);
			if (c == null) continue;
			if (pick == null) {
				pick = c;
			} else if (c.getCard().getCost() > pick.getCard().getCost()) {
				pick = c;
			}
		}
		return pick;
	}
	
	public CardImage pickBestEnabledCard(CardType type) {
		CardImage card = null;
		List<CardImage> cards = getCards(type);
		int highest_cost = 0;
		for (CardImage c : cards) {
			if (!c.isEnabled()) 
				continue;
			if (c.getCard().getCost() > highest_cost) {
				highest_cost = c.getCard().getCost();
				card = c;
			}
		}
		return card;
	}
	
	public CardImage pickRandomEnabledCard() {
		Dice dice = new Dice(1,5);
		int roll = dice.roll();
		CardType type = TYPES[roll - 1];
		return pickRandomEnabledCard(type);
	}
	
	public CardImage pickRandomEnabledCard(CardType type) {
		CardImage ci = null;
		List<CardImage> cards = getCards(type);
		int enabledCount = 0;
		for (CardImage card : cards) {
			if (card.isEnabled()) {
				enabledCount ++;
			}
		}
		
		if (enabledCount == 0) {
			return null;
		}
		
		Dice dice = new Dice(1,4);
		do {
			int roll = dice.roll();
			ci = cards.get(roll - 1);
		} while (ci == null || !ci.isEnabled());
		
		return ci;
	}

	@Override
	public String toString() {
		return String.format("Player [id=%s imgName=%s, playerClass=%s, life=%s, strengthFire=%s, strengthAir=%s, strengthEarth=%s, strengthWater=%s, strengthSpecial=%s, fireCards=%s, airCards=%s, waterCards=%s, earthCards=%s, specialCards=%s]", 
				id, imgName, playerClass, life, strengthFire, strengthAir, strengthEarth, strengthWater, strengthSpecial, fireCards, airCards, waterCards, earthCards, specialCards);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public void setListener(PlayerListener listener) {
		this.listener = listener;
	}	
	

}
