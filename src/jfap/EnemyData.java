package jfap;

import bwapi.Player;
import bwapi.Position;
import bwapi.Race;
import bwapi.Unit;
import bwapi.UnitType;
import static ecgberht.Ecgberht.getGame;

public class EnemyData extends AEnemyData{
	
	final int ZERGREGEN = 8;
	final int PROTOSSSHIELDREGEN = 7;
	Unit u = null;
	Player lastPlayer = null;
	UnitType lastType;
	Position lastPosition;
	int lastShields;
	int lastHealth;
	int frameLastSeen = 0;
	int frameLastDetected = 0;
	int unitID;
	boolean positionInvalidated = true;
	boolean isCompleted = false;
	
	public EnemyData() {
	}
	
	public EnemyData(Unit unit){
		if (unit != null) {
			u = unit;
			initFromUnit();
		}
	}
	
	public EnemyData(UnitType ut){
		u = null;
		lastType = ut;
		lastPlayer = getGame().self();
		lastPosition = null;
		lastHealth= ut.maxHitPoints();
		lastShields = ut.maxShields();
	}
	
	@Override
	final void initFromUnit() {
		lastType = u.getType();
		lastPlayer = u.getPlayer();
		unitID = u.getID();
		updateFromUnit(u);
		
	}

	@Override
	final void updateFromUnit() {
		updateFromUnit(u);
	}

	@Override
	final void updateFromUnit(final Unit unit) {
		frameLastSeen = getGame().getFrameCount();
		lastPosition = unit.getPosition();
		if (!unit.isDetected())
		    return;
		  frameLastDetected = getGame().getFrameCount();
		  lastHealth = unit.getHitPoints();
		  lastShields = unit.getShields();
		  isCompleted = unit.isCompleted();
		
	}

	@Override
	int expectedHealth() {
		if (lastType.getRace() == Race.Zerg) {
			return Math.min(((getGame().getFrameCount() - frameLastDetected) * ZERGREGEN) / 
					256 + lastHealth, lastType.maxHitPoints()); 
		}
		return lastHealth;
	}

	@Override
	int expectedShields() {
		if (lastType.getRace() == Race.Protoss) {
			return Math.min(((getGame().getFrameCount() - frameLastDetected) *
					PROTOSSSHIELDREGEN) / 256 + lastShields, lastType.maxShields());
		}
		return lastShields;
	}

	@Override
	boolean isFriendly() {
		 return lastPlayer.getID() == getGame().self().getID();
	}

	@Override
	boolean isEnemy() {
		return !isFriendly();
	}
}
