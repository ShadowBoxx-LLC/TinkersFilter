package me.jaredhewitt.tinkersfilter;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.json.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.*;


public final class EventManager {
  private static Logger logger = LogManager.getLogger();
  private boolean debugPrint = false;
  
  @SubscribeEvent
  public void onToolStationCraftingRegisterEvent (ToolStationCraftingRegisterEvent event) {
    String registeredTool = event.getRecipe().getRegistryName().toString();
    boolean blacklist = false;
    
    for (String entry : Config.toolStationBlacklist) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": The tool " + entry + " was added to the tool station blacklist");
      }
      if (entry.equals(registeredTool)) {
        blacklist = true;
      }
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
    
    for (String entry : Config.toolForgeBlacklist) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": The tool " + entry + " was added to the tool forge blacklist");
      }
      if (entry.equals(registeredTool)) {
        blacklist = true;
      }
    }
    
    if (!blacklist && Config.debugPrintToolForge) {
      logger.info(TinkersFilter.MODID + ": A tool with registry name " + event.getRecipe().getRegistryName().toString() + " has been registered to the Tool Forge");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onStencilTableCraftingRegisterEvent (StencilTableCraftingRegisterEvent event) {
    String NBTCompoundString = event.getRecipe().getTagCompound().toString();
    JSONObject NBTCompoundObj = new JSONObject(NBTCompoundString);
    String registeredPattern = NBTCompoundObj.getString("PartType");
    boolean blacklist = false;
    
    for (String entry : Config.stencilTableBlacklist) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": The tool part " + entry + " was added to the sketching table blacklist");
      }
      if (entry.equals(registeredPattern)) {
        blacklist = true;
      }
    }
    
    if (!blacklist && Config.debugPrintStencilTable) {
      logger.info(TinkersFilter.MODID + ": A pattern with registry name " + registeredPattern + " has been registered to the Stencil Table");
    }
    event.setCanceled(blacklist);
  }
}
