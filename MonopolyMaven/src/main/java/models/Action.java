package models;

/**
 * @author Ana
 */
public class Action {

	public enum Type {
		PAY, PAY_PLAYERS, EXIT_JAIL, ROLL_DICE, GO_BACK_CELLS, MOVE_CELLS, SUM_CELLS, GO_EXIT, RECIEVE, RECIEVE_PLAYERS
	}

	public int idAction;
	public Type actionType;
	public int times;

	public Action(int idAction, Type actionType, int times) {
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

	public Type getActionType() {
		return actionType;
	}

	public void setActionType(Type actionType) {
		this.actionType = actionType;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}