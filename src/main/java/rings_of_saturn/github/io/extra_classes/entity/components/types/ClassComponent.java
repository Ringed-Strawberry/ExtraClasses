package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import rings_of_saturn.github.io.extra_classes.components.types.StringComponent;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;

public class ClassComponent implements StringComponent, AutoSyncedComponent {
    private String value = "minecraft:overworld";
    private final Object provider;
    public ClassComponent(Object provider) {
        this.provider = provider;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        ModEntityComponents.CLASS.sync(this.provider);
    }

    @Override
    public void readData(ReadView readView) {
        this.value = readView.getString("value", "minecraft:overworld");
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putString("value", this.value);
    }
}
