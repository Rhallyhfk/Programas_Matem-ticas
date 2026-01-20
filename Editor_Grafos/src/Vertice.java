import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

public class Vertice {//Interfaz
    private String nombre;
    private Point2D centro;
    private Ellipse2D circulo;
    private Color color;

    private float dv;
    private Vertice pv; 
    private boolean visitado;

    private final static int diametro = 60;
    public final static int radio = diametro /2;
    
    public Vertice(Point2D centro, String nombre){
        double x = centro.getX();
        double y = centro.getY();
        this.circulo = new Ellipse2D.Double(x - radio, y - radio, diametro, diametro);
        this.color = Color.WHITE;
        this.centro = centro;
        this.nombre = nombre;

        this.dv = Float.POSITIVE_INFINITY;
        this.pv = null;
        this.visitado = false;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setColor(Color c){
        color = c;
    }

    public float getDv(){
        return dv;
    }
    
    public Vertice getPv (){
        return pv;
    }

    public boolean getVisitado (){
        return visitado;
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public void setDv(float dv) {
        this.dv = dv;
    }

    public void setPv(Vertice pv) {
        this.pv = pv;
    }

    public Ellipse2D getCirculo(){
        return circulo;
    }

    public void setCirculo(Ellipse2D circulo){
        this.circulo = circulo;
    }

    public Point2D getCentro(){
        return centro;
    }

    public void setCentro(Point2D centro){
        this.centro = centro;
    }

    public void dibujar(Graphics2D g2){
        g2.setPaint(color);
        g2.fill(circulo);
        g2.draw(circulo);
        
        Font fuenteOriginal = g2.getFont();
        Font fuenteNegrita = new Font(fuenteOriginal.getName(), Font.BOLD, 20);
        g2.setFont(fuenteNegrita);
        g2.setPaint(Color.BLACK);
        FontMetrics fm = g2.getFontMetrics();
        int posX = (int) (centro.getX() - fm.stringWidth(nombre) / 2);
        int posY = (int) (centro.getY() - fm.getHeight() / 2 + fm.getAscent());
        g2.drawString(nombre, posX, posY);
        g2.setPaint(Color.BLACK); 
    }
}
