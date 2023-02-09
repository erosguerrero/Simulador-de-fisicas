package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		Boolean con1 = (s1.getDouble("time") == s2.getDouble("time"));
		Boolean con2 = true;
		JSONArray sa1 = s1.getJSONArray("bodies");
		JSONArray sa2 = s2.getJSONArray("bodies");
		//id es un string
		//mass es un double
		if(sa1.length() == sa2.length()) {
			int i=0;
	
			while(i<sa1.length() && con2) {
				con2 = sa1.getJSONObject(i).getString("id").equals(sa2.getJSONObject(i).getString("id"))  &&  sa1.getJSONObject(i).getDouble("m") == sa2.getJSONObject(i).getDouble("m");
				i++;
			}
		}else {
			con2 = false;
		}
		
		return con1 && con2;
	}

}
