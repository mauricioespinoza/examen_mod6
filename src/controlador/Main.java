package controlador;
import vista.Inicio;

public class Main {
    
    public static void main(String[] args) {
            
       new Controlador(new Inicio() ).iniciar();
       
    }
}
