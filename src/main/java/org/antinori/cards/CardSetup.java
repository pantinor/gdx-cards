package org.antinori.cards;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

@SuppressWarnings("unchecked")
public class CardSetup {
	
	Set<Card> cardSet = new HashSet<Card>();
	
	
	Set<Card> creatureCards = new HashSet<Card>();
	Set<Card> spellCards = new HashSet<Card>();

	
	public static void main(String[] args) {
		
		CardSetup cs = new CardSetup();
		cs.parseCards();
		
		Card result = cs.getCardByName("GoblinBerserker");
        System.out.println(result.toString());
        
    	List<Card> set = cs.getCardsByType(CardType.FIRE);
        System.out.println(set.size());
        
    	List<Card> picks = cs.getCardsByType(CardType.FIRE, 4);
        System.out.println(picks.size());
       
	}
	
	
	
	/*
	 * To explain what this means: There are certain pairs of cards which it is
	 * not possible to get at once. This is usually because they have an
	 * interaction which makes them overpowered when used together, but can
	 * sometimes be because they are too weak together. These have been kept to
	 * a minimum as they are obviously undesirable, but have proved necessary to
	 * avoid unbalanced deals. For those of you who are not familiar with this
	 * list, it is well worth getting to know what these combos are. It gives
	 * you vital extra information during a duel. As soon as you the opponent
	 * use one of the cards in a banned combo, you can instantly be sure that
	 * they do not have the other card. Also consider that the opponent knows
	 * this information about you when you play one of those cards.
	 * 
	 * Basic power bans:
	 * 
	 * Orc Chieftain + Forest Sprite 
	 * Meditation + Stone Rain 
	 * Inferno + Armageddon 
	 * Elf Hermit + Nature's Fury
	 * 
	 * If you have Phoenix, you cannot have more than 1 of the following:
	 * Armageddon, Acidic Rain, Stone Rain, Drain Souls (Death 7)
	 * 
	 * Demonologist: 
	 * Greater Demon + Armageddon
	 * 
	 * Illusionist: 
	 * Armageddon + Wall of Reflection 
	 * Nature's Ritual + Wall of Reflection
	 * 
	 * Necromancer: 
	 * Meditation + Cursed Fog 
	 * Ice Golem + Cursed Fog
	 * 
	 * Chronomancer: 
	 * Nature's Ritual + Chrono Engine
	 * 
	 * Goblin Chieftain: 
	 * Ice Golem + Army of Rats 
	 * Elf Hermit + Rescue Operation
	 * 
	 * Mad Hermit: 
	 * Orc Chieftain + Forest Wolf
	 * 
	 * Vampire Lord: 
	 * Orc Chieftain + Devoted Servant 
	 * Sea Sprite + Chastiser
	 * Giant Spider + Vampire Mystic
	 * 
	 * Cultist: 
	 * Ice Golem + Greater Bargul 
	 * Nature's Fury + Greater Bargul 
	 * Astral Guard + Reaver
	 * 
	 * Golem Master: 
	 * Ice Golem + Stone Rain 
	 * Ice Golem + Armageddon 
	 * 
	 * [Note that these don't involve the Craft cards at all, but apply just because you are using this class]
	 */


	public Set<Card> getCardSet() {
		return cardSet;
	}

	public Set<Card> getCreatureCards() {
		return creatureCards;
	}

	public Set<Card> getSpellCards() {
		return spellCards;
	}



