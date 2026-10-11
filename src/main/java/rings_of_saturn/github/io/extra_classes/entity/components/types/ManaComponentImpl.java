package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class ManaComponentImpl implements ManaComponent, ServerTickingComponent, AutoSyncedComponent {

    private float mana = 0.0f;
    private float maxMana = 0.0f;
    private final Object provider;

    public ManaComponentImpl(Object provider) {
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
        if (this.mana >= this.maxMana){
            return;
        }
        int before = Math.round(this.mana);
        this.mana = Math.min(this.mana + REGEN_PER_TICK, this.maxMana);
        if (Math.round(this.mana) != before) {
            ModEntityComponents.MANA.sync(this.provider);
        }
    }

    @Override
    public int getMana() {
        return Math.round(this.mana);
    }

    @Override
    public int getMaxMana() {
        return Math.round(this.maxMana);
    }

    @Override
    public void setMana(int amount) {
        this.mana = Math.clamp(amount, 0.0f, this.maxMana);
        ModEntityComponents.MANA.sync(this.provider);
    }

    @Override
    public boolean trySpend(int amount) {
        if (amount < 0 || this.mana < amount) {
            return false;
        }
        this.mana -= amount;
        ModEntityComponents.MANA.sync(this.provider);
        return true;
    }

    @Override
    public void replenish(int amount) {
        setMana(getMana() + amount);
    }

    @Override
    public void applyClassDefaults(Identifier classId) {
        if (classId.equals(END_WIZARD_ID)) {
            this.maxMana = WIZARD_MAX_MANA;
        } else {
            this.maxMana = 0.0f;
            this.mana = 0.0f;
        }
        ModEntityComponents.MANA.sync(this.provider);
    }

    @Override
    public void readData(ReadView readView) {
        this.mana = readView.getFloat("mana", 0.0f);
        this.maxMana = readView.getFloat("maxMana", 0.0f);
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putFloat("mana", this.mana);
        writeView.putFloat("maxMana", this.maxMana);
    }

    @Override
    public boolean canSpend(int amount) {
        return amount >= 0 && this.mana >= amount;
    }
}
