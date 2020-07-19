package me.jaredhewitt.tinkersfilter;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.*;


public final class EventManager {
  private static Logger logger = LogManager.getLogger();
  private static boolean debugPrint = Config.generalDebug;

  @SubscribeEvent
  public void onToolStationCraftingRegisterEvent (ToolStationCraftingRegisterEvent event) {
    String registeredTool = event.getRecipe().getRegistryName().toString();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.toolStationBlacklist).contains(registeredTool)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting tool " + registeredTool + " from the Tool Station");
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintToolStation) {
      logger.info(TinkersFilter.MODID + ": A tool with registry name " + registeredTool + " has been registered to the Tool Station");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onToolForgeCraftingRegisterEvent (ToolForgeCraftingRegisterEvent event) {
    String registeredTool = event.getRecipe().getRegistryName().toString();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.toolForgeBlacklist).contains(registeredTool)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting tool " + registeredTool + " from the Tool Forge");
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintToolForge) {
      logger.info(TinkersFilter.MODID + ": A tool with registry name " + event.getRecipe().getRegistryName().toString() + " has been registered to the Tool Forge");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onStencilTableCraftingRegisterEvent (StencilTableCraftingRegisterEvent event) {
    String registeredPattern = event.getRecipe().getTagCompound().getString("PartType");
    boolean blacklist = false;
    
    if (Arrays.asList(Config.stencilTableBlacklist).contains(registeredPattern)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting pattern " + registeredPattern + " from the Stencil Table");
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintStencilTable) {
      logger.info(TinkersFilter.MODID + ": A pattern with registry name " + registeredPattern + " has been registered to the Stencil Table");
    }
    event.setCanceled(blacklist);
  }
}
