import java.util.*;
import java.io.*;

public class database {
	private Vector<Vector<Float>> caminos = new Vector<Vector<Float>>(); // Si el camino no existe el coste es -1
	private Vector<Float> heuristicas = new Vector<Float>();
	private Integer n_nodos;
	private int aristas;
	private String fichero_c = new String();
	private String fichero_h = new String();

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
			fichero_h = file_h;
			fichero_h = fichero_h.replace("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/","");
			fichero_c = file_caminos;
			fichero_c = fichero_c.replace("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/","");
			archivo = new File(file_caminos);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			//Integer n_nodos;
			Float coste_t;
			Float coste_h;
			linea = br.readLine();
			if (linea != null) {
				n_nodos = new Integer(linea);

				for (int i = 0; i < n_nodos; i++) {
					caminos.add(new Vector<Float>());
				}

				for (int i = 0; i < (n_nodos - 1); i++) {
					caminos.get(i).add(new Float(-1));
					for (int j = (i + 1); j < n_nodos; j++) {
						if ((linea = br.readLine()) != null) {
							coste_t = new Float(linea);
							caminos.get(i).add(coste_t);
							caminos.get(j).add(coste_t);
							if (!(Float.compare(coste_t, -1) == 0))
								aristas++;
						} else {
							br.close();
							fr.close();
							throw new Exception("Faltan caminos en el fichero");
						}
					}
				}
				caminos.get(n_nodos - 1).add(new Float(-1));

				br.close();
				fr.close();
				archivo = new File(file_h);
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);

				for (int i = 0; i < n_nodos; i++) {
					if ((linea = br.readLine()) != null) {
						coste_h = new Float(linea);
						heuristicas.add(coste_h);

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