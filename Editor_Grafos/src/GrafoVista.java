import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*; // Importaciones limpiadas

public class GrafoVista extends JFrame{ //Interrfaz
    private JPanel pBotones, pLetrero;
    private Lienzo lienzo;
    private JButton btMostrarMatriz, btLimpiar, btSalir, btInfo, btBfs, btDfs, btDijkstra, btPrim;
    private TextField txtInfo;
    private JLabel lbEtiqueta;
    private Grafo grafo;

    public GrafoVista(Grafo g1){
        super(" Editor de Grafos ");
        setLayout(new BorderLayout(2,2));

        grafo = g1;
        lienzo = new Lienzo(grafo); 
        grafo.setLienzo(lienzo); 
        
        lienzo.setPreferredSize(new Dimension(600, 500));
        getContentPane().setBackground(Color.DARK_GRAY);
        initComponentes();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void initComponentes(){
        pBotones = new JPanel();
        pLetrero = new JPanel();
        lbEtiqueta = new JLabel("Da click para agregar verices");
        
        btLimpiar = new JButton("Limpiar");
        btLimpiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                grafo.resetColores();
                txtInfo.setText("Colores reseteados.");
            }
        });

                btMostrarMatriz = new JButton("Mostrar matriz");
        btMostrarMatriz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFrame ventanaMatriz = new JFrame("Matriz de Adyacencia.");
                ArrayList<Vertice> vertices = grafo.getVertice();
                String[] cabecera = new String[vertices.size() + 1];
                cabecera[0] = "";
                for (int i = 0; i < vertices.size(); i++ ){
                    cabecera[i + 1] = vertices.get(i).getNombre();
                }

                Arista[][] matriz = grafo.getMatrizAdyacencia();
                Object[][] datos = new Object[vertices.size()][vertices.size()+1];
                
                for(int i = 0; i < vertices.size(); i++){
                    for(int j = 0; j < vertices.size(); j++){
                        datos [i][0] = vertices.get(i).getNombre();
                       Arista arista = matriz[i][j];
                       if (arista != null) {
                           datos[i][j+1] = arista.getPeso();
                       }
                       else{
                           datos[i][j+1] = "0";
                       }
                    }
                }
            
            
            JTable tabla = new JTable(datos, cabecera);
            JScrollPane panelScroll = new JScrollPane(tabla);
            
            ventanaMatriz.add(panelScroll);
            ventanaMatriz.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventanaMatriz.pack();
            ventanaMatriz.setLocationRelativeTo(null);
            ventanaMatriz.setVisible(true);
    
        }});
        
        btSalir = new JButton("Salir");
        btSalir.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
                System.exit(0);
         }});

        btInfo = new JButton("Lista vertices");
        btInfo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            txtInfo.setText(grafo.mostrarVertices());
        }});


        
        btBfs = new JButton("BFS");
        btBfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inicio = JOptionPane.showInputDialog("Vértice inicial para BFS:");
                if (inicio != null && !inicio.trim().isEmpty()) {
                    new Thread(() -> {
                        grafo.bfs(inicio.trim());
                    }).start();
                }
            }
        });
        
        btDfs = new JButton("DFS");
        btDfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inicio = JOptionPane.showInputDialog("Vértice inicial para DFS:");
                if (inicio != null && !inicio.trim().isEmpty()) {
                    new Thread(() -> {
                        grafo.dfs(inicio.trim());
                    }).start();
                }
            }
        });

        btDijkstra = new JButton("Dijkstra");
        btDijkstra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inicio = JOptionPane.showInputDialog("Vértice inicial para Dijkstra:");
                String fin = JOptionPane.showInputDialog("Vértice final para Dijkstra:");
                if (inicio != null && !inicio.trim().isEmpty() && fin != null && !fin.trim().isEmpty()) {
                    new Thread(() -> {
                        grafo.Dijkstra(inicio.trim(), fin.trim());
                    }).start();
                }
            }
        });


        btPrim = new JButton("Prim");
        btPrim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inicio = JOptionPane.showInputDialog("Vértice inicial para Prim:");
                if (inicio != null && !inicio.trim().isEmpty()) {
                    new Thread(() -> {
                        grafo.prim(inicio.trim());
                    }).start();
                }
            }
        });
        
        txtInfo = new TextField("", 20);
        
        pBotones.setBackground(Color.BLACK);
        pLetrero.setBackground(Color.DARK_GRAY);
        txtInfo.setBackground(Color.GRAY); 
        txtInfo.setForeground(Color.white); 
        lbEtiqueta.setForeground(Color.WHITE); 
        
        pBotones.add(btLimpiar);
        pBotones.add(btSalir);
        pBotones.add(btInfo);
        pBotones.add(txtInfo);
        pBotones.add(btMostrarMatriz);

        pBotones.add(btBfs);
        pBotones.add(btDfs);
        pBotones.add(btDijkstra);
        pBotones.add(btPrim);
        
        pLetrero.add(lbEtiqueta);
        add(pLetrero, BorderLayout.NORTH); 
        add(lienzo,BorderLayout.CENTER );
        add(pBotones, BorderLayout.SOUTH);
    }
}