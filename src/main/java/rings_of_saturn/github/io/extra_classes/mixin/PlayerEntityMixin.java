package rings_of_saturn.github.io.extra_classes.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rings_of_saturn.github.io.extra_classes.util.CooldownsUtil;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Unique
    PlayerEntity entity = (PlayerEntity) (Object) this;

    @Inject(method = "tick", at=@At("TAIL"))
    private void tickCooldowns(CallbackInfo ci){
        if(!entity.getEntityWorld().isClient()){
            CooldownsUtil.decrementCooldowns(entity);
        }
    }

    @Inject(method = "tick", at=@At("TAIL"))
    private void changeAttributes(CallbackInfo ci){
//        if(entity instanceof PlayerEntity player && entity.getEntityWorld() instanceof ServerWorld world) {
//            float percentage = AdvancementUtil.getNonZeroAdvancementProgressPercentage(player);
//            player.sendMessage(Text.of(String.valueOf(percentage)), true);
//            if(percentage != -1) {
//                EntityAttributeModifier speedModifier = new EntityAttributeModifier(EntityAttributes.MOVEMENT_SPEED.getKey().get().getValue(),
//                        2 / percentage,
//                        EntityAttributeModifier.Operation.ADD_VALUE);
//                entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).overwritePersistentModifier(speedModifier);
//            }
//        }
    }
}
