package dev.sillibeans.fivedurationswing.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class HandSwingMixin {
	@Inject(method = "getCurrentSwingDuration", at = @At("RETURN"), cancellable = true)
	private void changeSwing(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValue()*5/6);
	}
}