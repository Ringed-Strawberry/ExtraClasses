package rings_of_saturn.github.io.extra_classes.client;

import net.fabricmc.api.ClientModInitializer;
import rings_of_saturn.github.io.extra_classes.client.spell.WandInputsTracker;

public class ExtraClassesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WandInputsTracker.registerInputs();
    }
}
