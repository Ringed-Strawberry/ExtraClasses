package rings_of_saturn.github.io.extra_classes.entity.components;

import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import rings_of_saturn.github.io.extra_classes.entity.components.types.ClassComponent;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public class ModEntityComponents implements EntityComponentInitializer {
        public static final ComponentKey<ClassComponent> CLASS =
                ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "class"), ClassComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(CLASS, ClassComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
