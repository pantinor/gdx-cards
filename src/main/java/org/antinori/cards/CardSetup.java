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
    
    	List<Card> picks = cs.getCardsByType(CardType.FIRE, 4);
        System.out.println(picks.size());
       
	}

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
                    
                    c.setAttack(getAttrNumber( n1, "attack" ));
            		c.setOriginalAttack(c.getAttack());

                    c.setLife(getAttrNumber( n1, "life" ));
            		c.setOriginalLife(c.getLife());

                    Boolean spell = Boolean.parseBoolean(getAttrText( n1, "spell" ));
                    c.setSpell(spell);
                    
                    Boolean targetable = Boolean.parseBoolean(getAttrText( n1, "targetable" ));
                    c.setTargetable(targetable);
                    
                    Boolean targetableOnEmptySlot = Boolean.parseBoolean(getAttrText( n1, "targetableOnEmptySlot" ));
                    c.setTargetableOnEmptySlotOnly(targetableOnEmptySlot);
                    
                    Card.TargetType target = Card.fromTargetTypeString(getAttrText( n1, "target" ));
                    c.setTargetType(target);
                    
                    int cost = getAttrNumber( n1, "summoningCost" );
                    if (spell) {
                    	cost = getAttrNumber( n1, "castingCost" );
                    }
                    c.setCost(cost);
                    
                    int selfInflicting = getAttrNumber( n1, "selfInflictingDamage" );
                    c.setSelfInflictingDamage(selfInflicting);
                    
                    Boolean wall = Boolean.parseBoolean(getAttrText( n1, "wall" ));
                    c.setWall(wall);
                    
                    c.setMustBeSummoneOnCard(getAttrText(n1, "mustBeSummoneOnCard"));

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
		return result.clone();
	}

	
	/**
	 * Get shuffled selection of cards of the number indicated.
	 */
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
	
	public CardImage getCardImageByName(TextureAtlas atlas1, TextureAtlas atlas2, String name) throws Exception {
    	Card c = getCardByName(name);
    	Sprite sp = atlas1.createSprite(c.getName().toLowerCase());
		if (sp == null) {
    		sp = atlas2.createSprite(c.getName().toLowerCase());
    		if (sp != null) sp.flip(false, true); //tga files need to be flipped twice
		}
    		
		if (sp == null) 
			throw new Exception("Sprite is null for card: " + c);
		
		sp.flip(false, true);
	
		CardImage img = new CardImage(sp, c);
    	
    	return img;
		
	}
	
	public List<Card> getCardsByType(CardType type, int maxNumber) {
		
//		List<Card> result = new ArrayList<Card>();
//		//debugging
//		if (type == CardType.FIRE) {
//			result.add(getCardByName("walloffire"));
//			result.add(getCardByName("MinotaurCommander"));
//			result.add(getCardByName("PriestofFire"));
//			result.add(getCardByName("OrcChieftain"));
//			return result;
//		}
//		if (type == CardType.WATER) {
//		result.add(getCardByName("FaerieApprentice"));
//		result.add(getCardByName("FaerieSage"));
//		result.add(getCardByName("CalltoThunder"));
//		result.add(getCardByName("Titan"));
//		return result;
//	}
//		if (type == CardType.AIR) {
//		result.add(getCardByName("FaerieApprentice"));
//		result.add(getCardByName("FaerieSage"));
//		result.add(getCardByName("CalltoThunder"));
//		result.add(getCardByName("Titan"));
//		return result;
//	}
//		if (type == CardType.EARTH) {
//		result.add(getCardByName("FaerieApprentice"));
//		result.add(getCardByName("FaerieSage"));
//		result.add(getCardByName("CalltoThunder"));
//		result.add(getCardByName("Titan"));
//		return result;
//	}
		
		
		
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
				Card c = result.get(rand).clone();
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
    
    private int getAttrNumber( Node n, String attr ) throws Exception {
        NamedNodeMap attrs = n.getAttributes();
        if ( attrs == null ) return 0;

        Node valueNode = attrs.getNamedItem(attr);
        if ( valueNode == null ) return 0;

        return Integer.parseInt(valueNode.getNodeValue());
    }

}
