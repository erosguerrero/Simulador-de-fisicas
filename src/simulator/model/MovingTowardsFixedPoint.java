package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
/*Para la ley de "MovingTowardsFixed", le tenemos que pasar un vector dirección y
 *  un valor acceleracion fija para obtener una aceleracion y aplicarsela a la fuerza por
 *   la formula de fuerza igual a masa por aceleracion*/
	
	Vector2D centro;
	double g;
	
	public MovingTowardsFixedPoint(Vector2D centro, double g){
		
		this.centro = new Vector2D(centro);
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for (Body body : bs) {
			body.addForce(centro.minus(body.getPosition()).direction().scale(g*body.getMass()));
		}
		
	}
	 @Override
	 public String toString() {
	   	return "Moving Towards "+centro+" with constant acceleration "+ g;
	  }


}
