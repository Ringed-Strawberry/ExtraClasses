package rings_of_saturn.github.io.extra_classes.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.spell.Spell;

import java.util.LinkedHashMap;

public class CooldownsUtil {
    public static boolean isCooldownFinished(Spell spell, PlayerEntity player){
        if(spell.getCooldown() != 0)
            return isCooldownFinished(spell.getId(), player);
        else
            return true;
    }

    public static boolean isCooldownFinished(Identifier spell, PlayerEntity player){
        getPlayerCooldowns(player).putIfAbsent(spell, 0);
        return getPlayerCooldowns(player).get(spell) == 0;
    }

    public static void setCooldown(Identifier spell, PlayerEntity player, int amount){
        getPlayerCooldowns(player).put(spell, amount);
    }

    private static int getPlayerCooldown(Spell spell, PlayerEntity player) {
        return getPlayerCooldown(spell.getId(), player);
    }

    private static int getPlayerCooldown(Identifier spell, PlayerEntity player) {
        return ModEntityComponents.COOLDOWNS.get(player).getValue().get(spell);
    }

    public static void decrementCooldown(Spell spell, PlayerEntity player){
        decrementCooldown(spell,player,1);
    }

    public static void decrementCooldown(Spell spell, PlayerEntity player, int amount){
        decrementCooldown(spell.getId(), player, amount);
    }

    public static void decrementCooldown(Identifier spell, PlayerEntity player, int amount){
        setCooldown(spell,player, getPlayerCooldown(spell,player)-amount);
    }

    public static void decrementCooldowns(PlayerEntity player, int amount){
        for(Identifier id : getPlayerCooldowns(player).keySet()){
            decrementCooldown(id,player,amount);
        }
    }

    public static void decrementCooldowns(PlayerEntity player){
        for(Identifier id : getPlayerCooldowns(player).keySet()){
            if(!isCooldownFinished(id, player)) {
                player.sendMessage(Text.of(String.valueOf(getPlayerCooldown(id, player))), false);
                decrementCooldown(id, player, 1);
            }
        }
    }

    public static void resetCooldown(Spell spell, PlayerEntity player){
        getPlayerCooldowns(player).put(spell.getId(), spell.getCooldown());
    }

    private static LinkedHashMap<Identifier, Integer> getPlayerCooldowns(PlayerEntity player) {
        if(ModEntityComponents.COOLDOWNS.get(player).getValue() == null)
            ModEntityComponents.COOLDOWNS.get(player).setValue(LinkedHashMap.newLinkedHashMap(0));
        return ModEntityComponents.COOLDOWNS.get(player).getValue();
    }


}
