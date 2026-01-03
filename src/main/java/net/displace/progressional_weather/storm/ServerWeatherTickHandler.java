package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.displace.progressional_weather.storm.managers.ActiveStormManager;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ProgressionalWeather.MODID)
public class ServerWeatherTickHandler {
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ActiveStormManager.tickAllStorms();

        event.getServer().getAllLevels().forEach(level -> {
            ActiveStormManager.getActiveStorm(level).ifPresentOrElse(
                    activeStorm -> {
                        PacketDistributor.sendToPlayersInDimension(level, activeStorm);
                        // or we could just do activeStorm.setActiveBool(false);
                    }, () -> PacketDistributor.sendToPlayersInDimension(level, ActiveStorm.createInactive())
            );
        });
    }
}
