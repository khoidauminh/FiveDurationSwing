package sillibeans.fivedurationswing.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Player.class)
public class HandSwingMixin {
	/**
	 * @author sillibeanss
	 * @reason the duration is hardcoded so the only way to change it is to overwrite the method.
	 */
	@Overwrite
	protected void updateAI() {
		Player p = (Player) (Object) this;
		Minecraft client = Minecraft.getMinecraft();
		final boolean isContinuous = GameSettings.KEY_ATTACK.isPressed() && client.objectMouseOver != null;

		final int maxDuration = 6;
		final int duration = isContinuous ? 3 : maxDuration;

		if (p.isSwinging) {
			p.swingProgressInt += 1;

			if (p.swingProgressInt >= duration) {
				p.swingProgressInt = 0;

				if (!isContinuous) {
					p.isSwinging = false;
				}
			}
		} else {
			p.swingProgressInt = 0;
		}

		p.swingProgress = (float)p.swingProgressInt / (float) maxDuration;
	}

	/**
	 * @author sillibeanss
	 * @reason disallow swingProgressInt being forced to -1
	 */
	@Overwrite
	public void swingItem() {
		Player p = (Player) (Object) this;
		p.isSwinging = true;
	}
}
