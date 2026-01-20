import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

class Lienzo extends JPanel{ //Interfaz
    private Ellipse2D actual;
    private Vertice vOrigen, vDestino;
    private Grafo grafo;
    
    
    //Constructor.
    public Lienzo(Grafo g1){
         
        grafo = g1; //Referencia.
        actual = null;
        vOrigen = vDestino = null;
        setLayout(null);
        setBackground(Color.GRAY);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento){

            Point2D x = evento.getPoint();

            if (grafo.getVertice(x) == null) {
                if (!grafo.enAreaVertice(x)) {
    
                    String nombre = JOptionPane.showInputDialog(null, "Introduce el nombre del nuevo vértice:",
                    "Crear Vértice", JOptionPane.PLAIN_MESSAGE);
                    if (nombre != null && !nombre.trim().isEmpty()) {  
    
                    grafo.agregarVertice(x, nombre.trim());
                    }
                }
            }
            repaint();
            
        }

            public void mousePressed(MouseEvent evento){
                Point2D x = evento.getPoint();
                vOrigen = grafo.getVertice(x);
            }

            public void mouseReleased(MouseEvent evento){
                Point2D x = evento.getPoint();
                if ((vDestino = grafo.getVertice (x)) != null){
                    if( vOrigen != null && vOrigen != vDestino ){
                        try{
                            String sPeso = JOptionPane.showInputDialog("Peso del arista: ");
                            if (sPeso != null && !sPeso.isEmpty()){
                                int peso = Integer.parseInt(sPeso);
                                Arista nArista = new Arista(peso);
                                grafo.agregarAdyacente(vOrigen, vDestino, nArista);
                            }
                        }
                        catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido.");

                        }
                    }                
                }
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evento){
                if (grafo.getVertice(evento.getPoint()) == null){
                    setCursor(Cursor.getDefaultCursor());
                }
                else{
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        grafo.dibujar(g2, this);
    }
    public void limpiar(Grafo g1){
        grafo = g1;
        repaint();
    }   
}

