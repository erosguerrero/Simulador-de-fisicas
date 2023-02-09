package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{
//PREGUNTAR
	public MassEqualStatesBuilder() {
		_typeTag = "meq";
		_desc = "Mass-equal states comparator";
	}
	

	@Override
	protected StateComparator createTheInstance(JSONObject data) {
		return new MassEqualStates();
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject result = new JSONObject();
		return result;
	}

}
