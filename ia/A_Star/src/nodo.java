import java.util.*;

public class nodo {

	private Float coste;  // coste del nodo + coste de los anteriores
	private Float costeheuristica; // coste del nodo + coste de los anteriores + heurística
	private Vector<Integer> padres = new Vector<Integer>(); // Vector de los nodos que se han recorrido previos a este
	public ArrayList<nodo> hijos = new ArrayList<nodo>(); // Lista de hijos del nodo
	private int id_; // Nombre o número de identificación del nodo
	boolean generado = false; // Variable para ver si se ha generado el nodo

	public nodo(Float coste, int ID, Float heuristica) {
		this.coste = coste;
		this.id_ = ID;
		this.costeheuristica = coste + heuristica;

	}

	// GETTER 

	public Float get_ch() {
		return costeheuristica;
	}

	public Float get_coste() {
		return coste;
	}

	public int get_id() {
		return id_;
	}
	public int get_id1(){
		return (id_+1);
	}

	public Vector<Integer> get_camino() {
		return padres;
	}

	// GENERAR HIJOS

	public void generar_hijos(database data, Vector<nodo> open) {
		boolean es_padre = false;
		for (int i = 0; i < data.get_caminos(id_).size(); i++) {  // Vamos recorriendo el vector de caminos de la base de datos
			if (data.get_caminos(id_).get(i) != -1) {   		  // Comprobamos que el coste de la arista no es -1 
				for (int j = 0; j < this.padres.size(); j++) {    // Recorremos el vector de padres del nodo
					if (this.padres.get(j) == i) {			      // Miramos si el nodo está en su vector de padres
						es_padre = true;					     // Ponemos la variable a true
					}
				}
				if (!es_padre) {     							// Si efectivamente el nodo no es padre seguimos con el algoritmo
					nodo child = new nodo(coste + data.get_caminos(id_).get(i), i, data.get_h(i)); 		// Creamos al primer hijo usando los datos guardados en la base de datos
					this.hijos.add(child);  					// Añadimos al hijo al vector de hijos del padre
					child.padres = (Vector<Integer>) this.padres.clone();		// Copiamos el vector de padres del nodo padre al vector de padres del hijo
					child.padres.add(this.id_); 							// Añadimos al padre al vector de padres del nodo hijo
					if (!child.padres.contains(child.id_)) {         		// Hacemos una segunda comprobación que no se ha metido un nodo padre de nuevo y ponemos al nodo como generado
						open.add(child);
						generado = true;
					}
				}
				es_padre = false;
			}
		}
	}

}
