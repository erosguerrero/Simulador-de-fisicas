package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T>{
	List<Builder<T>> _builders; //constructores para los cuerpos
	List<JSONObject> _factoryElements;//Jsons por defecto
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = new ArrayList<>(builders);
		_factoryElements =  new ArrayList<>();          
		for (Builder<T> builder : _builders) {
			_factoryElements.add(builder.getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) {

		if(info==null) 
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		int i = 0;

		while(i < _builders.size() && _builders.get(i).createInstance(info) == null) i++;
		
		if(i == _builders.size()) throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString()); 
		

		return _builders.get(i).createInstance(info);
	}

	@Override
	public List<JSONObject> getInfo() {
		return _factoryElements;
	}
	
}
