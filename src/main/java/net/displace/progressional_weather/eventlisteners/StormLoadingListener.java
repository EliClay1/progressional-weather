package net.displace.progressional_weather.eventlisteners;

import net.displace.progressional_weather.stormdata.StormRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import org.jspecify.annotations.NonNull;

public class StormLoadingListener {
    @SubscribeEvent
    public static void onResourcesReload(AddServerReloadListenersEvent event) {

        Identifier test = Identifier.parse("storm_listener");



        event.addListener(Identifier.parse("storm_listener"), new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(@NonNull ResourceManager pResourceManager, @NonNull ProfilerFiller pProfiler) {
                return null;
            }

            @Override
            protected void apply(Void unused, @NonNull ResourceManager pResourceManager, @NonNull ProfilerFiller pProfiler) {
                StormRegistry.loadStorms(pResourceManager);
            }
        });
    }
}
