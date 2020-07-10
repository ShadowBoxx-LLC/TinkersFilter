package me.jaredhewitt.tinkersfilter;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TinkersFilter.MODID, name = TinkersFilter.NAME, version = TinkersFilter.VERSION)
public class TinkersFilter
{
    public static final String MODID = "tinkersfilter";
    public static final String NAME = "Tinkers' Filter";
    public static final String VERSION = "${version}";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("Hello World");
    }
}