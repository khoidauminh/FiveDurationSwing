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
	private final static int DURATION = 5;

	/**
	 * @author sillibeanss
	 * @reason the duration is hardcoded so the only way to change it is to overwrite the method.
	 */

	// This is much harder to implement because the animation is fully dependent on swingItem(),
	// while having no extra info on whether the user is holding down the mouse buttons or not.
	// This adds 3 different durations for idle (5), interacting (4) and mining (3).
	// The name "Five Duration Swing" refers to the modern Minecraft swing animation
	// implementation, where a swing speed of 5 makes the animation run at 3 ticks
	// while mining (for some reason).
	@Overwrite
	protected void updateAI() {
		Player p = (Player) (Object) this;
		Minecraft client = Minecraft.getMinecraft();

		// If the player is holding the attack mouse button and is looking at something,
		// they most likely is mining, swingItem() should be called every tick to make
		// sure the hand swing is restarted right after it finishes.
		// Make sure the hand does not swing again if a screen has been opened.
		if (GameSettings.KEY_ATTACK.isPressed() && client.objectMouseOver != null && client.currentScreen == null) {
			swingItem();
		}

		if (p.isSwinging) {
			p.swingProgressInt += 1;

			if (p.swingProgressInt >= DURATION) {
				p.swingProgressInt = 0;
				p.isSwinging = false;
			}
		} else {
			p.swingProgressInt = 0;
		}

		p.swingProgress = (float)p.swingProgressInt / (float) DURATION;
	}

	/**
	 * @author sillibeans
	 * @reason based on modern Minecraft code.
	 */
	// DURATION / 2 is the key part.
	@Overwrite
	public void swingItem() {
		Player p = (Player) (Object) this;
		if (!p.isSwinging || p.swingProgressInt >= DURATION / 2 || p.swingProgressInt == -1) {
			p.isSwinging = true;
			p.swingProgressInt = -1;
		}
	}
}
