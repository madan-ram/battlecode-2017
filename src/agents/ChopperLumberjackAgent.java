package agents;
import goap.*;
import god.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

import battlecode.common.RobotController;

public class ChopperLumberjackAgent extends GoapAgent {
	
	public ArrayList<HashMap<String, Object>> createGoalsState() {
		ArrayList<HashMap<String, Object>> goals = new ArrayList<HashMap<String, Object>>();
		
		//addGoal takes in list of K,V and return goal, this list represent "and goal"
		//list of "and goal" forms goals
				
		goals.add(addGoal(new Tuple("chopForPath", true)));
		
		goals.add(addGoal(new Tuple("chopForSpace", true)));

		goals.add(addGoal(new Tuple("chopForbot", true)));
		
		goals.add(addGoal(new Tuple("chopEnemyTree", true)));
		
		goals.add(addGoal(new Tuple("moveRandom", true)));
		
		return goals;
	}
	
	public void createActionList(RobotController rc,DataProvider dataProvider) {
		addAction(
				new actions.ChopForSpaceAction(rc, dataProvider),
				new actions.MoveRandomAction(rc, dataProvider)
		);
	}
} 
