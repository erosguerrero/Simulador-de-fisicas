package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	public T createInstance(JSONObject info);//Crea un objeto T a partir de info

	public List<JSONObject> getInfo(); //Default List asociada al tipo T
	
}
