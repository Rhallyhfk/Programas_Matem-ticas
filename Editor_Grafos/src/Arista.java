import java.awt.Color; 

class Arista{ //Interfaz
    public int peso;
    private Color color; 

    public Arista(int peso){
        this.peso = peso;
        this.color = Color.BLACK; // Color por defecto
    }

    public int getPeso() {
        return peso;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}