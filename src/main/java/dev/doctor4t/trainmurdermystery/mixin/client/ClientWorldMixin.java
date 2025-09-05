package dev.doctor4t.trainmurdermystery.mixin.client;

import dev.doctor4t.trainmurdermystery.index.TrainMurderMysteryParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Shadow public abstract void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ClientPlayerEntity player = client.player;
        Random random = player.getRandom();
        for (int i = 0; i < 200; i++) {
            Vec3d pos = new Vec3d(player.getX() -20f + random.nextFloat(), player.getY() + (random.nextFloat() * 2 - 1) * 10f, player.getZ() + (random.nextFloat() * 2 - 1) *10f);
            if (this.client.world.isSkyVisible(BlockPos.ofFloored(pos))) {
                this.addParticle(TrainMurderMysteryParticles.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 2, 0, 0);
            }
        }
    }
}
