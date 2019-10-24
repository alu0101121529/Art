import java.util.*;

public class nodo {

    public Float coste;
    public Float costeheuristica;
    private Vector<Integer> padres = new Vector<Integer>();
    public ArrayList<nodo> hijos = new ArrayList<nodo>();
    private int id_;

    
    
    public nodo(Float coste, int ID, Float heuristica){
        this.coste= coste;
        this.id_=ID;
        this.costeheuristica=coste+heuristica;
        
    }
    
    public Float get_ch() {
    	return costeheuristica;
    }
    
    public Float get_coste() {
    	return coste;
    }
    
    public int get_id() {
    	return id_;
    }
    
    public Vector<Integer> get_camino(){
    	return padres;
    }
  
    //método para la búsqueda
    public void generar_hijos(database data,Vector<nodo> open) {
    	boolean es_padre=false;
    	for(int i=0 ; i < data.get_caminos(id_).size() ; i++){
    		if( data.get_caminos(id_).get(i) != -1) {
    			for(int j=0 ; j < this.padres.size() ; j++){
    				if(this.padres.get(j) == i) {
    					es_padre=true;
    				}
    			}
    			if(!es_padre) {
    				nodo child= new nodo(coste+data.get_caminos(id_).get(i),i,data.get_h(id_));
    				this.hijos.add(child);
	        		child.padres = (Vector<Integer>)this.padres.clone(); 
	        		child.padres.add(this.id_);
	        		open.add(child);
	    		}
	    		es_padre=false;
    		}
    	}
    }
   
}
