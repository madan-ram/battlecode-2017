package fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import goap.GoapAction;
import goap.GoapAgent;
import goap.GoapPlanner;
import god.DataProvider;
import scala.reflect.internal.Trees.Super;

public class IdleState extends FSMState {
	
	GoapPlanner planner;
	ArrayList<GoapAction> availableActions;
	
	DataProvider dataProvider;
	GoapAgent agent;
	RobotController rc;
	FSM fsm;
	
	public IdleState(FSM fsm, RobotController rc, GoapAgent agent, DataProvider dataProvider) {
		this.fsm = fsm;
		this.rc = rc;
		this.agent = agent;
		this.dataProvider = dataProvider;
		
		availableActions = GoapAgent.getActions();
		System.out.printf("Avilable action list %s\n", availableActions);
		//create planner object
		planner = new GoapPlanner();
	}
	
	public void play() {
		
		// get the world state and the goal we want to plan for
		HashMap<String, Object> worldState = dataProvider.getWorldState();
		HashMap<String, Object> goal = agent.createGoalState();
		
		// Plan
		Queue<GoapAction> plan = planner.plan(rc, availableActions, worldState, goal);
		
		if (plan != null) {
			// we have a plan, hooray!
			dataProvider.planFound(goal, plan);
			
			fsm.popState(); // move to PerformAction state
			
			fsm.pushState(agent.performActionState);

		} else {
			// ugh, we couldn't get a plan
			System.out.printf("<color=orange>Failed Plan:</color> %s\n", GoapAgent.prettyPrint(goal));
			dataProvider.planFailed(goal);
			fsm.popState (); // move back to IdleAction state
			fsm.pushState (agent.idleState);
		}
	}

	
}
