package CrackShotExtras;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Jeff on 7/19/2017.
 */
public class CrackShotExtrasWeapon {

    private String Name;
    private String Type;

    private ConfigurationSection configSect;

    //list of mechanics the gun has
    Boolean MythicMobsSpawn;
    Boolean CustomExplosion;

    CrackShotExtrasWeapon(String Name, String Type, ConfigurationSection configSect){
        this.Name = Name;
        this.Type = Type;
        this.configSect = configSect;

    }


    String GetName(){
        return Name;
    }

    public ConfigurationSection getConfigSect() {
        return configSect;
    }

    public String getType() {
        return Type;
    }
    //load properties of the weapon from crackshot and crackshot plus, etc

}
