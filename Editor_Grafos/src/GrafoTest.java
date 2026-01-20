import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class GrafoTest {//Interfaz

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
        Grafo grafo = new Grafo();
        GrafoVista frame = new GrafoVista(grafo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        });
    }
}
/*
javac -d bin -cp "lib/flatlaf-3.6.jar" src/*.java
java -cp "bin;lib/flatlaf-3.6.jar" GrafoTest 
*/


