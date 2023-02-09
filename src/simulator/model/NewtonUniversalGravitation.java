package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{

    protected double _G; //valor gravitacional 
   	 
    public NewtonUniversalGravitation(double _G) {
    	this._G = _G;
    }
    
    @Override
    public void apply(List<Body> bs) {
   	 for(Body o: bs){
   		 for(Body o2: bs){
   			 if(!o.equals(o2)) {
   				 o.addForce(force(o, o2));
   					 
   			 }
   		 }
   	 }
    }
    
	private Vector2D force(Body a, Body b) { // calculo de f_ab
   	 Vector2D delta = b.getPosition().minus(a.getPosition());
   	 double dist = delta.magnitude();
   	 double magnitude = dist>0 ? (_G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
   	 return delta.direction().scale(magnitude);
   }

   public String toString() {
   	return "Newton Universal Gravitation G: " + _G;
   }
}

