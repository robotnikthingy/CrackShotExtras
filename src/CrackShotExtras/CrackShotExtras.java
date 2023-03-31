package CrackShotExtras;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;


public class CrackShotExtras extends JavaPlugin  {

	private CrackShotExtras plugin = this;

	private static Boolean  MythicMobsInstalled;

	 static File PluginFolder;
	 static FileConfiguration WeaponsFileConfig;
	 static FileConfiguration ExplosivesFileConfig;
	 static FileConfiguration AttachmentsFileConfig;


	private static CrackShotExtras instance;

//	private WeaponListener weaponListener;

	public static ArrayList<CrackShotExtrasWeapon> Weapons = new ArrayList<CrackShotExtrasWeapon>();

	public void onEnable() {
		PluginFolder = getDataFolder();




		//register listeners
		getServer().getPluginManager().registerEvents(new WeaponListener(this), this);


		// check and see if any softdependencies are installed
		if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
			getLogger().info("MythicMobs detected, hooked into MythicMobs!");
			MythicMobsInstalled = true;

		}

		loadWeapons();
	}

	public static CrackShotExtras getInstance() {
		return instance;
	}


	public void loadWeapons(){

		ConfigurationSection WeaponsSect;
		ConfigurationSection ExplosivesSect;
		ConfigurationSection AttachmentsSect;

		//create weapon and config files if they dont exist
		File configFile = new File(PluginFolder + "/config.yml");

		File WeaponsFile = new File(PluginFolder + "/defaultWeapons.yml");
		File ExplosivesFile = new File(PluginFolder + "/defaultExplosives.yml");
		File AttachmentsFile = new File(PluginFolder + "/defaultAttachments.yml");

		if(!configFile.exists()){
			saveDefaultConfig();
		}

		if(!WeaponsFile.exists()){
			plugin.copy(plugin.getResource("defaultWeapons.yml"),WeaponsFile);
		}

		if(!ExplosivesFile.exists()){
			plugin.copy(plugin.getResource("defaultExplosives.yml"),ExplosivesFile);
		}

		if(!AttachmentsFile.exists()){
			plugin.copy(plugin.getResource("defaultAttachments.yml"),AttachmentsFile);
		}

		WeaponsFileConfig = YamlConfiguration.loadConfiguration(WeaponsFile);
		ExplosivesFileConfig = YamlConfiguration.loadConfiguration(ExplosivesFile);
		AttachmentsFileConfig = YamlConfiguration.loadConfiguration(AttachmentsFile);

		//load weapons into memory
		WeaponsSect = WeaponsFileConfig.getConfigurationSection("");
		ExplosivesSect = ExplosivesFileConfig.getConfigurationSection("");
		AttachmentsSect = AttachmentsFileConfig.getConfigurationSection("");

		for(String Weapon:WeaponsSect.getKeys(false)){
			getLogger().info("Loaded Weapon: " + Weapon);
			Weapons.add(new CrackShotExtrasWeapon(Weapon,"Weapon",WeaponsSect.getConfigurationSection(Weapon)));
		}

		for(String Weapon:ExplosivesSect.getKeys(false)){
			getLogger().info("Loaded Explosive: " + Weapon);
			Weapons.add(new CrackShotExtrasWeapon(Weapon,"Explosive",WeaponsSect.getConfigurationSection(Weapon)));
		}

		for(String Weapon:AttachmentsSect.getKeys(false)){
			getLogger().info("Loaded Attachment: " + Weapon);
			Weapons.add(new CrackShotExtrasWeapon(Weapon,"Attachment",WeaponsSect.getConfigurationSection(Weapon)));
		}

	}

	// used to copy files from the .jar file to the config folders
	void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// handles commands typed in game
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Boolean IsPlayer = false;
		Player player = null;
		if ((sender instanceof Player)) {
			player = (Player) sender;
			IsPlayer = true;
		}

		if (cmd.getName().equalsIgnoreCase("CrackshotExtras") || cmd.getName().equalsIgnoreCase("CrackshotExtras")) {

			if (args.length == 0) {
				if (IsPlayer) {
					player.sendMessage("Please enter a subcommand: CrackshotExtras <reload>");
				}

				getLogger().info("Please enter a subcommand: CrackshotExtras <reload>");

				return true;

				// there was a subcommand entered, lets see what it is
			} else {

				if (args[0].equalsIgnoreCase("reload")) {
					//reload the configs and schematics
					loadWeapons();

					//send a message to the player if executed in game
					if (IsPlayer) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Plugin reloaded!"));
					}
				}


			}
		}

		return true;
	}


	public static Boolean isMythicMobsInstalled() {
		return MythicMobsInstalled;
	}
}
