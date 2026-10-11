package rings_of_saturn.github.io.extra_classes.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.util.Identifier;
import ringed_strawberry.github.io.spacelib.item.components.DataComponentGen;

import java.util.List;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public class ModItemComponents {
    public static final ComponentType<List<Identifier>> WAND_SPELLS = DataComponentGen.registerComponent(
            Identifier.of(MOD_ID, "wand_spells"),
            ComponentType.<List<Identifier>>builder()
                    .codec(Codec.list(Identifier.CODEC))
                    .build());

    public static final ComponentType<Identifier> REQUIRED_CLASS = DataComponentGen.registerComponent(
            Identifier.of(MOD_ID, "required_class"),
            ComponentType.<Identifier>builder()
                    .codec(Identifier.CODEC)
                    .build());
    public static void registerModComponents(){

    }

}
