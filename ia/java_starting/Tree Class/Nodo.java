import java.util.*;

public class Nodo{
    public int coste;
    public int costeheuristica;
    public Vector<Integer> padres = new Vector<Integer>();
    public Nodo padre;
    public ArrayList<Nodo> hijos = new ArrayList<Nodo>();
    public int id_;

    public Nodo(int coste, int ID){
        this.coste= coste;
        this.id_=ID;
        this.costeheuristica=0;
    }

    public void generar_hijos(int heuristica, Nodo child) {
        this.hijos.add(child);
        child.padre = this;
        child.padres.add(this.id_);
        child.coste = this.coste+child.coste;
        child.costeheuristica = child.coste+heuristica;  
    }

    public static void main(String[] args) {
        Nodo padre = new Nodo(5,3);
        Nodo hijo = new Nodo(5,1);
        padre.generar_hijos(10,hijo);
    }
        
}