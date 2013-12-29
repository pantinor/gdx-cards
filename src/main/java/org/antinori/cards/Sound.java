package org.antinori.cards;

public enum Sound {
		
	BACKGROUND1 ("audio/combat3.ogg", true), 

	MAGIC ("audio/magic.ogg", false), 
	ATTACK ("audio/attack.ogg", false), 
	SUMMON_DROP ("audio/summondrop.ogg", false), 
	SUMMONED ("audio/summon1.ogg", false);
	
	String file;
	boolean looping;
	
	private Sound(String name, boolean looping) {
		this.file = name;
		this.looping = looping;
	}
	
	public String getFile() {
		return this.file;
	}
	
	public boolean getLooping() {
		return this.looping;
	}
	
}
