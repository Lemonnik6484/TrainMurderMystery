package dev.doctor4t.trainmurdermystery.util;

import dev.doctor4t.trainmurdermystery.block.TrimmedBedBlock;
import dev.doctor4t.trainmurdermystery.cca.PlayerPoisonComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PoisonUtils {
    public static float getFovMultiplier(float tickDelta, PlayerPoisonComponent poisonComponent) {
        if (!poisonComponent.pulsing) return 1f;

        poisonComponent.pulseProgress += tickDelta * 0.1f;

        if (poisonComponent.pulseProgress >= 1f) {
            poisonComponent.pulsing = false;
            poisonComponent.pulseProgress = 0f;
            return 1f;
        }

        float maxAmplitude = 0.1f;
        float minAmplitude = 0.025f;

        float result = getResult(poisonComponent, minAmplitude, maxAmplitude);

        return result;
    }

    private static float getResult(PlayerPoisonComponent poisonComponent, float minAmplitude, float maxAmplitude) {
        float amplitude = minAmplitude + (maxAmplitude - minAmplitude) * (1f - ((float) poisonComponent.poisonTicks / 1200f));

        float result;

        if (poisonComponent.pulseProgress < 0.25f) {
            result = 1f - amplitude * (float) Math.sin(Math.PI * (poisonComponent.pulseProgress / 0.25f));
        } else if (poisonComponent.pulseProgress < 0.5f) {
            result = 1f - amplitude * (float) Math.sin(Math.PI * ((poisonComponent.pulseProgress - 0.25f) / 0.25f));
        } else {
            result = 1f;
        }
        return result;
    }

    public static void bedFunc(PlayerEntity player) {
        World world = player.getEntityWorld();
        BlockPos bedPos = player.getBlockPos();

        if (!(world.getBlockState(bedPos).getBlock() instanceof TrimmedBedBlock)) return;

        if (world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();

            client.inGameHud.setOverlayMessage(Text.translatable("game.player.poisoned"), false);
        } else {
            int poisonTicks = PlayerPoisonComponent.KEY.get(player).poisonTicks;

            if (poisonTicks == -1) PlayerPoisonComponent.KEY.get(player).setPoisonTicks(
                    Random.createThreadSafe().nextBetween(PlayerPoisonComponent.clampTime.getLeft(), PlayerPoisonComponent.clampTime.getRight()));
            else PlayerPoisonComponent.KEY.get(player).setPoisonTicks(MathHelper.clamp(
                    poisonTicks - Random.createThreadSafe().nextBetween(100, 300), 0, PlayerPoisonComponent.clampTime.getRight()));
        }
    }
}
