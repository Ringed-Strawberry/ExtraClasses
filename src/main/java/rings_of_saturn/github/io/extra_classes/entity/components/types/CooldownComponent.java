package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import rings_of_saturn.github.io.extra_classes.components.types.IntIdentifierMapComponent;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;

import java.util.LinkedHashMap;

import static rings_of_saturn.github.io.extra_classes.util.ModConstants.DEFAULT_COOLDOWNS_VALUE;

public class CooldownComponent implements IntIdentifierMapComponent, AutoSyncedComponent {
    private LinkedHashMap<Identifier, Integer> value = DEFAULT_COOLDOWNS_VALUE;
    private final Object provider;

    public CooldownComponent(Object provider) {
        this.provider = provider;
    }

    @Override
    public LinkedHashMap<Identifier, Integer> getValue() {
        return this.value;
    }

    @Override
    public void setValue(LinkedHashMap<Identifier, Integer> value) {
        this.value = value;
        ModEntityComponents.COOLDOWNS.sync(this.provider);
    }

    @Override
    public void readData(ReadView readView) {
        int length = readView.getInt("list_length", 0);
        for (int i = 0; i < length; i++) {
            this.value.put(Identifier.of(readView.getString("key" + i, "")), readView.getInt("value" + i, 0));
        }
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putInt("list_length", this.value.size());
        for (int i = 0; i < this.value.size(); i++) {
            Identifier id = (Identifier) this.value.keySet().toArray()[i];
            writeView.putInt("value" + i, this.value.get(id));
            writeView.putString("key" + i, id.toString());
        }
    }
}
