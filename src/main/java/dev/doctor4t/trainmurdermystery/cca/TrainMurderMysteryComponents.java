package dev.doctor4t.trainmurdermystery.cca;

import dev.doctor4t.trainmurdermystery.TrainMurderMystery;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class TrainMurderMysteryComponents implements WorldComponentInitializer, EntityComponentInitializer {
    public static final ComponentKey<WorldTrainComponent> TRAIN = ComponentRegistry.getOrCreate(TrainMurderMystery.id("train"), WorldTrainComponent.class);
    public static final ComponentKey<PlayerRoleComponent> ROLE = ComponentRegistry.getOrCreate(TrainMurderMystery.id("role"), PlayerRoleComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(TRAIN, WorldTrainComponent::new);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ROLE, PlayerRoleComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}