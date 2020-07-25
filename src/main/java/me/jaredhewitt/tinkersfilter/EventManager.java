package me.jaredhewitt.tinkersfilter;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import slimeknights.tconstruct.library.events.MaterialEvent.*;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent.*;


public final class EventManager {
  private static Logger logger = LogManager.getLogger();
  private static boolean debugPrint = Config.generalDebugPrint;

  @SubscribeEvent
  public void onMaterialRegisterEvent (MaterialRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onMaterialRegister");
    if (event.material == null) return;
    String registeredMaterial = event.material.getIdentifier();
    if (registeredMaterial.contains("_internal_render")) return;
    boolean blacklist = false;
 
    if (Arrays.asList(Config.materialBlacklist).contains(registeredMaterial)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Material " + registeredMaterial);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintMaterial) {
      logger.info(TinkersFilter.MODID + ": A material with identifier " + registeredMaterial + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onIntegrationEvent (IntegrationEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onIntegrationEvent");
    if (event.material == null || event.materialIntegration == null) return;
    String registeredMaterial = event.material.getIdentifier();
    String registeredIntegration = event.materialIntegration.material.getIdentifier();
    boolean blacklist = false;

    if (Arrays.asList(Config.materialBlacklist).contains(registeredMaterial) || Arrays.asList(Config.materialBlacklist).contains(registeredIntegration)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Material Integration " + registeredMaterial + ":" + registeredIntegration);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintMaterial) {
      logger.info(TinkersFilter.MODID + ": An integration with identifiers " + registeredMaterial + ":" + registeredIntegration + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onTraitRegisterEvent (TraitRegisterEvent<?> event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onTraitRegisterEvent");
    if (event.material == null || event.trait == null) return;
    String registeredMaterial = event.material.getIdentifier();
    String registeredTrait = event.trait.getIdentifier();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.traitBlacklist).contains(registeredMaterial + ":" + registeredTrait)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Trait " + registeredMaterial + ":" + registeredTrait);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintTrait) {
      logger.info(TinkersFilter.MODID + ": A trait  with identifier " + registeredTrait + " has been registered to material " + registeredMaterial);
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onModifierRegisterEvent (ModifierRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onModifierRegisterEvent");
    if (event.getRecipe() == null) return;
    String registeredModifier = event.getRecipe().getIdentifier();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.modifierBlacklist).contains(registeredModifier)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Modifier " + registeredModifier);
      }
      blacklist = true;
    }

    if (!blacklist && Config.debugPrintModifier) {
      logger.info(TinkersFilter.MODID + ": A modifier with identifier " + registeredModifier + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onMeltingRegisterEvent (MeltingRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onMeltingRegisterEvent");
    if (event.getRecipe() == null) return;
    String registeredItem = "";
    for (ItemStack itemStack : event.getRecipe().input.getInputs()) {
      if (itemStack != null) {
        registeredItem = itemStack.getItem().getRegistryName().toString().replaceAll("^.*:","");
      }
    }
    if (registeredItem.length() < 1) return;
    String registeredLiquid = event.getRecipe().getResult().getFluid().getName();
    boolean blacklist = false;

    if (Arrays.asList(Config.meltingBlacklist).contains(registeredItem + ":" + registeredLiquid)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Melting Material " + registeredItem + ":" + registeredLiquid);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintMelting) {
      logger.info(TinkersFilter.MODID + ": A melting material from item " + registeredItem + " with name " + registeredLiquid + " has been registered");
    }
    event.setCanceled(blacklist);
  }

  @SubscribeEvent
  public void onEntityMeltingRegisterEvent (EntityMeltingRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onEntityMeltingRegisterEvent");
    if (event.getRecipe() == null || event.getFluidStack() == null) return;
    // There is probably a better way to do this...
    String registeredEntity = event.getRecipe().getName().replaceAll("^.*\\.","").replace("Entity","").replace(".class","");
    String resultFluid = event.getFluidStack().getFluid().getName();
    boolean blacklist = false;

    if (Arrays.asList(Config.entityMeltingBlacklist).contains(registeredEntity + ":" + resultFluid)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Melting Entity " + registeredEntity + ":" + resultFluid);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintEntityMelting) {
      logger.info(TinkersFilter.MODID + ": A melting entity with name " + registeredEntity + " that melts into " + resultFluid + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onAlloyRegisterEvent (AlloyRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onAlloyRegisterEvent");
    if (event.getRecipe() == null) return;
    String registeredAlloy = event.getRecipe().getResult().getFluid().getName();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.alloyBlacklist).contains(registeredAlloy)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Alloy " + registeredAlloy);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintAlloy) {
      logger.info(TinkersFilter.MODID + ": An alloy with name " + registeredAlloy + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onSmelteryFuelRegisterEvent (SmelteryFuelRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onSmelteryFuelRegisterEvent");
    if (event.getRecipe() == null) return;
    String registeredFuel = event.getRecipe().getFluid().getName();
    boolean blacklist = false;
    
    if (Arrays.asList(Config.smelteryFuelBlacklist).contains(registeredFuel)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Fuel " + registeredFuel);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintSmelteryFuel) {
      logger.info(TinkersFilter.MODID + ": A smeltery fuel with name " + registeredFuel + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onDryingRackRegisterEvent (DryingRackRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onDryingRackRegisterEvent");
    if (event.getRecipe() == null) return;
    String registeredOutputItem = event.getRecipe().getResult().getItem().getRegistryName().toString().replaceAll("^.*:","");
    String registeredInputItem = "";
    for (ItemStack itemStack : event.getRecipe().input.getInputs()) {
      if (itemStack != null) {
        registeredInputItem = itemStack.getItem().getRegistryName().toString().replaceAll("^.*:","");
      }
    }
    boolean blacklist = false;
    
    if (Arrays.asList(Config.dryingRackBlacklist).contains(registeredInputItem + ":" + registeredOutputItem)) {
      if (debugPrint) {
        logger.info(TinkersFilter.MODID + ": Blacklisting Drying Rack recipe " + registeredInputItem + " producing item " + registeredOutputItem);
      }
      blacklist = true;
    }
    
    if (!blacklist && Config.debugPrintDryingRack) {
      logger.info(TinkersFilter.MODID + ": A drying rack recipe for " + registeredInputItem + " producing an item with registry name " + registeredOutputItem + " has been registered");
    }
    event.setCanceled(blacklist);
  }
  
  @SubscribeEvent
  public void onToolStationCraftingRegisterEvent (ToolStationCraftingRegisterEvent event) {
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onToolStationCraftingRegisterEvent");
    if (event.getRecipe() == null) return;
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
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onToolForgeCraftingRegisterEvent");
    if (event.getRecipe() == null) return;
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
    if (debugPrint) logger.info(TinkersFilter.MODID + ": Entered onStencilTableCraftingRegisterEvent");
    if (event.getRecipe() == null) return;
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
