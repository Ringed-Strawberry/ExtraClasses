package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import rings_of_saturn.github.io.extra_classes.components.types.SpendableIntComponent;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class ManaComponent implements SpendableIntComponent, ServerTickingComponent, AutoSyncedComponent {

    private float value = 0.0f;
    private float maxMana = 0.0f;
    private final Object provider;

    public ManaComponent(Object provider) {
        this.provider = provider;
    }

    @Override
    public void serverTick() {
        if (!(this.provider instanceof PlayerEntity player)){
            return;
        }
        if (this.maxMana <= 0.0f || !ClassesUtil.isEndWizard(player)){
            return;
        }
        if (this.value >= this.maxMana){
            return;
        }
        int before = Math.round(this.value);
        this.value = Math.min(this.value + REGEN_PER_TICK, this.maxMana);
        if (Math.round(this.value) != before) {
            ModEntityComponents.MANA.sync(this.provider);
        }
    }

    @Override
    public int getValue() {
        return Math.round(this.value);
    }

    @Override
    public int getMaxValue() {
        return Math.round(this.maxMana);
    }

    @Override
    public void setValue(int amount) {
        this.value = Math.clamp(amount, 0.0f, this.maxMana);
        ModEntityComponents.MANA.sync(this.provider);
    }

    @Override
    public boolean trySpend(int amount) {
        if (amount < 0 || this.value < amount) {
            return false;
        }
        this.value -= amount;
        ModEntityComponents.MANA.sync(this.provider);
        return true;
    }

    @Override
    public void replenish(int amount) {
        setValue(getValue() + amount);
    }

    @Override
    public void applyClassDefaults(Identifier classId) {
        if (classId.equals(END_WIZARD_ID)) {
            this.maxMana = WIZARD_MAX_MANA;
        } else {
            this.maxMana = 0.0f;
            this.value = 0.0f;
        }
        ModEntityComponents.MANA.sync(this.provider);
    }

    @Override
    public void readData(ReadView readView) {
        this.value = readView.getFloat("mana", 0.0f);
        this.maxMana = readView.getFloat("maxMana", 0.0f);
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putFloat("mana", this.value);
        writeView.putFloat("maxMana", this.maxMana);
    }

    @Override
    public boolean canSpend(int amount) {
        return amount >= 0 && this.value >= amount;
    }
}
