package rings_of_saturn.github.io.extra_classes.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public record CastSpellPayload(Identifier spellId) implements CustomPayload {

    public static final CustomPayload.Id<CastSpellPayload> ID =
            CustomPayload.id(MOD_ID + "cast_spell");

    public static final PacketCodec<RegistryByteBuf, CastSpellPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> buf.writeIdentifier(payload.spellId()),
                    buf -> new CastSpellPayload(buf.readIdentifier()));

    @Override
    public CustomPayload.Id<CastSpellPayload> getId() {
        return ID;
    }
}