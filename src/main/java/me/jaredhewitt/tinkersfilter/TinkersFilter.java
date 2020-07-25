package me.jaredhewitt.tinkersfilter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TinkersFilter.MODID, name = TinkersFilter.NAME, version = TinkersFilter.VERSION, dependencies = TinkersFilter.TCONDEP)
public class TinkersFilter
{
  public static final String MODID = "tinkersfilter";
  public static final String NAME = "Tinkers' Filter";
  public static final String VERSION = "${version}";
  public static final String TCONDEP = "required-before:tconstruct@[1.12.2-2.13.0.185,)";

  private static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    Config.load(event);
    logger = event.getModLog();
    MinecraftForge.EVENT_BUS.register(new EventManager());
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
    logger.info("Starting " + NAME);
  }
}
