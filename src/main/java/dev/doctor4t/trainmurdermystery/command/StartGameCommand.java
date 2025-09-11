package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.doctor4t.trainmurdermystery.cca.PlayerRoleComponent;
import dev.doctor4t.trainmurdermystery.cca.TrainMurderMysteryComponents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Locale;

public class StartGameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:startGame")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> startGame(context.getSource()))

        );
    }

    private static int startGame(ServerCommandSource source) {
        ServerWorld world = source.getWorld();

        TrainMurderMysteryComponents.TRAIN.get(world).setTrainSpeed(130);

//        List<ServerPlayerEntity> players = world.getPlayers().stream().filter(serverPlayerEntity -> !serverPlayerEntity.isInCreativeMode() && !serverPlayerEntity.isSpectator()).toList();
        List<ServerPlayerEntity> players = world.getPlayers().stream().toList();
        for (ServerPlayerEntity player : players) {
            PlayerRoleComponent.Role role = PlayerRoleComponent.Role.getRandomRole();
            TrainMurderMysteryComponents.ROLE.get(player).setRole(role);
            player.sendMessage(Text.translatable("tip.role." + role.name().toLowerCase(Locale.ROOT)));
        }


        return 1;
    }
}
