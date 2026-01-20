import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

class Grafo { 
    private int orden;
    private ArrayList <Vertice> vertices;
    private Arista [][] matrizAdyacencia;

    private Lienzo lienzo; 
    private static final long DELAY_MS = 500; 
    
    public static final Color COLOR_INICIAL = Color.WHITE;
    public static final Color COLOR_NO_VISITADO = Color.RED;
    public static final Color COLOR_VISITADO = Color.BLUE;
    public static final Color COLOR_ARISTA_DEFAULT = Color.BLACK;
    public static final Color COLOR_ARISTA_VISITADA = Color.BLUE;
    public static final Color COLOR_RUTA_FINAL = Color.GREEN; 

    public Grafo(int orden){
        this.orden = orden;
        vertices = new ArrayList<>();
        matrizAdyacencia = new Arista[orden][orden];
        this.lienzo = null; 
    }
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public Grafo() {
        this(50);
    }
    
    public void agregarVertice(Point2D p, String nombre) {
        if (vertices.size() < orden) {
            Vertice v = new Vertice(p, nombre);
            vertices.add(v);
        } else {
            JOptionPane.showMessageDialog(null, "Se ha alcanzado el número máximo de vértices.");
        }
    }
    public Vertice getVertice(Point2D p){
        for(Vertice v : vertices){
            if(v.getCirculo().contains(p)) 
            return v;
        }
        return null;
    }
    
    private int getIndex(Point2D p){
        for (int i = 0; i < vertices.size(); i++){
            if((vertices.get(i)).getCirculo().contains(p)){
                return i;
            }
        }
        return -1;
    }

    public void agregarAdyacente(Vertice vOrigen, Vertice vDestino, Arista arista){
        int indiceOrigen = vertices.indexOf(vOrigen);
        int indiceDestino = vertices.indexOf(vDestino);

        if (indiceOrigen != -1 && indiceDestino != -1) {
            matrizAdyacencia[indiceOrigen][indiceDestino ] = arista;
            matrizAdyacencia [indiceDestino][indiceOrigen] = arista;
        
        }

    }

    public String mostrarVertices(){
        String cad = " ";
        for(Vertice v : vertices){
            cad += v.getNombre ()+ ", ";
        }
        return cad;
    }

