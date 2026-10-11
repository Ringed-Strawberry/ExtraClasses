package rings_of_saturn.github.io.extra_classes.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import ringed_strawberry.github.io.spacelib.item.ItemGen;
import rings_of_saturn.github.io.extra_classes.item.component.ModItemComponents;

import java.util.List;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class ModItems {

    public static final Item WAND = ItemGen.createItem(Identifier.of(MOD_ID, WAND_ITEM_NAME),
            WandItem::new, new Item.Settings().component(ModItemComponents.WAND_SPELLS,
                    List.of(Identifier.of(MOD_ID, DEBUG_SPELL_NAME)))
                    .component(ModItemComponents.REQUIRED_CLASS, END_WIZARD_ID)
    );

    public static void registerModItems(){

    }
}
