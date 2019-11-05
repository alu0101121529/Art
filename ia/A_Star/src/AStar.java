import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class AStar {
	private Vector<nodo> OpenList = new Vector<nodo>(); // Lista de nodos que se van a estudiar en cada iteración
	private database info; // Base de datos donde se guardarán la información conseguida en los ficheros
	private int Generados; // Número de nodos generados
	private int Analizados; // Número de nodos analizados

	public AStar(String file_caminos, String file_h) {
		info = new database(file_caminos, file_h); // En el constructor solo llenamos la base de datos
	}

	public void Search(int inicio, int fin) {
		nodo inicial = new nodo(new Float(0), inicio, info.get_h(inicio)); // Creamos el nodo inicial con coste 0, el id que le pasamos al método y la heurística asociada a esa ID
		OpenList.add(inicial); // Lo añadimos a la lista para estudiarlo
		int LastID = inicio; // Esta variable se usa para ver cual fue la última ID estudiada
		nodo menor = inicial; // En este nodo se guardará el menor nodo con costeheurística, en la primera iteración es el inicial
		Analizados = 0; // Inicializamos el número de nodos analizados
		Generados = 1; // Inicializamos el número de nodos generados, es 1 ya que ya generamos el nodo inicial
		if (inicio == fin) {
			Analizados = 1; // Si el nodo inicial y el final es el mismo ponemos que solo se ha analizado 1 nodo
		}
		while ((LastID != fin) && (OpenList.size() != 0)) { // Empezamos el algoritmo siempre y cuando la lista no esté vacia o el nodo inicial y final no coincidan
			int indice_menor = 0; // Usamos esta variable para ver el índice dentro de los vectores del nodo menor
			for (int i = 1; i < OpenList.size(); i++) { // Empezamos a recorrer la lista
				if (OpenList.get(i).get_ch() < OpenList.get(indice_menor).get_ch()) { // Miramos si el nodo i de la lista tiene un costeheurística menor que el nodo menor
					indice_menor = i; // Ponemos el nodo i como el nodo menor si la línea anterior se cumple
				} else {
					if (Float.compare((OpenList.get(i).get_ch()), (OpenList.get(indice_menor).get_ch())) == 0 ? true: false) { // Miramos si los costeheurísticas de ambos nodos son iguales
						if (info.get_h(OpenList.get(i).get_id()) < info.get_h(OpenList.get(indice_menor).get_id())) { // Miramos que nodo tiene la heurística menor
							indice_menor = i; // Cogemos el nodo con la menor heurística
						}
					}
				}
			}

			menor = OpenList.get(indice_menor); // Cogemos el nodo menor para la siguiente iteración
			LastID = menor.get_id();  // Actualizamos el LastID
			if (menor.get_id() == fin) { // Miramos si el nodo menor es el nodo final
				Analizados++; // Subimos el contador de analizados
				OpenList.remove(indice_menor); // Lo borramos de la lista para analizar
			} else { // No es el nodo final
				Analizados++; // Subimos el contador de nodos analizados
				int old_size = OpenList.size(); // Cogemos el tamaño de la lista actualmente
				menor.generar_hijos(info, OpenList); // Generamos a los hijos del nodo menor, por ende cambiamos el tamaño de la lista de nodos a analizar
				Generados += OpenList.size() - old_size; // Calculamos cuantos nodos se han generado en esta iteración
				OpenList.remove(indice_menor); // Borramos de la lista el nodo que acabamos de analizar

			}
		}

		// IMPRESIÓN POR PANTALLA
		
		// Impriminos el número de nodos generados
		System.out.println("Nodos generados: " + Generados);
		// Imprimimos el número de nodos analizados
		System.out.println("Nodos Analizados: " + Analizados);
		// Imprimimos el coste total del camino sin el costes de las heurísticas
		System.out.println("Coste Final: " + menor.get_coste());
		// Por último imprimimos el camino
		System.out.println("Camino:");

		for (int i = 0; i < menor.get_camino().size(); i++) {
			System.out.print((menor.get_camino().get(i) + 1) + " -> ");
		}
		System.out.println(menor.get_id() + 1);

		// IMPRESIÓN AL FICHERO

		// Creamos el fichero donde se almacenarán todos los resultados

		File dir = new File("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/"); // Hay que tener cuidaod con esta línea ya que esta es la ruta en mi PC y habría que cambiarla para cada PC
		dir.mkdirs();
		File file = new File(dir, "resultados.txt");

		// Empezamos la escritura en el fichero
		try {
			PrintWriter archivo = new PrintWriter(new FileWriter(file,true));
			// Imprimimos un demilitador entre ejecución y ejecución
			archivo.write('\n');
			archivo.write("---------------------------------------------------------------------------------------------------------------------------------");
			archivo.write('\n');
			// Empezamos la impresión del nombre del fichero de caminos
			archivo.write("Fichero de caminos: ");
			archivo.write(info.get_fichero_c());
			archivo.write('\t');
			// Ahora viene la impresión del fichero de heurísticas
			archivo.write("Fichero de Heurísticas: ");
			archivo.write(info.get_fichero_h());
			archivo.write('\n');
			// Empezamos a imprimir la tabla con los valores del número de nodos, número de aristas, nodo inicial, nodo final , nodos generados y analizados y el coste del camino
			archivo.write(String.format("%20s %20s %20s %20s %20s %20s %20s \r\n","Nodos", "Aristas","Nodo inicial", "Nodo final", "Generados", "Analizados", "Coste"));
			archivo.write(String.format("%20s %20s %20s %20s %20s %20s %20s \r\n", +info.get_nodos(),+info.get_aristas(),+(inicio+1),+(fin+1),+Generados, +Analizados, +menor.get_coste()));
			// Imprimimos el camino al fichero debajo de la tabla
			archivo.write("camino: ");
			for (int i = 0; i < menor.get_camino().size(); i++) {
				archivo.write((menor.get_camino().get(i) + 1 + " -> "));
			}
			String id = new StringBuilder().append(menor.get_id1()).toString();
			archivo.write(id);
			archivo.write('\n');
			// Cerramos el archivo
			if (null != archivo) {
				archivo.flush();
				archivo.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	// Main
	public static void main(String[] args) {

		// Pedimos los nombres de ambos ficheros por pantalla
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Escriba el fichero de caminos: ");
		String f_camino = keyboard.next();
		System.out.println("Escriba el fichero de heurísticas: ");
		String f_heuristicas = keyboard.next();
		// Empezamos la búsqueda Astar, hay que tener cuidado ya que le paso las rutas de mi PC y esto habría que cambiarlo para cada PC
		AStar Busqueda = new AStar("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/"+f_camino,"/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/"+ f_heuristicas);
		// Pedimos el nodo inicial y el nodo final
		System.out.println("Escriba el nodo inicial: ");
		int nodo_ini = keyboard.nextInt();
		--nodo_ini;
		System.out.println("Escriba el nodo final: ");
		int nodo_fin = keyboard.nextInt();
		--nodo_fin;
		// Empezamos a calcular el tiempo de ejecución
		double inicio = System.currentTimeMillis();
		// Realizamos la búsqueda 
		Busqueda.Search(nodo_ini, nodo_fin);
		keyboard.close();
		double fin = System.currentTimeMillis();
		// Calculamos cuanto ha tardado la búsqueda en realizarse
		double tiempo = ((fin - inicio) / 1000);
		System.out.println("Tiempo de ejecución: " + tiempo);
	}
}
