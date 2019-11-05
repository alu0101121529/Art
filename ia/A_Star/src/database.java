import java.util.*;
import java.io.*;

public class database {
	private Vector<Vector<Float>> caminos = new Vector<Vector<Float>>(); // Si el camino no existe el coste es -1
	private Vector<Float> heuristicas = new Vector<Float>();  // Vector que contiene todas las heurísticas
	private Integer n_nodos;  // Número de nodos del grafo
	private int aristas;  // Número de aristas del grafo
	private String fichero_c = new String(); // Nombre del fichero de caminos
	private String fichero_h = new String(); // Nombre del fichero de heurísticas

	// GETTERS

	public Float get_h(int index) {
		return this.heuristicas.get(index);
	}

	public Vector<Float> get_caminos(int index) {
		return this.caminos.get(index);
	}

	public int get_nodos(){
		return n_nodos;
	}

	public int get_aristas(){
		return aristas;
	}
	
	public String get_fichero_h(){
		return fichero_h;
	}
	public String get_fichero_c(){
		return fichero_c;
	}

	public database(String file_caminos, String file_h) {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			fichero_h = file_h; // Ponemos el nombre del fichero de heurísticas en el atributo que creamos
			fichero_h = fichero_h.replace("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/",""); //Borramos la ruta del fichero (Hay que tener cuidado ya que es la ruta de mi PC y para que funcione en otro hay que comentar esta línea o cambiar la ruta a mano)
			fichero_c = file_caminos; // Ponemos el nombre del fichero de caminos en el atributo que creamos
			fichero_c = fichero_c.replace("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/",""); //Borramos la ruta del fichero (Hay que tener cuidado ya que es la ruta de mi PC y para que funcione en otro hay que comentar esta línea o cambiar la ruta a mano)
			archivo = new File(file_caminos); 
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			Float coste_t;
			Float coste_h;
			linea = br.readLine();
			if (linea != null) {
				n_nodos = new Integer(linea); //Ponemos el número de nodos en n_nodos

				for (int i = 0; i < n_nodos; i++) {
					caminos.add(new Vector<Float>()); //Ponemos en el primer vector tantos vectors como número de nodos haya
				}

				for (int i = 0; i < (n_nodos - 1); i++) {
					caminos.get(i).add(new Float(-1));
					for (int j = (i + 1); j < n_nodos; j++) {
						if ((linea = br.readLine()) != null) {
							coste_t = new Float(linea);  //Empezamos a coger los valores de las aristas
							caminos.get(i).add(coste_t); // Empezamos a meter los valores en los vectores
							caminos.get(j).add(coste_t); // Metemos el coste del camino de vuelta, o sea si arriba metimos el valor del nodo 1 al 2 este será el valor del nodo 2 al 1
							if (!(Float.compare(coste_t, -1) == 0))
								aristas++; // Si el coste no es -1 subimos el número de aristas
						} else {
							br.close(); // Cerramos el fichero
							fr.close();
							throw new Exception("Faltan caminos en el fichero");
						}
					}
				}
				// Ahora hacemos lo mismo pero con las heurísticas
				caminos.get(n_nodos - 1).add(new Float(-1));

				br.close();
				fr.close();
				archivo = new File(file_h);
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);

				for (int i = 0; i < n_nodos; i++) {
					if ((linea = br.readLine()) != null) {
						coste_h = new Float(linea); // Cogemos los valores de las heurísticas
						heuristicas.add(coste_h);  // Los metemos en el vector de las heurísticas

					} else {
						br.close();
						fr.close();
						throw new Exception("Faltan heuristicas en el fichero");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
}