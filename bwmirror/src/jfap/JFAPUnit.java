package jfap;

import java.util.Objects;

import bwapi.DamageType;
import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import bwapi.Unit;
import bwapi.UnitSizeType;
import bwapi.UnitType;
import bwapi.UpgradeType;
import bwapi.WeaponType;

public class JFAPUnit implements Comparable<JFAPUnit>{
	protected int id = 0;
	public Unit unit;
	protected int x = 0, y = 0;
	protected int health = 0;
	protected int maxHealth = 0;
	protected int armor = 0;
	protected int shields = 0;
	protected int shieldArmor = 0;
	protected int maxShields = 0;
	protected double speed = 0;
	protected boolean flying = false;
	protected int elevation = -1;
	protected UnitSizeType unitSize = UnitSizeType.Unknown;
	protected int groundDamage = 0;
	protected int groundCooldown = 0;
	protected int groundMaxRange = 0;
	protected int groundMinRange = 0;
	protected DamageType groundDamageType = DamageType.Unknown;
	protected int airDamage = 0;
	protected int airCooldown = 0;
	protected int airMaxRange = 0;
	protected int airMinRange = 0;
	protected DamageType airDamageType = DamageType.Unknown;
	protected UnitType unitType = UnitType.Unknown;
	protected Player player = null;
	protected boolean isOrganic = false;
	protected boolean didHealThisFrame = false;
	protected int score = 0;
	protected int attackCooldownRemaining = 0;
	protected Race race = Race.Unknown;

	public JFAPUnit(Unit u) {
		unit = u;
		x = u.getX();
		y = u.getY();
		id = u.getID();
		UnitType auxType = u.getType();
		Player auxPlayer = u.getPlayer();
		health = u.getHitPoints();
		unitSize = auxType.size();
		maxHealth = auxType.maxHitPoints();
		armor = auxPlayer.armor(auxType);
		shields = u.getShields();
		shieldArmor = auxPlayer.getUpgradeLevel(UpgradeType.Protoss_Plasma_Shields);
		maxShields = auxType.maxShields();
		speed = auxPlayer.topSpeed(auxType);
		flying = auxType.isFlyer();
		groundDamage = auxPlayer.damage(auxType.groundWeapon());
		groundCooldown = auxType.groundWeapon().damageFactor() > 0 && auxType.maxGroundHits() > 0 ? auxPlayer.weaponDamageCooldown(auxType) /
						(auxType.groundWeapon().damageFactor() * auxType.maxGroundHits()) : 0;
		groundMaxRange = auxPlayer.weaponMaxRange(auxType.groundWeapon());
		groundMinRange = auxType.groundWeapon().minRange();
		groundDamageType = auxType.groundWeapon().damageType();
		airDamage = auxPlayer.damage(auxType.airWeapon());
		airCooldown = auxType.airWeapon().damageFactor() > 0 && auxType.maxAirHits() > 0 ? auxType.airWeapon().damageCooldown() /
				auxType.airWeapon().damageFactor() * auxType.maxAirHits() : 0;
		airMaxRange = auxPlayer.weaponMaxRange(auxType.airWeapon());
		airMinRange = auxType.airWeapon().minRange();
		airDamageType = auxType.airWeapon().damageType();
		unitType = auxType;
		player = auxPlayer;
		isOrganic = auxType.isOrganic();
		score = auxType.destroyScore();
		race = auxType.getRace();
		doThings(u, JFAP.game);
	}

	public JFAPUnit() {
	}

	private void doThings(Unit u, Game game) {
		if(unitType == UnitType.Protoss_Carrier) {
			groundDamage = player.damage(UnitType.Protoss_Interceptor.groundWeapon());
			if (u != null && u.isVisible()) {
				final int interceptorCount = u.getInterceptorCount();
				if (interceptorCount > 0) {
					groundCooldown = (int)(Math.round(37.0f / interceptorCount));
				} else {
					groundDamage = 0;
					groundCooldown = 5;
				}
			} else {
				if (player != null) {
					groundCooldown = (int)(Math.round(37.0f / (player.getUpgradeLevel(UpgradeType.Carrier_Capacity) == 1 ? 8 : 4)));
				} else {
					groundCooldown = (int)(Math.round(37.0f / 8));
				}
			}
			groundDamageType = UnitType.Protoss_Interceptor.groundWeapon().damageType();
			groundMaxRange = 32 * 8;
			airDamage = groundDamage;
			airDamageType = groundDamageType;
			airCooldown = groundCooldown;
			airMaxRange = groundMaxRange;
		}
		else if(unitType == UnitType.Terran_Bunker) {
			groundDamage = player.damage(WeaponType.Gauss_Rifle);
			groundCooldown = UnitType.Terran_Marine.groundWeapon().damageCooldown() / 4;
			groundMaxRange = player.weaponMaxRange(UnitType.Terran_Marine.groundWeapon()) + 32;
			airDamage = groundDamage;
			airCooldown = groundCooldown;
			airMaxRange = groundMaxRange;
		}
		else if(unitType == UnitType.Protoss_Reaver) {
			groundDamage = player.damage(WeaponType.Scarab);
		}
		if (u != null && u.isStimmed()) {
			groundCooldown /= 2;
			airCooldown /= 2;
		}
		if (u != null && u.isVisible() && !u.isFlying()) {
			elevation = game.getGroundHeight(u.getTilePosition());
		}
		groundMaxRange *= groundMaxRange;
		groundMinRange *= groundMinRange;
		airMaxRange *= airMaxRange;
		airMinRange *= airMinRange;
		health <<= 8;
		maxHealth <<= 8;
		shields <<= 8;
		maxShields <<= 8;
	}

	@Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof JFAPUnit)) {
            return false;
        }
        JFAPUnit jfap = (JFAPUnit) o;
        return unit.equals(jfap.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit);
    }

	@Override
	public int compareTo(JFAPUnit arg0) {

		return this.id - arg0.id;
	}
}