    public void dibujar(Graphics2D g2, JPanel l){
        for(int i = 0; i < vertices.size(); i++){
            for(int j = i + 1; j < vertices.size(); j++){ 
                if (matrizAdyacencia[i][j] != null){
                    Arista arista = matrizAdyacencia[i][j];
                    Point2D p1 = vertices.get(i).getCentro();
                    Point2D p2 = vertices.get(j).getCentro();
                    
                    g2.setColor(arista.getColor()); 
                    g2.setStroke(new BasicStroke(10)); //Aquí se puede modificar el
                    g2.draw(new Line2D.Double(p1, p2));
                    
                    String pesoStr = String.valueOf(arista.getPeso());
                    float midX = (float) (p1.getX() + p2.getX()) / 2;
                    float midY = (float) (p1.getY() + p2.getY()) / 2;
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));//Aquí se cambia el tamaño del texto del peso..
                    g2.drawString(pesoStr, midX, midY);
                }
            }
        }
        
        for(Vertice v : vertices){
            v.dibujar(g2);
        }
    }

    public Arista[][] getMatrizAdyacencia(){
        return matrizAdyacencia;
    }

    public ArrayList<Vertice> getVertice () {
        return vertices;
    }
    
    public boolean enAreaVertice(Point2D nuevaPosicion){
        double nradio = Vertice.radio;
        for (Vertice v : vertices){
            double distanciaX = nuevaPosicion.getX() - v.getCentro().getX();
            double distanciaY = nuevaPosicion.getY() - v.getCentro().getY();
            //Sí un dia me pasa algo, diaganle a pitagoras que gracias por todo.
            double distanciaCentro = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
            //si la distancia entre los centros es menor a la suma de sus radios los verices estan encimados
            if (distanciaCentro < (nradio + Vertice.radio)){  
                return true; //Estan encimados                                       
            }
        }
        return false;
    }

    private void pausarYRepintar() {
        try {
            Thread.sleep(DELAY_MS); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        SwingUtilities.invokeLater(() -> {
            if (lienzo != null) {
                lienzo.repaint();
            }
        });
    }

    public void prepararAlgoritmo() {
        for (Vertice v : vertices) {
            v.setColor(COLOR_NO_VISITADO); 
            v.setDv(Float.POSITIVE_INFINITY);
            v.setPv(null);
            v.setVisitado(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (matrizAdyacencia[i][j] != null) {
                    matrizAdyacencia[i][j].setColor(COLOR_ARISTA_DEFAULT);
                }
            }
        }
        pausarYRepintar(); 
    }
    
    public void resetColores() {
         for (Vertice v : vertices) {
            v.setColor(COLOR_INICIAL);
         }
         for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (matrizAdyacencia[i][j] != null) {
                    matrizAdyacencia[i][j].setColor(COLOR_ARISTA_DEFAULT);
                }
            }
        }
        pausarYRepintar();
    }

    private void pintarVertice(Vertice v, Color c) {
        if (v != null) {
            v.setColor(c);
            pausarYRepintar();
        }
    }
    
    private void pintarArista(int u, int v, Color c) {
        if (matrizAdyacencia[u][v] != null) {
            matrizAdyacencia[u][v].setColor(c);
            matrizAdyacencia[v][u].setColor(c);
            pausarYRepintar();
        }
    }
    
    public int getIndicePorNombre(String nombre) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }
    
    public Vertice getVerticePorNombre(String nombre) {
        for (Vertice v : vertices) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }

    public void bfs(String inicio) {
        prepararAlgoritmo(); 
        int numVerticesGUI = vertices.size();
        
        int indiceInicio = getIndicePorNombre(inicio); 
        if (indiceInicio == -1) {
            System.out.println("Vértice inicial '" + inicio + "' no encontrado.");
            return;
        }

        boolean[] visitadosConsola = new boolean[numVerticesGUI]; 
        ArrayList<Integer> cola = new ArrayList<>();

        visitadosConsola[indiceInicio] = true;
        cola.add(indiceInicio);
        
        pintarVertice(vertices.get(indiceInicio), COLOR_VISITADO); // Pinta el inicio (AZUL)

        while (!cola.isEmpty()) {
            int indiceActual = cola.remove(0);

            for (int i = 0; i < numVerticesGUI; i++) {
                if (matrizAdyacencia[indiceActual][i] != null && !visitadosConsola[i]) {
                    visitadosConsola[i] = true;
                    cola.add(i);
                    
                    pintarArista(indiceActual, i, COLOR_ARISTA_VISITADA);
                    pintarVertice(vertices.get(i), COLOR_VISITADO);
                }
            }
        }
    }


    public void dfs(String inicio) {
        prepararAlgoritmo(); // Pone todo en ROJO
        int numVerticesGUI = vertices.size();
        
        int indiceInicio = getIndicePorNombre(inicio);
        if (indiceInicio == -1) {
            System.out.println("Vértice inicial '" + inicio + "' no encontrado.");
            return;
        }
        boolean[] visitadosConsola = new boolean[numVerticesGUI];
        
        dfsRecursivo(indiceInicio, visitadosConsola, -1);
    }

    private void dfsRecursivo(int indiceActual, boolean[] visitadosConsola, int indicePadre) {
        visitadosConsola[indiceActual] = true;
        
        pintarVertice(vertices.get(indiceActual), COLOR_VISITADO); // Pinta el actual (AZUL)
        if (indicePadre != -1) { // Si no es el nodo raíz
            pintarArista(indicePadre, indiceActual, COLOR_ARISTA_VISITADA);
        }
                
        int numVerticesGUI = vertices.size();
        for (int i = 0; i < numVerticesGUI; i++) {
            if (matrizAdyacencia[indiceActual][i] != null && !visitadosConsola[i]) {
                dfsRecursivo(i, visitadosConsola, indiceActual); // Pasa el actual como padre
            }
        }
    }


    public void Dijkstra(String vInicio, String vFinal) {
        prepararAlgoritmo(); // Pone todo en ROJO
        int numVerticesGUI = vertices.size();

        Vertice inicio = getVerticePorNombre(vInicio);
        if (inicio == null) {
            System.out.println("No se encontro el vertice inicio");
            return;
        }

        inicio.setDv(0);
        pintarVertice(inicio, COLOR_VISITADO); // Pinta el inicio

        ArrayList<Vertice> porVisitar = new ArrayList<>(vertices);
        while (!porVisitar.isEmpty()) {
            Vertice actual = null;
            float distanciam = Float.POSITIVE_INFINITY;

            for (Vertice v : porVisitar) {
                if (v.getDv() < distanciam) {
                    distanciam = v.getDv();
                    actual = v;
                }
            }
            
            if (actual == null) break;
            
            actual.setVisitado(true);
            porVisitar.remove(actual);
            
            pintarVertice(actual, COLOR_VISITADO); // Pinta el actual (AZUL)
            if (actual.getPv() != null) { // Pinta la arista por la que llegó
                pintarArista(vertices.indexOf(actual.getPv()), vertices.indexOf(actual), COLOR_ARISTA_VISITADA);
            }

            if (actual.getNombre().equals(vFinal)) break;

            int iActual = vertices.indexOf(actual); 
            for (int i = 0; i < numVerticesGUI; i++) {
                if (matrizAdyacencia[iActual][i] != null) {
                    Vertice vecino = vertices.get(i);
                    if (!vecino.getVisitado()) {
                        float distanciam_2 = actual.getDv() + matrizAdyacencia[iActual][i].getPeso();
                        if (distanciam_2 < vecino.getDv()) {
                            vecino.setDv(distanciam_2);
                            vecino.setPv(actual);
                        }
                    }
                }
            }
        }

        Vertice fin = getVerticePorNombre(vFinal);
        if (fin != null && fin.getDv() != Float.POSITIVE_INFINITY) {
            Vertice paso = fin;
            while (paso != null && paso.getPv() != null) {
                pintarVertice(paso, COLOR_RUTA_FINAL);
                pintarArista(vertices.indexOf(paso), vertices.indexOf(paso.getPv()), COLOR_RUTA_FINAL);
                paso = paso.getPv();
            }
            if(paso != null) pintarVertice(paso, COLOR_RUTA_FINAL); // Pinta el inicio
        } else {
            System.out.println("Error, no se encontró ruta");
        }
    }
    
    public void prim(String vInicio) {
        prepararAlgoritmo(); // Pone todo en ROJO
        int numVerticesGUI = vertices.size();

        Vertice inicio = getVerticePorNombre(vInicio);
        if (inicio == null) {
            System.out.println("No se encontro el vertice");
            return;
        }

        inicio.setDv(0); 

        ArrayList<Vertice> porVisitar = new ArrayList<>(vertices);
        
        while (!porVisitar.isEmpty()) {
            Vertice actual = null;
            float costom = Float.POSITIVE_INFINITY;
            
            for (Vertice v : porVisitar) {
                if (!v.getVisitado() && v.getDv() < costom) {
                    costom = v.getDv();
                    actual = v;
                }
            }

            if (actual == null) break;

            actual.setVisitado(true);
            porVisitar.remove(actual);

            pintarVertice(actual, COLOR_VISITADO); // Pinta el vértice (AZUL)
            if (actual.getPv() != null) {
                // Pinta la arista que se agrega al AEM
                pintarArista(vertices.indexOf(actual), vertices.indexOf(actual.getPv()), COLOR_ARISTA_VISITADA);
            }

            int iActual = vertices.indexOf(actual);
            for (int i = 0; i < numVerticesGUI; i++) {
                Vertice vecino = vertices.get(i);
                if (matrizAdyacencia[iActual][i] != null && !vecino.getVisitado()) {
                    int pArista = matrizAdyacencia[iActual][i].getPeso();
                    if (pArista < vecino.getDv()) {
                        vecino.setDv(pArista);
                        vecino.setPv(actual);
                    }
                }
            }
        }
    }
}

