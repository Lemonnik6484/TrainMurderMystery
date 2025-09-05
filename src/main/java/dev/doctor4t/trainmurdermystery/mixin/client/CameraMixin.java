package dev.doctor4t.trainmurdermystery.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Unique
    private static final PerlinNoiseSampler sampler = new PerlinNoiseSampler(Random.create());
    @Unique
    private float yawOffset;
    @Unique
    private float pitchOffset;

    @Unique
    private static float randomizeOffset(int offset) {
        float intensity = 0.1f;

        float min = -intensity * 2;
        float max = intensity * 2;
        float sampled = (float) sampler.sample((MinecraftClient.getInstance().world.getTime() % 24000L + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false)) / intensity, offset, 0) * 1.5f;
        return min >= max ? min : sampled * max;
    }

    @Inject(method = "update", at = @At("RETURN"))
    private void trainmurdermystery$doScreenshake(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Camera camera = (Camera) (Object) this;

        yawOffset = randomizeOffset(10);
        pitchOffset = randomizeOffset(-10);
//        camera.setRotation(camera.getYaw() + yawOffset, camera.getPitch() + pitchOffset);

        int age = MinecraftClient.getInstance().player.age;
        float amplitude = .0025f;
        float strength = 0.5f;
        camera.setPos(camera.getPos().add(0, Math.sin((age + tickDelta) * strength) / 2f * amplitude, Math.cos((age + tickDelta) * strength) * amplitude));
    }
}
