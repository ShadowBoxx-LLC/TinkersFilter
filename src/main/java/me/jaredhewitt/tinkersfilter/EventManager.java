package me.jaredhewitt.tinkersfilter;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import me.jaredhewitt.tinkersfilter.Config;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.*;


public final class EventManager {
  private static Logger logger = LogManager.getLogger();
  
  @SubscribeEvent
  public void onToolStationCraftingRegisterEvent (ToolStationCraftingRegisterEvent event) {
    String registeredTool = event.getRecipe().getRegistryName().toString();
    boolean blacklist = false;
    if (Config.debugPrintToolStation) {
      logger.info(TinkersFilter.MODID + ": A tool with registry name " + registeredTool + " has been registered to the Tool Station");
    }
    for (String entry : Config.toolStationBlacklist) {
      if (entry == registeredTool) {
        blacklist = true;
      }
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onToolForgeCraftingRegisterEvent (ToolForgeCraftingRegisterEvent event) {
    String registeredTool = event.getRecipe().getRegistryName().toString();
    if (Config.debugPrintToolForge) {
      logger.info(TinkersFilter.MODID + ": A tool with registry name " + event.getRecipe().getRegistryName().toString() + " has been registered to the Tool Forge");
    }
    boolean blacklist = false;
    for (String entry : Config.toolForgeBlacklist) {
      if (entry == registeredTool) {
        blacklist = true;
      }
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onStencilTableCraftingRegisterEvent (StencilTableCraftingRegisterEvent event) {
    String registeredPattern = event.getRecipe().getItem().getRegistryName().toString();
    if (Config.debugPrintStencilTable) {
      logger.info(TinkersFilter.MODID + ": A pattern with registry name " + registeredPattern + " has been registered to the Tool Station");
    }
    boolean blacklist = false;
    for (String entry : Config.toolForgeBlacklist) {
      if (entry == registeredPattern) {
        blacklist = true;
      }
    }
    event.setCanceled(blacklist);
  }
}
