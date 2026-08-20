package rings_of_saturn.github.io.extra_classes;

import net.fabricmc.api.ModInitializer;

import static rings_of_saturn.github.io.extra_classes.block.ModBlocks.registerModBlocks;
import static rings_of_saturn.github.io.extra_classes.item.ModItems.registerModItems;

public class ExtraClasses implements ModInitializer {

    @Override
    public void onInitialize() {
        registerModBlocks();
        registerModItems();
    }
}
