package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	
	private Double eps;
	
	public EpsilonEqualStates(Double eps) {
		this.eps = eps;
	}
	
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
				//INICIALIZACIÓN
				Vector2D p1,p2,v1,v2,f1,f2;
				p1=new Vector2D(sa1.getJSONObject(i).getJSONArray("p").getDouble(0),sa1.getJSONObject(i).getJSONArray("p").getDouble(1));
				p2=new Vector2D(sa2.getJSONObject(i).getJSONArray("p").getDouble(0),sa2.getJSONObject(i).getJSONArray("p").getDouble(1));
				v1=new Vector2D(sa1.getJSONObject(i).getJSONArray("v").getDouble(0),sa1.getJSONObject(i).getJSONArray("v").getDouble(1));
				v2=new Vector2D(sa2.getJSONObject(i).getJSONArray("v").getDouble(0),sa2.getJSONObject(i).getJSONArray("v").getDouble(1));
				f1=new Vector2D(sa1.getJSONObject(i).getJSONArray("f").getDouble(0),sa1.getJSONObject(i).getJSONArray("f").getDouble(1));
				f2=new Vector2D(sa2.getJSONObject(i).getJSONArray("f").getDouble(0),sa2.getJSONObject(i).getJSONArray("f").getDouble(1));

				
				con2 = sa1.getJSONObject(i).getString("id").equals(sa2.getJSONObject(i).getString("id")) 
						&& Math.abs(sa1.getJSONObject(i).getDouble("m")-sa2.getJSONObject(i).getDouble("m")) <= eps
						&& p1.distanceTo(p2) <= eps
						&& v1.distanceTo(v2) <= eps
						&& f1.distanceTo(f2) <= eps;
				i++;
			}
		}else {
			con2 = false;
		}

		return con1 && con2;
	}

}
