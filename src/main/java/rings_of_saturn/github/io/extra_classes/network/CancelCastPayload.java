package rings_of_saturn.github.io.extra_classes.network;


import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public record CancelCastPayload() implements CustomPayload {
    public static final CustomPayload.Id<CancelCastPayload> ID =
            CustomPayload.id(MOD_ID + "cancel_cast");

    public static final PacketCodec<RegistryByteBuf, CancelCastPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {},
                    buf -> new CancelCastPayload()
            );

    @Override
    public CustomPayload.Id<CancelCastPayload> getId() {
        return ID;
    }
}
