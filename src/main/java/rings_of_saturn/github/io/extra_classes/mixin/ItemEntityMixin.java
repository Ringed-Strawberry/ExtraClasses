package rings_of_saturn.github.io.extra_classes.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void extraClasses$restrictPickup(PlayerEntity player, CallbackInfo ci) {
        if (player.getEntityWorld().isClient()) {
            return;
        }

        ItemEntity self = (ItemEntity) (Object) this;
        if (!ClassesUtil.canUseItem(player, self.getStack())) {
            ci.cancel();
        }
    }
}
