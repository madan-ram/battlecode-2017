package goap;

import java.util.HashMap;
import battlecode.common.*;
import god.DataProvider;

public abstract class GoapAction {
	
	protected static RobotController rc;
	protected static DataProvider dataProvider;
	
	public static int HIRE_FARM_GARDENER_ACTION = 0;
	public static int LOCATE_FREE_SPACE_ACTION = 1;
	public static int MOVE_RANDOM_ACTION = 2;
	public static int PLANT_TREE_ACTION = 3;
	
	protected GoapAction(RobotController rc, DataProvider dataProvider) {
		GoapAction.rc = rc;
		GoapAction.dataProvider = dataProvider;
	}
	
	protected static RobotController getRobotController() {
		return rc;
	}
	
	protected static DataProvider getRDataProvider() {
		return dataProvider;
	}
	
	protected HashMap<String, Object> effects = new HashMap<String, Object>();
	protected HashMap<String, Object> preConditions = new HashMap<String, Object>();
	protected static Direction[] dirs_8s = new Direction[8];
	protected static Direction[] dirs_6s = new Direction[6]; ;

	static void initDirection(Direction[] d) {
    	for(int i=0; i<d.length; i++) {
    		d[i] = new Direction( (float) (-Math.PI + i* (Math.PI/(d.length/2.)) ) );
    	}
    }
	
	public static boolean isCircleOccupiedByTree(MapLocation center, float radius, Team team) throws GameActionException{
        
        if(team != null){
        	return (rc.isCircleOccupied(center, radius) && rc.senseNearbyTrees(center, radius, team).length > 0);
		}
		else {
			int count = rc.senseNearbyTrees(center, radius, Team.A).length
					+rc.senseNearbyTrees(center, radius, Team.B).length
					+rc.senseNearbyTrees(center, radius, Team.NEUTRAL).length;
			return (rc.isCircleOccupied(center, radius) && count > 0);
		}
    }
	
	public static boolean isCircleOccupiedByRobot(MapLocation center, float radius, Team team) throws GameActionException{
		if(team != null){
			return (rc.isCircleOccupied(center, radius) && rc.senseNearbyRobots(center, radius, team).length > 0);
		}
		else {
			
			int count = rc.senseNearbyRobots(center, radius, Team.A).length
					+rc.senseNearbyRobots(center, radius, Team.B).length
					+rc.senseNearbyRobots(center, radius, Team.NEUTRAL).length;
			return (rc.isCircleOccupied(center, radius) && count > 0);
		}
    }
	
	public static void init(RobotController rc) {
		initDirection(dirs_8s);
		
		initDirection(dirs_6s);
	}
	
	public static Direction[] getEightDirection() {
		return dirs_8s;
	}
	
	public static Direction[] getSixDirection() {
		return dirs_6s;
	}
	
	public abstract void doReset();
	
	//If is done return false, it indicates action failed but can perform later 
	public abstract boolean isDone();
	
	// action failed, we need to plan again (perform return false only when action failure should reconstruct new plan)
	public abstract boolean perform(RobotController rc) throws GameActionException;
	
	public abstract boolean checkProceduralPreCondtion(RobotController rc) throws GameActionException;

	public abstract float getCost();
	
	public void addEffect(String k, boolean v) {
		effects.put(k, v);
	}
	
	public void addPreCondition(String k, boolean v) {
		preConditions.put(k, v);
	}
	
	public HashMap<String, Object> getEffects() {
		return effects;
	}
	
	public HashMap<String, Object> getPreConditions() {
		return preConditions;
	}
}
