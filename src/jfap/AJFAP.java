package jfap;

import java.util.List;

import bwapi.DamageType;
import bwapi.Pair;
import bwapi.UnitType;

public abstract class AJFAP {
	
	  /**
	   * \brief Adds the unit to the simulator for player 1
	   * \param fu The FAPUnit to add
	   */
	  abstract void addUnitPlayer1(JFAPUnit fu);
	  /**
	   * \brief Adds the unit to the simulator for player 1, only if it is a combat unit
	   * \param fu The FAPUnit to add
	   */
	  abstract void addIfCombatUnitPlayer1(JFAPUnit fu);
	  /**
	   * \brief Adds the unit to the simulator for player 2
	   * \param fu The FAPUnit to add
	   */
	  abstract void addUnitPlayer2(JFAPUnit fu);
	  /**
	   * \brief Adds the unit to the simulator for player 2, only if it is a combat unit
	   * \param fu The FAPUnit to add
	   */
	  abstract void addIfCombatUnitPlayer2(JFAPUnit fu);

	  /**
	   * \brief Starts the simulation. You can run this function multiple times. Feel free to run once, get the state and keep running.
	   * \param nFrames the number of frames to simulate. A negative number runs the sim until combat is over.
	   */
	  abstract void simulate(int nFrames); // = 24*4, 4 seconds on fastest

	  /**
	   * \brief Default score calculation function
	   * \return Returns the score for alive units, for each player
	   */
	  abstract Pair<Integer, Integer> playerScores();
	  /**
	   * \brief Default score calculation, only counts non-buildings.
	   * \return Returns the score for alive non-buildings, for each player
	   */
	  abstract Pair<Integer, Integer> playerScoresUnits();
	  /**
	   * \brief Default score calculation, only counts buildings.
	   * \return Returns the score for alive buildings, for each player
	   */
	  abstract Pair<Integer, Integer> playerScoresBuildings();
	  /**
	   * \brief Gets the internal state of the simulator. You can use this to get any info about the unit participating in the simulation or edit the state.
	   * \return Returns a pair of pointers, where each pointer points to a vector containing that player's units.
	   */
	  abstract Pair<List<JFAPUnit>, List<JFAPUnit>> getState();
	  /**
	   * \brief Clears the simulation. All units are removed for both players. Equivalent to reconstructing.
	   */
	  abstract void clear();

	  abstract void dealDamage(final JFAPUnit fu, int damage, DamageType damageType);
	  abstract int distButNotReally(final JFAPUnit u1, final JFAPUnit u2);
	  abstract boolean isSuicideUnit(UnitType ut);
	  abstract void unitsim(final JFAPUnit fu, List<JFAPUnit> enemyUnits);
	  abstract void medicsim(final JFAPUnit fu, List<JFAPUnit> friendlyUnits);
	  abstract boolean suicideSim(final JFAPUnit fu, List<JFAPUnit> enemyUnits);
	  abstract void isimulate();
	  abstract void unitDeath(final JFAPUnit fu, List<JFAPUnit> itsFriendlies);
	  abstract void convertToUnitType(final JFAPUnit fu, UnitType ut);
}
