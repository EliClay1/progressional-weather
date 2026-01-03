package net.displace.progressional_weather.testing;

import io.netty.buffer.ByteBuf;
import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MyData(Identifier name, int age) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MyData> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "test_sync")
    );

    public static final StreamCodec<ByteBuf, MyData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Identifier.CODEC),
            MyData::name,
            ByteBufCodecs.VAR_INT,
            MyData::age,
            MyData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
