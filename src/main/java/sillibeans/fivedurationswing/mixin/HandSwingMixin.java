package sillibeans.fivedurationswing.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class HandSwingMixin {
	@Unique
	private final static int IDLE_DURATION = 5;

	@Unique
	private final static int INTERACT_DURATION = 4;

	@Unique
	private final static int MINE_DURATION = 3;

	@Unique
	private int progress = 0;

	@Unique
	private int computeDuration(boolean isLookingAtObj, boolean isMining, boolean isInteracting) {
		if (isLookingAtObj) {
			if (isMining) {
				return MINE_DURATION;
			}

			if (isInteracting) {
				return INTERACT_DURATION;
			}
		}

		return IDLE_DURATION;
	}

	/**
	 * @author sillibeanss
	 * @reason the duration is hardcoded so the only way to change it is to overwrite the method.
	 */

	// This is much harder to implement because the animation is fully dependent on swingItem(),
	// while having no extra info on whether the user is holding down the mouse buttons or not.
	// This adds 3 different durations for idle (5), interacting (4) and mining (3).
	// The name "Five Duration Swing" refers to the modern minecraft swing animation
	// implementation, where a swing speed of 5 makes the animation run at 3 ticks
	// while mining (for some reason).
	@Overwrite
	protected void updateAI() {
		Player p = (Player) (Object) this;
		Minecraft client = Minecraft.getMinecraft();
		final boolean isLookingAtObj = client.objectMouseOver != null;
		final boolean isMining = GameSettings.KEY_ATTACK.isPressed();
		final boolean isInteracting = GameSettings.KEY_INTERACT.isPressed();

		final int duration = computeDuration(isLookingAtObj, isMining, isInteracting);

		if (p.isSwinging) {
			progress += 1;

			if (progress >= duration) {
				progress = 0;

				if (!(isLookingAtObj && (isMining || isInteracting))) {
					p.isSwinging = false;
				}
			}
		} else {
			progress = 0;
		}

		p.swingProgressInt = progress;
		p.swingProgress = (float)p.swingProgressInt / (float) IDLE_DURATION;
	}
}
