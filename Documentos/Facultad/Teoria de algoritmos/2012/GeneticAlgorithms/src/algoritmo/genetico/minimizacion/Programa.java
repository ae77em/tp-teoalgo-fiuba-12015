package algoritmo.genetico.minimizacion;

import java.io.FileWriter;
import java.util.ArrayList;

import algoritmo.genetico.AlgoritmoGenetico;
import algoritmo.genetico.FuncionDeAptitud;
import algoritmo.genetico.FuncionDeCorte;
import algoritmo.genetico.Generacion;
import algoritmo.genetico.Individuo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Programa {

    public static void main(String[] args) throws Exception {
        // Creamos el archivo para el reporte
        FileWriter fw = new FileWriter("Reporte.csv",true);
        String renglon = "";
        // Creamos la funcion cuadratica X^2 + 4X - 4
        // Hay que representarla en forma computacional
        FuncionDeAptitud funcionAMinimizar1 = new FuncionCuadratica(1, 4, -4, -10, 20);
        // Usamos el mismo string
        FuncionDeAptitud funcionAMinimizar2 = new FuncionCuadratica("x^2 + 4x - 4", -10, 20);
        
        // Creamos la funcion de corte, por cantidad de iteraciones
        FuncionDeCorte cortePorIteraciones = new CortePorIteraciones(50);

        // Creamos el algoritmo genetico y aplicamos la evolucion
        AlgoritmoGenetico minimizarFuncion = new AlgoritmoGenetico(100, 0.001, 0.25, 0.5, funcionAMinimizar1, cortePorIteraciones);
        minimizarFuncion.iniciar();

        //Obtenemos la historia de las generaciones
        ArrayList<Generacion> historiaGeneraciones = minimizarFuncion.obtenerHistoriaGeneraciones();

        // Iteramos sobre las generaciones e imprimimos la aptitud del individuo mas apto
        renglon = "Nº Generacion | Aptitud total | Promedio aptitud | Individuo mas apto | Individuo menos apto |\n";
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        fw.append("------------ Corrida a las "+hourdateFormat.format(date)+"\n");
        fw.append(renglon);
        System.out.println(renglon);

        for (int i = 0; i < historiaGeneraciones.size(); i++) {
            Generacion generacion = historiaGeneraciones.get(i);
            Individuo individuoMasApto = generacion.obtenerIndividuoMasApto();
            Individuo individuoMenosApto = generacion.obtenerIndividuoMenosApto();
            double aptitudTotal = generacion.obtenerAptitudTotal();

            System.out.printf("%13.13s | %13.13s | %16.16s | %18.18s | %20.20s |\n", Integer.toString(i), Double.toString(aptitudTotal), Double.toString(aptitudTotal / 100), Double.toString(individuoMasApto.obtenerAptitud()), Double.toString(individuoMenosApto.obtenerAptitud()));
            renglon = String.format("%13.13s | %13.13s | %16.16s | %18.18s | %20.20s |\n", Integer.toString(i), Double.toString(aptitudTotal), Double.toString(aptitudTotal / 100), Double.toString(individuoMasApto.obtenerAptitud()), Double.toString(individuoMenosApto.obtenerAptitud()));

            fw.append(renglon);
        }

        fw.close();
    }
}
