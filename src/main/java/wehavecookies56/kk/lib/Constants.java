package wehavecookies56.kk.lib;

import java.util.HashMap;
import java.util.Map;

import wehavecookies56.kk.driveforms.ModDriveForms;

public class Constants {

	public static final int
	// Input
	LEFT_MOUSE = 0, 
	RIGHT_MOUSE = 1, 
	MIDDLE_MOUSE = 2, 
	WHEEL_UP = -1, 
	WHEEL_DOWN = 1,
	SCALE_AUTO = 0, 
	SCALE_SMALL = 1, 
	SCALE_NORMAL = 2, 
	SCALE_LARGE = 3,

	// OTHER
	TICKS_PER_SECOND = 20,
	
	MAX_MAGIC_LEVEL = 3
	;

	public static Map<String, Integer> costs;

	public static void registerCosts () {
		costs = new HashMap<String, Integer>();
		costs.put(Strings.Spell_Fire, 20);
		costs.put(Strings.Spell_Blizzard, 15);
		costs.put(Strings.Spell_Thunder, 30);
		costs.put(Strings.Spell_Gravity, 25);
		costs.put(Strings.Spell_Aero, 20);
		costs.put(Strings.Spell_Stop, 15);
		costs.put(Strings.Spell_Cure, -1);

		costs.put("potion", 1);
		costs.put("ether", 1);
		costs.put("elixir", 1);

		costs.put(Strings.Form_Valor, (int) ModDriveForms.Valor.getCost());
		costs.put(Strings.Form_Wisdom, (int) ModDriveForms.Wisdom.getCost());
		costs.put(Strings.Form_Limit, (int) ModDriveForms.Limit.getCost());
		costs.put(Strings.Form_Master, (int) ModDriveForms.Master.getCost());
		costs.put(Strings.Form_Final, (int) ModDriveForms.Final.getCost());

	}
	
	public static Map<String, Map> magicLevels;
	public static Map<Integer, String> fireLevels;
	public static Map<Integer, String> blizzardLevels;
	public static Map<Integer, String> cureLevels;
	public static Map<Integer, String> thunderLevels;
	public static Map<Integer, String> gravityLevels;
	public static Map<Integer, String> stopLevels;
	public static Map<Integer, String> aeroLevels;
	
	public static void registerMagicLevels() {
		
		fireLevels = new HashMap<Integer, String>();
		fireLevels.put(1, Strings.Spell_Fire);
		fireLevels.put(2, Strings.Spell_Fira);
		fireLevels.put(3, Strings.Spell_Firaga);
		
		blizzardLevels = new HashMap<Integer, String>();
		blizzardLevels.put(1, Strings.Spell_Blizzard);
		blizzardLevels.put(2, Strings.Spell_Blizzara);
		blizzardLevels.put(3, Strings.Spell_Blizzaga);
		
		cureLevels = new HashMap<Integer, String>();
		cureLevels.put(1, Strings.Spell_Cure);
		cureLevels.put(2, Strings.Spell_Cura);
		cureLevels.put(3, Strings.Spell_Curaga);
		
		thunderLevels = new HashMap<Integer, String>();
		thunderLevels.put(1, Strings.Spell_Thunder);
		thunderLevels.put(2, Strings.Spell_Thundara);
		thunderLevels.put(3, Strings.Spell_Thundaga);
		
		gravityLevels = new HashMap<Integer, String>();
		gravityLevels.put(1, Strings.Spell_Gravity);
		gravityLevels.put(2, Strings.Spell_Gravira);
		gravityLevels.put(3, Strings.Spell_Graviga);
		
		stopLevels = new HashMap<Integer, String>();
		stopLevels.put(1, Strings.Spell_Stop);
		stopLevels.put(2, Strings.Spell_Stopra);
		stopLevels.put(3, Strings.Spell_Stopga);
		
		aeroLevels = new HashMap<Integer, String>();
		aeroLevels.put(1, Strings.Spell_Aero);
		aeroLevels.put(2, Strings.Spell_Aerora);
		aeroLevels.put(3, Strings.Spell_Aeroga);
		
		magicLevels = new HashMap<String, Map>();
		magicLevels.put(Strings.Spell_Fire, fireLevels);
		magicLevels.put(Strings.Spell_Blizzard, blizzardLevels);
		magicLevels.put(Strings.Spell_Cure, cureLevels);
		magicLevels.put(Strings.Spell_Thunder, thunderLevels);
		magicLevels.put(Strings.Spell_Gravity, gravityLevels);
		magicLevels.put(Strings.Spell_Stop, stopLevels);
		magicLevels.put(Strings.Spell_Aero, aeroLevels);

	}
	
	public static String getMagicName(String name, int level){
		if(magicLevels.containsKey(name)){
			if(magicLevels.get(name).containsKey(level)){
				return (magicLevels.get(name)).get(level).toString();
			}
		}
		return null;
	}

	

	public static int getCost (String name) {
		return costs.get(name);
	}

	// Drive abilities
	public static double 
	VALOR_SPEED = 1.1, 
	VALOR_JUMP_1 = 0.025, 
	VALOR_JUMP_2 = 0.03, 
	VALOR_JUMP_3 = 0.035,
	MASTER_JUMP_1 = 0.015,
	MASTER_JUMP_2 = 0.0175,
	MASTER_JUMP_3 = 0.02,
	FINAL_SPEED = 1.23, 
	FINAL_JUMP = 0.03, 
	FINAL_GLIDE_1 = 0.8,
	FINAL_GLIDE_2 = 0.6,
	FINAL_GLIDE_3 = 0.4
	;

	public static final double 
	PLAYER_WALKSPEED = 0.10000000149011612D, 
	PLAYER_JUMP = 0.42D;
	;

}
