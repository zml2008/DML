package wolfshotz.dml.entities;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class WaterDragonEntity extends TameableDragonEntity
{
    private static final ImmutableList<RegistryKey<Biome>> BIOMES = ImmutableList.of(Biomes.OCEAN,
            Biomes.COLD_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.DEEP_OCEAN,
            Biomes.DEEP_WARM_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.WARM_OCEAN,
            Biomes.FROZEN_OCEAN);

    public WaterDragonEntity(EntityType<? extends TameableDragonEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    public boolean shouldFly()
    { // we can fly in water!
        return canFly() && getAltitude() > ALTITUDE_FLYING_THRESHOLD;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() { return CreatureAttribute.WATER; }

    @Override
    public boolean canBreatheUnderwater() { return true; }

    @Override
    public boolean isPushedByWater() { return false; }

    @Override
    public boolean canBeRiddenInWater(Entity rider) { return true; }

    public static int getHabitatPoints(DragonEggEntity egg)
    {
        int points = 0;
        BlockPos basePos = egg.getPosition();

        for (BlockPos pos : BlockPos.getAllInBoxMutable(basePos.add(1, 1, 1), basePos.add(-1, -1, -1)))
            if (egg.world.getFluidState(pos).isTagged(FluidTags.WATER)) ++points;
        if (egg.isInWater()) ++points;
        if (BIOMES.contains(egg.world.getBiome(egg.getPosition()))) points += 2;
        return points;
    }
}
