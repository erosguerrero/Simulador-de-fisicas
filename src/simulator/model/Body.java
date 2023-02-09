package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D velocidad;
	protected Vector2D fuerza;
	protected Vector2D posicion;
	protected double m;//masa

    
	public Body(String id, Vector2D velocidad, Vector2D posicion, double mass) {
   	  this.id = id;
		  this.velocidad = velocidad;
		  this.posicion = posicion;
		  this.m = mass;
		  resetForce();
    }

    public String getId() {
   	 return id;
	}
    
	public Vector2D getVelocity() {
   	 return velocidad;
	}
    
	public Vector2D getForce() {
  	      return fuerza;
	}
    
	public Vector2D getPosition() {
  	      return posicion;
	}
    
	public double getMass() {
  	      return m;
	}
    
	void addForce(Vector2D f) { //suma a la fuerza actual, la fuerza viene como parámetro
   	 	fuerza = fuerza.plus(f);
	}
    
	void resetForce() { //inicializa la fuerza invocando a la constructora por defecto de Vector 2D
		fuerza = new Vector2D();
	}
    
	void move(double t) { //mueve el cuerpo durante t segundos
    	Vector2D a;
    	if(m==0) { //calculamos la aceleracion en funcion de la masa
        	a = new Vector2D();
    	}
    	else {
        	a = fuerza.scale(1.0/m);
    	}
    	posicion = posicion.plus(velocidad.scale(t).plus(a.scale(0.5 * t * t)));
    	velocidad = velocidad.plus(a.scale(t));
    
	}

    
	public JSONObject getState() {
   	 JSONObject jo1 = new JSONObject(); // devuelve el estado del objeto en formato JSON
   	 jo1.put("id", getId());
   	 jo1.put("m", getMass());
   	 jo1.put("p", getPosition().asJSONArray());
   	 jo1.put("v", getVelocity().asJSONArray());
   	 jo1.put("f", getForce().asJSONArray());
   	 return jo1;
   	 
	}
    
	public String toString() {
  	  return getState().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}


