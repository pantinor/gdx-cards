package org.antinori.cards;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class ActionMoveCircular extends TemporalAction {
	private float startX, startY;
	private float r1;
	private float r2;

	public static ActionMoveCircular actionCircle(float x, float y, float r, float duration) {
		return ActionMoveCircular.actionEllipse(x, y, r, r, duration, Interpolation.linear);
	}

	public static ActionMoveCircular actionCircle(float x, float y, float r, float duration, Interpolation interpolation) {
		return ActionMoveCircular.actionEllipse(x, y, r, r, duration, Interpolation.linear);
	}

	public static ActionMoveCircular actionEllipse(float x, float y, float r1, float r2, float duration) {
		return actionEllipse(x, y, r1, r2, duration, Interpolation.linear);
	}

	public static ActionMoveCircular actionEllipse(float x, float y, float r1, float r2, float duration, Interpolation interpolation) {
		ActionMoveCircular action = new ActionMoveCircular();
		action.setR(r1, r2);
		action.setDuration(duration);
		action.setPosition(x, y);
		action.setInterpolation(interpolation);
		return action;
	}

	protected void setPosition(float x, float y) {
		startX = x;
		startY = y;
	}

	protected void begin() {

	}

	protected void update(float percent) {
		float angle = (float) (Math.PI * 2 * (percent / 1f));
		actor.setPosition(startX + r1 * (float) Math.cos(angle), startY + r2 * (float) Math.sin(angle));
	}

	public void setR(float r1, float r2) {
		this.r1 = r1;
		this.r2 = r2;
	}
}
