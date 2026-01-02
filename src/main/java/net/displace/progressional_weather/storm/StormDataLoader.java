package net.displace.progressional_weather.storm;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class StormDataLoader extends SimpleJsonResourceReloadListener<Storm> {
    public StormDataLoader() {
        super(Storm.CODEC, FileToIdConverter.registry(Storm.STORM_REGISTRY_KEY));
    }

    @Override
    protected void apply(Map<Identifier, Storm> identifierStormMap, @NonNull ResourceManager resourceManager, @NonNull ProfilerFiller profilerFiller) {
        Map<Identifier, Storm> storms = new HashMap<>();
    }
}
