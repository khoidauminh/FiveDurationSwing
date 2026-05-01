package dev.sillibeans.fivedurationswing.mixin;

import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SwingAnimation.class)
public class HandSwingMixin {
	@Shadow
	@SuppressWarnings("all")
	public static final SwingAnimation DEFAULT = new SwingAnimation(SwingAnimationType.WHACK, 5);
}