package net.ArcaneArtificer.arcanevillagers;

import com.mojang.logging.LogUtils;
import net.ArcaneArtificer.arcanevillagers.villager.ModVillagers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArcaneVillagers.MOD_ID)
public class ArcaneVillagers
{
    // Directly reference a slf4j logger
    public static final String MOD_ID = "arcanevillagers";
    private static final Logger LOGGER = LogUtils.getLogger();


    public ArcaneVillagers()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModVillagers.register(eventBus);

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModVillagers::registerPOIs);
    }

}
