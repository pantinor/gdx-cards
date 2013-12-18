package org.antinori.cards.characters;import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.CreatureFactory;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.SlotImage;

public class GiantSpider extends BaseCreature {
	public GiantSpider(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		
		int nl = slotIndex - 1;
		int nr = slotIndex + 1;
		
		SlotImage[] slots = owner.getSlots();
		
		try {
	
			if (nl >= 0 && owner.getSlotCards()[nl] == null) {
				addForestSpider(nl, slots[nl]);
			}
			
			if (nr >= 0 && owner.getSlotCards()[nr] == null) {
				addForestSpider(nr, slots[nr]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void addForestSpider(int index, SlotImage slot) throws Exception {
		CardImage orig = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, "ForestSpider");
		CardImage ci1 = orig.clone();
		Creature sp1 = CreatureFactory.getCreatureClass("ForestSpider", game, ci1.getCard(), ci1, index, owner, opponent);
		ci1.setCreature(sp1);
		ci1.setFont(Cards.greenfont);
		ci1.setFrame(Cards.ramka);
		ci1.addListener(game.new TargetedCardListener(owner.getPlayerInfo().getId(), index));
		ci1.addListener(game.sdl);
		slot.setOccupied(true);
		owner.getSlotCards()[index] = ci1;
		ci1.setBounds(slot.getX() + 5, slot.getY() + 26, ci1.getFrame().getWidth(), ci1.getFrame().getHeight());
		game.stage.addActor(ci1);
	}

}
