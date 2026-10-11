package rings_of_saturn.github.io.extra_classes;

import net.fabricmc.api.ModInitializer;
import rings_of_saturn.github.io.extra_classes.command.ModCommands;
import rings_of_saturn.github.io.extra_classes.item.ItemProtector;
import rings_of_saturn.github.io.extra_classes.item.component.ModItemComponents;
import rings_of_saturn.github.io.extra_classes.network.ModPayloads;
import rings_of_saturn.github.io.extra_classes.spell.ModSpells;

import static rings_of_saturn.github.io.extra_classes.block.ModBlocks.registerModBlocks;
import static rings_of_saturn.github.io.extra_classes.item.ModItems.registerModItems;

public class ExtraClasses implements ModInitializer {

    public static final String MOD_ID = "extra_classes";

    @Override
    public void onInitialize() {
        registerModBlocks();
        ModItemComponents.registerModComponents();
        registerModItems();
        ModCommands.register();
        ModSpells.registerModSpells();
        ModPayloads.register();
        ItemProtector.registerProtection();
    }
}
