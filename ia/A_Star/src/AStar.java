import java.util.*;
import java.util.Scanner;

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
					if (Float.compare((OpenList.get(i).get_ch()), (OpenList.get(indice_menor).get_ch())) == 0 ? true : false) {
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
	}

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		System.out.println("Escriba el fichero de caminos: ");
		String f_camino = keyboard.next();
		System.out.println("Escriba el fichero de heurísticas: ");
		String f_heuristicas = keyboard.next();
		AStar Busqueda = new AStar(f_camino, f_heuristicas);
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
