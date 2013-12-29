package org.antinori.cards;

public enum Sound {
		
	BACKGROUND1 ("audio/combat1.ogg", false, 0.1f), 
	BACKGROUND2 ("audio/combat2.ogg", false, 0.1f), 
	BACKGROUND3 ("audio/combat3.ogg", false, 0.1f), 

	MAGIC ("audio/magic.ogg", false, 0.3f), 
	ATTACK ("audio/attack.ogg", false, 0.3f), 
	SUMMON_DROP ("audio/summondrop.ogg", false, 0.3f), 
	SUMMONED ("audio/summon1.ogg", false, 0.3f);
	
	String file;
	boolean looping;
	float volume;
	
	private Sound(String name, boolean looping, float volume) {
		this.file = name;
		this.looping = looping;
		this.volume = volume;
	}
	
	public String getFile() {
		return this.file;
	}
	
	public boolean getLooping() {
		return this.looping;
	}
	
	public float getVolume() {
		return this.volume;
	}
	
}
