import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class AStar {
	private Vector<nodo> OpenList = new Vector<nodo>();
	private database info;
	private int Generados;
	private int Analizados;

	public AStar(String file_caminos, String file_h) {
		info = new database(file_caminos, file_h);
	}

	public void Search(int inicio, int fin) {
		nodo inicial = new nodo(new Float(0), inicio, info.get_h(inicio));
		OpenList.add(inicial);
		int LastID = inicio;
		nodo menor = inicial;
		Analizados = 0;
		Generados = 1;
		if (inicio == fin) {
			Analizados = 1;
		}
		while ((LastID != fin) && (OpenList.size() != 0)) {
			int indice_menor = 0;
			for (int i = 1; i < OpenList.size(); i++) {
				if (OpenList.get(i).get_ch() < OpenList.get(indice_menor).get_ch()) {
					indice_menor = i;
				} else {
					if (Float.compare((OpenList.get(i).get_ch()), (OpenList.get(indice_menor).get_ch())) == 0 ? true
							: false) {
						if (info.get_h(OpenList.get(i).get_id()) < info.get_h(OpenList.get(indice_menor).get_id())) {
							indice_menor = i;
						}
					}
				}
			}

			menor = OpenList.get(indice_menor);

			LastID = menor.get_id();
			if (menor.get_id() == fin) {
				Analizados++;
				OpenList.remove(indice_menor);
			} else {
				Analizados++;
				int old_size = OpenList.size();
				menor.generar_hijos(info, OpenList);
				Generados += OpenList.size() - old_size;
				OpenList.remove(indice_menor);

			}
		}

		System.out.println("Nodos generados: " + Generados);
		System.out.println("Nodos Analizados: " + Analizados);
		System.out.println("Coste Final: " + menor.get_coste());
		System.out.println("Camino:");

		for (int i = 0; i < menor.get_camino().size(); i++) {
			System.out.print((menor.get_camino().get(i) + 1) + " -> ");
		}
		System.out.println(menor.get_id() + 1);

		File dir = new File("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/");
		dir.mkdirs();
		File file = new File(dir, "resultados.txt");

		try {
			PrintWriter archivo = new PrintWriter(new FileWriter(file,true));
			archivo.write('\n');
			archivo.write("Fichero de caminos: ");
			archivo.write(info.get_fichero_c());
			archivo.write('\t');
			archivo.write("Fichero de Heurísticas: ");
			archivo.write(info.get_fichero_h());
			archivo.write('\n');
			archivo.write(String.format("%20s %20s %20s %20s %20s %20s %20s \r\n","Nodos", "Aristas","Nodo inicial", "Nodo final", "Generados", "Analizados", "Coste"));
			archivo.write(String.format("%20s %20s %20s %20s %20s %20s %20s \r\n", +info.get_nodos(),+info.get_aristas(),+(inicio+1),+(fin+1),+Generados, +Analizados, +menor.get_coste()));
			archivo.write("camino: ");
			for (int i = 0; i < menor.get_camino().size(); i++) {
				archivo.write((menor.get_camino().get(i) + 1 + " -> "));
			}
			String id = new StringBuilder().append(menor.get_id1()).toString();
			archivo.write(id);
			archivo.write('\n');
			if (null != archivo) {
				archivo.flush();
				archivo.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		System.out.println("Escriba el fichero de caminos: ");
		String f_camino = keyboard.next();
		System.out.println("Escriba el fichero de heurísticas: ");
		String f_heuristicas = keyboard.next();
		AStar Busqueda = new AStar("/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/"+f_camino,"/mnt/c/Users/Guillex/Desktop/Clases/Art/ia/A_Star/grafos/"+ f_heuristicas);
		System.out.println("Escriba el nodo inicial: ");
		int nodo_ini = keyboard.nextInt();
		--nodo_ini;
		System.out.println("Escriba el nodo final: ");
		int nodo_fin = keyboard.nextInt();
		--nodo_fin;
		double inicio = System.currentTimeMillis();
		Busqueda.Search(nodo_ini, nodo_fin);
		keyboard.close();
		double fin = System.currentTimeMillis();
		double tiempo = ((fin - inicio) / 1000);
		System.out.println("Tiempo de ejecución: " + tiempo);
	}
}
