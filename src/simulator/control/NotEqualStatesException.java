package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -1221803898228865349L;

    private JSONObject actual;
    
    public NotEqualStatesException(JSONObject exp, JSONObject act, int step){
    	super("States are different at step " + step + System.lineSeparator() + " Actual: " + act + System.lineSeparator() + " Expected: " + exp +  System.lineSeparator());

    	actual = act;
	}

	public JSONObject get_act() {
    	return actual;
	}
    
}


