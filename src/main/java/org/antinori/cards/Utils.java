package org.antinori.cards;

import org.antinori.cards.network.Event;
import org.antinori.cards.network.NetworkEvent;

public class Utils {
	
	public static void attackWithNetworkEvent(Creature c, Player p, int index) throws GameOverException {
		try {
			c.onAttack(); 
		} catch (GameOverException ge) {
			throw ge;
		} finally {
			if (Cards.NET_GAME != null) {
				NetworkEvent ne = new NetworkEvent(Event.CARD_ATTACK, p.getId());
				ne.setSlot(index);
				Cards.NET_GAME.sendEvent(ne);
			}
		}
	}

}
