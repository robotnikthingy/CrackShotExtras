package CrackShotExtras;

import com.shampaggon.crackshot.events.*;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.AbstractWorld;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitPlayer;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitWorld;
import io.lumine.xikage.mythicmobs.mobs.GenericCaster;
import io.lumine.xikage.mythicmobs.skills.Skill;
import io.lumine.xikage.mythicmobs.skills.SkillCaster;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.SkillTrigger;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class WeaponListener implements Listener{
	Plugin plugin;
	
	WeaponListener(Plugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onWeaponShoot(WeaponShootEvent event){
		String WeaponTitle = event.getWeaponTitle();

		//check if weapon exists in this plugin
		for(CrackShotExtrasWeapon Weapon:CrackShotExtras.Weapons){
			if(Weapon.GetName().equals(WeaponTitle)){
				//apply mechanics and do stuff
				if(Weapon.getConfigSect().contains("onWeaponShoot.SpawnMythicMob") && CrackShotExtras.isMythicMobsInstalled() == true){
					String mobName = Weapon.getConfigSect().getString("onWeaponShoot.SpawnMythicMob");

					MythicMobs.inst().getMobManager().spawnMob(mobName,event.getPlayer().getLocation());
				}

				if(Weapon.getConfigSect().contains("onWeaponShoot.doSkill") && CrackShotExtras.isMythicMobsInstalled() == true){
					String skill = Weapon.getConfigSect().getString("onWeaponShoot.doSkill.MythicMobsSkill");
					Skill sk = MythicMobs.inst().getSkillManager().getSkill(skill).get();

					GenericCaster caster = new GenericCaster(new BukkitPlayer(event.getPlayer()));
					SkillMetadata skData = new SkillMetadata(SkillTrigger.TIMER, caster, caster.getEntity());

					Block block = event.getPlayer().getTargetBlock(null, 100);
					BukkitWorld bukkitWorld = new BukkitWorld(block.getLocation().getWorld());
					AbstractLocation location = new AbstractLocation(bukkitWorld, block.getLocation().getX(), block.getLocation().getY(),block.getLocation().getZ());
					AbstractLocation origin = new AbstractLocation(bukkitWorld, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY(),event.getPlayer().getLocation().getZ());

					skData.setLocationTarget(location);
					skData.setOrigin(origin);

					plugin.getLogger().info("Is Skill Usable: " + sk.isUsable(skData));
					sk.execute(skData);
				}
			}

		}

	}

	@EventHandler
	public void onWeaponExplode(WeaponExplodeEvent event){
		String WeaponTitle = event.getWeaponTitle();

		//check if weapon exists in this plugin
		for(CrackShotExtrasWeapon Weapon:CrackShotExtras.Weapons){
			if(Weapon.GetName().equals(WeaponTitle)){
				//apply mechanics and do stuff
				if(Weapon.getConfigSect().contains("onWeaponExplode.SpawnMythicMob")){
					String mobName = Weapon.getConfigSect().getString("onWeaponExplode.SpawnMythicMob");

					MythicMobs.inst().getMobManager().spawnMob(mobName,event.getLocation());
				}
			}
		}
	}

	@EventHandler
	public void onWeaponDamage(WeaponDamageEntityEvent event){
		String WeaponTitle = event.getWeaponTitle();

		//check if weapon exists in this plugin
		for(CrackShotExtrasWeapon Weapon:CrackShotExtras.Weapons){
			if(Weapon.GetName().equals(WeaponTitle)){
				//apply mechanics and do stuff
				if(Weapon.getConfigSect().contains("onWeaponDamage.SpawnMythicMob")){
					String mobName = Weapon.getConfigSect().getString("onWeaponDamage.SpawnMythicMob");

					MythicMobs.inst().getMobManager().spawnMob(mobName,event.getVictim().getLocation());
				}
			}
		}
	}


	@EventHandler
	public void onWeaponHitBlock(WeaponHitBlockEvent  event){
		String WeaponTitle = event.getWeaponTitle();

		//check if weapon exists in this plugin
		for(CrackShotExtrasWeapon Weapon:CrackShotExtras.Weapons){
			if(Weapon.GetName().equals(WeaponTitle)){
				//apply mechanics and do stuff
				if(Weapon.getConfigSect().contains("onWeaponHitBlock.SpawnMythicMob")){
					String mobName = Weapon.getConfigSect().getString("onWeaponHitBlock.SpawnMythicMob");

					MythicMobs.inst().getMobManager().spawnMob(mobName,event.getBlock().getLocation());
				}
			}
		}
	}


	@EventHandler
	public void onWeaponReload(WeaponReloadEvent event){
		String WeaponTitle = event.getWeaponTitle();

		//check if weapon exists in this plugin
		for(CrackShotExtrasWeapon Weapon:CrackShotExtras.Weapons){
			if(Weapon.GetName().equals(WeaponTitle)){
				//apply mechanics and do stuff
				if(Weapon.getConfigSect().contains("onWeaponShoot.SpawnMythicMob")){
					String mobName = Weapon.getConfigSect().getString("onWeaponShoot.SpawnMythicMob");

					MythicMobs.inst().getMobManager().spawnMob(mobName,event.getPlayer().getLocation());
				}
			}
		}
	}
	
}
