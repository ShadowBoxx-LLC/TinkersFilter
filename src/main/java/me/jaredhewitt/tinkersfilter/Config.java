package me.jaredhewitt.tinkersfilter;

import java.util.List;
import com.google.common.collect.Lists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Config {
  public static Config instance = new Config();
  
  private Config() {
  }
  
  public static boolean debugPrintToolStation = false;
  public static boolean debugPrintToolForge = false;
  public static boolean debugPrintStencilTable = false;
  public static String[] toolStationBlacklist = {};
  public static String[] toolForgeBlacklist = {};
  public static String[] stencilTableBlacklist = {};
  
  static Configuration configFile;
  
  static ConfigCategory Debug;
  static ConfigCategory Blacklists;
  
  public static void load(FMLPreInitializationEvent event) {
    configFile = new Configuration(event.getSuggestedConfigurationFile(), "0.1", false);
    
    MinecraftForge.EVENT_BUS.register(instance);
    
    syncConfig();
  }
  
  @SubscribeEvent
  public void update(ConfigChangedEvent.OnConfigChangedEvent event) {
    if(event.getModID().equals(TinkersFilter.MODID)) {
      syncConfig();
    }
  }
  
  public static boolean syncConfig() {
    Property prop;
    {
      String category = "debug";
      List<String> propertyOrder = Lists.newArrayList();
      Debug = configFile.getCategory(category);
      
      prop = configFile.get(category, "Print Tool Station Registry", debugPrintToolStation);
      prop.setComment("Setting this to true, will cause each tool registered to the Tool Station, to be printed in the minecraft log. Useful for getting tool names.");
      debugPrintToolStation = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Tool Forge Registry", debugPrintToolForge);
      prop.setComment("Setting this to true, will cause each tool registered to the Tool Forge, to be printed in the minecraft log. Useful for getting tool names.");
      debugPrintToolForge = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Stencil Table Registry", debugPrintStencilTable);
      prop.setComment("Setting this to true, will cause each pattern registered to the Stencil Table, to be printed in the minecraft log. Useful for getting tool names.");
      debugPrintStencilTable = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      Debug.setPropertyOrder(propertyOrder);
    }
    {
      String category = "blacklists";
      List<String> propertyOrder = Lists.newArrayList();
      Blacklists = configFile.getCategory(category);
      
      prop = configFile.get(category, "Tool Station Blacklist", toolStationBlacklist);
      prop.setComment("Adding the name of a tool here (ie. hatchet, pickaxe, mattock), will remove it as a craftable tool, from the Tool Station");
      toolStationBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Tool Forge Blacklist", toolForgeBlacklist);
      prop.setComment("Adding the name of a tool here (ie. hatchet, pickaxe, mattock), will remove it as a craftable tool, from the Tool Forge");
      toolForgeBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Stencil Table Blacklist", stencilTableBlacklist);
      prop.setComment("Adding the name of a pattern here (ie. shard, sword_blade, axe_head), will remove it as a craftable pattern, from the Stencil Table");
      stencilTableBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      Blacklists.setPropertyOrder(propertyOrder);
    }
    
    boolean changed = false;
    if(configFile.hasChanged()) {
      configFile.save();
      changed = true;
    }
    return changed;
  }
}
