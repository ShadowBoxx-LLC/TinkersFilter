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
  
  private Config() {}
  
  public static boolean generalDebugPrint       = false;
  public static boolean debugPrintMaterial      = false;
  public static boolean debugPrintTrait         = false;
  public static boolean debugPrintModifier      = false;
  public static boolean debugPrintMelting       = false;
  public static boolean debugPrintEntityMelting = false;
  public static boolean debugPrintAlloy         = false;
  public static boolean debugPrintSmelteryFuel  = false;
  public static boolean debugPrintDryingRack    = false;
  public static boolean debugPrintToolStation   = false;
  public static boolean debugPrintToolForge     = false;
  public static boolean debugPrintStencilTable  = false;
  
  public static String[] materialBlacklist      = {};
  public static String[] traitBlacklist         = {};
  public static String[] modifierBlacklist      = {};
  public static String[] meltingBlacklist       = {};
  public static String[] entityMeltingBlacklist = {};
  public static String[] alloyBlacklist         = {};
  public static String[] smelteryFuelBlacklist  = {};
  public static String[] dryingRackBlacklist    = {};
  public static String[] toolStationBlacklist   = {};
  public static String[] toolForgeBlacklist     = {};
  public static String[] stencilTableBlacklist  = {};
  
  static Configuration configFile;
  
  static ConfigCategory Debug;
  static ConfigCategory Blacklists;
  static ConfigCategory General;
  
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
      String category = "Registry Debug";
      List<String> propertyOrder = Lists.newArrayList();
      Debug = configFile.getCategory(category);

      prop = configFile.get(category, "Print Material Registry", debugPrintMaterial);
      prop.setComment("Setting this to true, will cause each material registered, to be printed in the minecraft log.");
      debugPrintMaterial = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Trait Registry", debugPrintTrait);
      prop.setComment("Setting this to true, will cause each trait registered, to be printed in the minecraft log.");
      debugPrintTrait = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Modifier Registry", debugPrintModifier);
      prop.setComment("Setting this to true, will cause each modifier registered, to be printed in the minecraft log.");
      debugPrintModifier = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Melting Registry", debugPrintMelting);
      prop.setComment("Setting this to true, will cause each melting recipe registered, to be printed in the minecraft log.");
      debugPrintMelting = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Entity Melting Registry", debugPrintEntityMelting);
      prop.setComment("Setting this to true, will cause each entity melting recipe registered, to be printed in the minecraft log.");
      debugPrintEntityMelting = prop.getBoolean();
      propertyOrder.add(prop.getName());

      prop = configFile.get(category, "Print Alloy Registry", debugPrintAlloy);
      prop.setComment("Setting this to true, will cause the end result for each allory recipe registered, to be printed in the minecraft log.");
      debugPrintAlloy = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Smeltery Fuel Registry", debugPrintSmelteryFuel);
      prop.setComment("Setting this to true, will cause each Smeltery fuel type registered, to be printed in the minecraft log. Useful for getting fuel names.");
      debugPrintSmelteryFuel = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Print Drying Rack Registry", debugPrintDryingRack);
      prop.setComment("Setting this to true, will cause the end result item for each recipe registered to the Drying Rack, to be printed in the minecraft log.");
      debugPrintDryingRack = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
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
      String category = "Registry Blacklists";
      List<String> propertyOrder = Lists.newArrayList();
      Blacklists = configFile.getCategory(category);

      prop = configFile.get(category, "Material Blacklist", materialBlacklist);
      prop.setComment("Adding the identifier of a material (ie. paper, wood, flint), will remove that material.");
      materialBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Trait Blacklist", traitBlacklist);
      prop.setComment("Adding the identifier for a material and a trait in the format <material>:<trait> (ie. wood:ecological, prismarine:jagged, sponge:squeaky), will remove that trait from the material.");
      traitBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Modifier Blacklist", modifierBlacklist);
      prop.setComment("Adding the identifier for a modifier (ie. aridiculous, cheap, ecological), will remove the availability of that modifier.");
      modifierBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Item Melting Blacklist", meltingBlacklist);
      prop.setComment("Adding the name of an item and a material in the format <item>:<material> (ie. anvil:iron, golden_boots:gold, sand:glass), will prevent it from melting in the Smeltery.");
      meltingBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Entity Melting Blacklist", entityMeltingBlacklist);
      prop.setComment("Adding components in the format <entityname>:<material> (ie. IronGolem:iron, Snowman:water, Villager:emerald), will stop the entity from melting into that material in the Smeltery");
      entityMeltingBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Alloy Blacklist", alloyBlacklist);
      prop.setComment("Adding the name of an alloy (ie. obsidian, clay, pigiron), will prevent the alloy from forming in the Smeltery.");
      alloyBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Smeltery Fuel Blacklist", smelteryFuelBlacklist);
      prop.setComment("Adding the name of a Smeltery Fuel (ie. lava), will remove it as a usable fuel in the Smeltery.");
      smelteryFuelBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Drying Rack Blacklist", dryingRackBlacklist);
      prop.setComment("Adding the registry name of the input item and output item in the format <item>:<item> from a Drying Rack recipe (ie. slime_sapling:deadbush, cooked_rabbit:leather, clay:dried_clay), will remove it.");
      dryingRackBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Tool Station Blacklist", toolStationBlacklist);
      prop.setComment("Adding the registry name of a tool here (ie. tcon:hatchet, tcon:pickaxe, tcon:mattock), will remove it as a craftable tool, from the Tool Station");
      toolStationBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Tool Forge Blacklist", toolForgeBlacklist);
      prop.setComment("Adding the registry name of a tool here (ie. tcon:hatchet, tcon:pickaxe, tcon:mattock), will remove it as a craftable tool, from the Tool Forge");
      toolForgeBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      prop = configFile.get(category, "Stencil Table Blacklist", stencilTableBlacklist);
      prop.setComment("Adding the registry name of a pattern here (ie. tcon:shard, tcon:sword_blade, tcon:axe_head), will remove it as a craftable pattern, from the Stencil Table");
      stencilTableBlacklist = prop.getStringList();
      propertyOrder.add(prop.getName());
      
      Blacklists.setPropertyOrder(propertyOrder);
    }
    {
      String category = "General";
      List<String> propertyOrder = Lists.newArrayList();
      General = configFile.getCategory(category);
      
      prop = configFile.get(category, "General Debugging Info", generalDebugPrint);
      prop.setComment("You should probably leave this false! Setting it to true spams the logfile with information that is useful to developers.");
      generalDebugPrint = prop.getBoolean();
      propertyOrder.add(prop.getName());
      
      General.setPropertyOrder(propertyOrder);
    }
    
    boolean changed = false;
    if(configFile.hasChanged()) {
      configFile.save();
      changed = true;
    }
    return changed;
  }
}
