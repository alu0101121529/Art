import java.util.*;

public class nodo {

	private Float coste;  // coste_heuristica del nodo + coste_heuristica de los anteriores
	private Float coste_heuristica; // coste_heuristica del nodo + coste_heuristica de los anteriores + heurística
	private Vector<Integer> camino_realizado = new Vector<Integer>(); // Vector de los nodos que se han recorrido previos a este
	public ArrayList<nodo> hijos = new ArrayList<nodo>(); // Lista de hijos del nodo
	private int id_; // Nombre o número de identificación del nodo
	boolean generado = false; // Variable para ver si se ha generado el nodo

	public nodo(Float coste, int ID, Float heuristica) {
		this.coste = coste;
		this.id_ = ID;
		this.coste_heuristica = coste + heuristica;

	}

	// GETTER 

	public Float get_ch() {
		return coste_heuristica;
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
		return camino_realizado;
	}

	// GENERAR HIJOS

	public void generar_hijos(elementos_ficheros elementos, Vector<nodo> lista_analizar) {
		boolean es_padre = false;
		for (int i = 0; i < elementos.get_caminos(id_).size(); i++) {  // Vamos recorriendo el vector de caminos de la base de datos
			if (elementos.get_caminos(id_).get(i) != -1) {   		  // Comprobamos que el coste_heuristica de la arista no es -1 
				for (int j = 0; j < this.camino_realizado.size(); j++) {    // Recorremos el vector de camino_realizado del nodo
					if (this.camino_realizado.get(j) == i) {			      // Miramos si el nodo está en su vector de camino_realizado
						es_padre = true;					     // Ponemos la variable a true
					}
				}
				if (!es_padre) {     							// Si efectivamente el nodo no es padre seguimos con el algoritmo
					nodo child = new nodo(coste + elementos.get_caminos(id_).get(i), i, elementos.get_h(i)); 	// Creamos al primer hijo usando los datos guardados en la base de datos
					this.hijos.add(child);  					// Añadimos al hijo al vector de hijos del padre
					child.camino_realizado = (Vector<Integer>) this.camino_realizado.clone();		// Copiamos el vector de camino_realizado del nodo padre al vector de camino_realizado del hijo
					child.camino_realizado.add(this.id_); 							// Añadimos al padre al vector de camino_realizado del nodo hijo
					if (!child.camino_realizado.contains(child.id_)) {         		// Hacemos una segunda comprobación que no se ha metido un nodo padre de nuevo y ponemos al nodo como generado
						lista_analizar.add(child);									// Añadimos a los hijos a la lista de nodos a analizar
						generado = true;
					}
				}
				es_padre = false;
			}
		}
	}

}
