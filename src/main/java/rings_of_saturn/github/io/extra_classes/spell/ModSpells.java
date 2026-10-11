package rings_of_saturn.github.io.extra_classes.spell;

import net.minecraft.util.Identifier;

import java.util.*;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.DEBUG_SPELL_MANA_COST;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.DEBUG_SPELL_NAME;

public class ModSpells {

    private static final Map<Identifier, Spell> SPELLS = new LinkedHashMap<>();

    public static void registerModSpells() {
        register(new DebugSpell(Identifier.of(MOD_ID, DEBUG_SPELL_NAME),
                List.of(Click.RIGHT,Click.LEFT), DEBUG_SPELL_MANA_COST));
    }

    public static void register(Spell spell) {
        SPELLS.put(spell.getId(), spell);
    }

    public static Optional<Spell> get(Identifier id) {
        return Optional.ofNullable(SPELLS.get(id));
    }

    public static List<Spell> getByIds(Collection<Identifier> ids) {
        List<Spell> spells = new ArrayList<>();
        for (Identifier id : ids) {
            Spell spell = SPELLS.get(id);
            if (spell != null) {
                spells.add(spell);
            }
        }
        return spells;
    }

    public static boolean anyStartsWith(List<Spell> spells, List<Click> buffer) {
        for (Spell spell : spells) {
            List<Click> inputs = spell.getInputs();
            if (inputs.size() >= buffer.size()
                    && inputs.subList(0, buffer.size()).equals(buffer)) {
                return true;
            }
        }
        return false;
    }

    public static Optional<Spell> findComplete(List<Spell> spells, List<Click> buffer) {
        for (Spell spell : spells) {
            if (spell.getInputs().equals(buffer)) {
                return Optional.of(spell);
            }
        }
        return Optional.empty();
    }
}
