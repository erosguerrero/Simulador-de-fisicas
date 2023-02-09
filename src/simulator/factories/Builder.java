package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String _typeTag; //Se aclara que es protected por documentacion
	protected String _desc;
	public Builder() {
		
	}
	
	T createInstance (JSONObject info){
		T b = null;
		if(_typeTag != null && _typeTag.equals(info.get("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	protected abstract T createTheInstance(JSONObject jsonObject); //La clase se encarga
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("data", createData());
		info.put("desc", _desc);
		return info;
	}
	
	protected JSONObject createData() { //Las clases lo sobreescriben
		return new JSONObject();
		}
	
	
		
}
