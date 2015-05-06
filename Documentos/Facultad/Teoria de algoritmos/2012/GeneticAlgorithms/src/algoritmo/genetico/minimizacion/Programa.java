package algoritmo.genetico.minimizacion;

import java.io.FileWriter;
import java.util.ArrayList;

import algoritmo.genetico.AlgoritmoGenetico;
import algoritmo.genetico.FuncionDeAptitud;
import algoritmo.genetico.FuncionDeCorte;
import algoritmo.genetico.Generacion;
import algoritmo.genetico.Individuo;

public class Programa {
	public static void main(String[] args) throws Exception {
		// Creamos el archivo para el reporte
		FileWriter fw = new FileWriter("Reporte.csv");
		
		// Creamos la funcion cuadratica X^2 + 4X - 4
		// Hay que representarla en forma computacional
		FuncionDeAptitud funcionAMinimizar = new FuncionCuadratica(1, 4, -4, -10, 20);
		
		// Creamos la funcion de corte, por cantidad de iteraciones
		FuncionDeCorte cortePorIteraciones = new CortePorIteraciones(50);
		
		// Creamos el algoritmo genetico y aplicamos la evolucion
		AlgoritmoGenetico minimizarFuncion = new AlgoritmoGenetico(100, 0.001, 0.25, 0.5, funcionAMinimizar, cortePorIteraciones);
		minimizarFuncion.iniciar();
		
		//Obtenemos la historia de las generaciones
		ArrayList<Generacion> historiaGeneraciones = minimizarFuncion.obtenerHistoriaGeneraciones();
		
		// Iteramos sobre las generaciones e imprimimos la aptitud del individuo mas apto
		fw.write("NroGeneracion, Aptitud total, Promedio aptitud, Individuo mas apto, Individuo menos apto, \n");
                System.out.println("NroGeneracion, Aptitud total, Promedio aptitud, Individuo mas apto, Individuo menos apto, \n");
		
		for (int i = 0; i < historiaGeneraciones.size(); i++){
			Generacion generacion = historiaGeneraciones.get(i);
			Individuo individuoMasApto = generacion.obtenerIndividuoMasApto();
			Individuo individuoMenosApto = generacion.obtenerIndividuoMenosApto();
			double aptitudTotal = generacion.obtenerAptitudTotal();

                        System.out.println("" + i + ", " + aptitudTotal + ", " + (aptitudTotal / 100) + ", " + individuoMasApto.obtenerAptitud() + ", " + individuoMenosApto.obtenerAptitud() + ", \n");
			fw.write(i + ", " + aptitudTotal + ", " + (aptitudTotal / 100) + ", " + individuoMasApto.obtenerAptitud() + ", " + individuoMenosApto.obtenerAptitud() + ", \n");
		}
		
		fw.close();
	}
}
