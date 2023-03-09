/**
 * Title: SwordAttack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import gameObjects.GameObject;
import utils.Random;

/**
 * The Class SwordAttack. Abstract class for all sword attack to use. This class contains 
 * the method for random damage based on fighters attack range and the defenders defense.
 */
public abstract class SwordAttack extends FightSceneAction {

	
	private static int damage;
	/**
	 * Sword attack.
	 *
	 * @param obj the obj
	 */
	public SwordAttack(GameObject obj) {
		super(obj);
		damage = -1;
	}

	/**
	 * Random damage.
	 *
	 * @param selected the selected
	 * @param strength - 1 would be normal, less than 1 is lesser attack, greater than 
	 * 1 greater attack
	 */
	public void randomDamage(Fighter selected,float strength) {
		if (damage != -1)return;
		
		//determine damage using attackrange and strength
		int lowEnd = (int) (((Fighter)obj).getAttackRange().a * strength);
		int highEnd = (int) (((Fighter)obj).getAttackRange().b * strength);
		int defense = selected.getDefense();
		
		int attack;
		//do not allow less then zero, and keep top end above lowEnd
		if (selected.isDefend()) { 
			damage = Random.getRndInteger(Math.max(0, lowEnd - defense),
					Math.max(lowEnd, highEnd - defense));
			return;
		}
		
		
		attack  = Random.getRndInteger(lowEnd, highEnd);
	
		//check if lucky and get big hit 
		double bigHit = Math.random();
		System.out.println(bigHit);
		if (bigHit * 10 > 8)attack *= 2;
		
		damage = attack;
	}

	/**
	 * Public method used to get
	 * @return the damage
	 */
	public static int getDamage() {
		return damage;
	}

	/**
	 * Public method used to set
	 * @param damage the damage to set
	 */
	public static void setDamage(int damage) {
		SwordAttack.damage = damage;
	}

}
