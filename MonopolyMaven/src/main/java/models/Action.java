package models;

/**
 * @author Ana
 */
public class Action {

	public enum Type {
		PAY, PAY_PLAYERS, EXIT_JAIL, ROLL_DICE, GO_BACK_CELLS, MOVE_CELLS, SUM_CELLS, GO_EXIT, RECIEVE, RECIEVE_PLAYERS
	}

	public int idAction;
	public String actionType;
	public int times;

	public Action(int idAction, String actionType, int times) {
		this.idAction = idAction;
		this.actionType = actionType;
		this.times = times;
	}

	public int getIdAction() {
		return idAction;
	}

	public void setIdAction(int idAction) {
		this.idAction = idAction;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
