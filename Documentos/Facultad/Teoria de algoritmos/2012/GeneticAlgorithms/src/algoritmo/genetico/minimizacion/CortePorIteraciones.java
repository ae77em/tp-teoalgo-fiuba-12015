package algoritmo.genetico.minimizacion;
import algoritmo.genetico.AlgoritmoGenetico;
import algoritmo.genetico.FuncionDeCorte;

public class CortePorIteraciones implements FuncionDeCorte {
	int cantidadDeIteraciones;
	
	/**
	 * Constructor - Toma la cantidad de iteraciones que se desea realizar
	 */
	public CortePorIteraciones(int cant) {
		cantidadDeIteraciones = cant;
	}
	
	@Override
	/**
	 * Evalua si se llego a la cantidad de iteraciones realizadas para cortar la evolucion
	 */
	public boolean condicionDeFin(AlgoritmoGenetico ag) {
		return (ag.obtenerNumeroIteraciones() < cantidadDeIteraciones);
	}
	
}