	public void parseCards() {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = CardSetup.class.getResourceAsStream("/cards.xml");
			Document doc = builder.parse(is);
            NodeList locater = doc.getElementsByTagName("cards");
            NodeList cards = locater.item(0).getChildNodes();

            for ( int i = 0; i < cards.getLength(); ++i ) {
                Node n1 = cards.item(i);
                String node_name1 = n1.getNodeName();
                if (node_name1 == "card") {
                    String type = getAttrText( n1, "type" );

                	Card c = new Card(CardType.fromString(type));
                    c.setName(getAttrText( n1, "name" ));
                    c.setCardname(getAttrText( n1, "cardname" ));
                    c.setDesc(getAttrText( n1, "desc" ));
                    
                    c.setAttack(Integer.parseInt(getAttrText( n1, "attack" )));
                    c.setLife(Integer.parseInt(getAttrText( n1, "life" )));
                    
                    Boolean spell = Boolean.parseBoolean(getAttrText( n1, "spell" ));
                    c.setSpell(spell);
                    
                    Boolean targetable = Boolean.parseBoolean(getAttrText( n1, "targetable" ));
                    c.setTargetable(targetable);
                    
                    int cost = Integer.parseInt(getAttrText( n1, "cost2" ));
                    if (spell) {
                    	cost = Integer.parseInt(getAttrText( n1, "cost1" ));
                    }
                    c.setCost(cost);
                    
                    Boolean wall = Boolean.parseBoolean(getAttrText( n1, "wall" ));
                    c.setWall(wall);

                    cardSet.add(c);
                    
                    //System.out.println(c);
                    
                    if (c.isSpell()) {
                    	spellCards.add(c);
                    } else {
                    	creatureCards.add(c);
                    }
                }
            }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Card getCardByName(String name) {
		return getCardByName(name, cardSet);
	}
	
	public Card getCardByName(String name, Set<Card> set) {
		Card result = (Card)CollectionUtils.find(set, new CardPredicate(name.toLowerCase()));
		return result;
	}
	
	public List<Card> getCardsByType(CardType type) {
		List<Card> result = (List<Card>) CollectionUtils.select(cardSet, new CardPredicate(type));
		return result;
	}
	
	public List<CardImage> getCardImagesByType(TextureAtlas atlas1, TextureAtlas atlas2, CardType type, int maxNumber) throws Exception {
    	List<Card> picks = getCardsByType(type, maxNumber);
    	List<CardImage> images = new ArrayList<CardImage>();
    	for (Card c : picks) {
    		
    		Sprite sp = atlas1.createSprite(c.getName().toLowerCase());
    		if (sp == null) {
        		sp = atlas2.createSprite(c.getName().toLowerCase());
        		if (sp != null) sp.flip(false, true); //tga files need to be flipped twice
    		}
    		
    		if (sp == null) 
    			throw new Exception("Sprite is null for card: " + c);
    		
    		sp.flip(false, true);

    		
    		CardImage img = new CardImage(sp, c);
    		images.add(img);
    	}
    	return images;
		
	}
	
	public List<Card> getCardsByType(CardType type, int maxNumber) {
		
		List<Card> result = new ArrayList<Card>();
		//debugging
		if (type == CardType.FIRE) {
			result.add(getCardByName("OrcChieftain"));
			result.add(getCardByName("GoblinBerserker"));
			result.add(getCardByName("Armageddon"));
			result.add(getCardByName("FireElemental"));
			return result;
		}
		
		
		
		return getCardsByType(type, maxNumber, cardSet);
	}

	public List<Card> getCardsByType(CardType type, int maxNumber, Set<Card> set) {
		List<Card> result = (List<Card>) CollectionUtils.select(set, new CardPredicate(type));
		if (maxNumber > result.size()) return result;
		//pick random number of cards up to the max number
		List<Card> picks = new ArrayList<Card>();
		for (int i=0;i<maxNumber;i++) {
			do {
				int rand = new Random().nextInt(result.size());
				Card c = result.get(rand);
				if (picks.contains(c)) continue;
				picks.add(c);
				break;
			} while(true);
			
		}
		return picks;
	}

	
    private String getAttrText( Node n, String attr ) {
        NamedNodeMap attrs = n.getAttributes();
        if ( attrs == null ) return null;

        Node valueNode = attrs.getNamedItem(attr);
        if ( valueNode == null ) return null;

        return valueNode.getNodeValue();
    }

}
