package org.antinori.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LogScrollPane extends ScrollPane {
	
	private static Table log = new Table();
	private static Label.LabelStyle labelStyle;

	public LogScrollPane(Skin skin) {
		super(log, skin, "transparent-background");
		
		labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
		labelStyle.fontColor = Color.BLACK;
		
		clear();
		this.setScrollingDisabled(true, false);

	}
	
//	// get the default style from the skin
//	ScrollPane.ScrollPaneStyle transparentStyle = new ScrollPane.ScrollPaneStyle(skin.get(ScrollPane.ScrollPaneStyle.class));
	
//	// set the background to be a new drawable, in this case a textureregion that is your transparent background.
//	transparentStyle.background = new TextureRegionDrawable(transparentTextureRegion);
	
//	// add the new style into the skin
//	skin.add("transparentScrollPane", transparentStyle);

	public void add(String text) {
		Label label = new Label(text, labelStyle);
		log.add(label);
		log.row();
		this.scrollTo(0, 0, 0, 0);
		this.invalidate();

	}

	public void clear() {
		log.clear();
		this.invalidate();
	}
}