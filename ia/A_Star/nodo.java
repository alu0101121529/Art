import java.util.*;

public class nodo {

    public Float coste;
    public Float costeheuristica;
    public Vector<Integer> padres = new Vector<Integer>();
    private nodo padre;
    public ArrayList<nodo> hijos = new ArrayList<nodo>();
    private int id_;

    public nodo(Float coste, int ID, Float heuristica){
        this.coste= coste;
        this.id_=ID;
        this.costeheuristica=coste+heuristica;
        
	}
	
	public nodo get_padre(){
		return this.padre;
	}
	public void set_padre(nodo node){
		this.padre = node;
	}

    public void generar_hijos(database data) {
    	boolean es_padre=false;
    	for(int i=0 ; i < data.get_caminos(id_-1).size() ; i++){
    		if( data.get_caminos(id_-1).get(i) != -1) {
    			for(int j=0 ; j < this.padres.size() ; j++){
    				if(this.padres.get(j) == i+1) {
    					es_padre=true;
    				}
    			}
    			if(!es_padre) {
    				nodo child= new nodo(coste+data.get_caminos(id_-1).get(i),i+1,data.get_h(id_-1));
    				this.hijos.add(child);
    				child.set_padre(this);;
	        		child.padres = this.padres; 
	        		child.padres.add(this.id_);
	    		}
	    		es_padre=false;
    		}
    	}
    }

    public static void main(String[] args) {
    	nodo padre;
    	database data = new database("caminos.txt","heuristicas.txt");
    	
    	for(int j=1;j<6;j++) {
    		System.out.println("hijos de "+j);
	        padre = new nodo(new Float(0),j,data.get_h(3));
	        padre.generar_hijos(data);
	        for(int i=0;i<padre.hijos.size();i++) {
	        	System.out.println(padre.hijos.get(i).id_);
	        }
    	}
    }
        
}
