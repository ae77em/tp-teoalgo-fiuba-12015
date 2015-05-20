package algoritmo.genetico;

import java.util.ArrayList;
import java.util.Collections;

public class Generacion {
	private ArrayList<Individuo> poblacion;
	private int cantidadIndividuos;
	private FuncionDeAptitud funcionDeAptitud;
	
	/**
	 * Constructor - Crea generacion con una poblacion de cantIndividuos individuos
	 */
	public Generacion(int cantidadIndividuos, FuncionDeAptitud funcionDeAptitud){
		this.poblacion = new ArrayList<Individuo>();
		this.cantidadIndividuos = cantidadIndividuos;
		this.funcionDeAptitud = funcionDeAptitud;
		
		// Agrega la cantidad de individuos indicada con aptitudes aleatorias
		for (int i = 0; i < cantidadIndividuos; i++){
			Individuo individuo = new Individuo((byte) (Math.random() * Byte.MAX_VALUE));
			individuo.calcularAptitud(funcionDeAptitud);
			poblacion.add(individuo);
		}
	}
	
	/**
	 * Constructor - Crea una generacion con la poblacion indicada
	 */
	public Generacion(ArrayList<Individuo> poblacion, FuncionDeAptitud funcionDeAptitud){
		this.poblacion = poblacion;
		this.cantidadIndividuos = poblacion.size();
		this.funcionDeAptitud = funcionDeAptitud;
		
		for (Individuo individuo : poblacion) {
			individuo.calcularAptitud(funcionDeAptitud);
		}
	}
	
	/**
	 * Agrega un nuevo individuo a la poblacion y le aplica la funcion de aptitud
	 */
	public void agregarIndividuo(Individuo individuo) {
		individuo.calcularAptitud(funcionDeAptitud);
		poblacion.add(individuo);
	}
	
	/**
	 * Aplica mutacion a todos los individuos de la poblacion.
	 */
	private void mutacion(double pctMutacion, ArrayList<Individuo> poblacionNueva) {
		for (int i = 0; i < poblacionNueva.size(); i++){
			Individuo individuo = poblacionNueva.get(i); 
			poblacionNueva.remove(individuo);
			poblacionNueva.add(individuo.mutar(pctMutacion));
		}
	}
	
	/**
	 * Aplica reproduccion a todos los individuos de la poblacion.
	 */
	private void reproduccion(double pctReproduccion, ArrayList<Individuo> poblacionNueva) {
		int cantReproducciones = (int) (poblacion.size() * pctReproduccion);
				
		// Genera los hijos 
		for(int i = 0; i < cantReproducciones; i++) {
			// Toma dos individuos al azar para las reproducciones 
			int indice1 = (int) (Math.random() * poblacionNueva.size());
			int indice2 = (int) (Math.random() * poblacionNueva.size());
			
			Individuo padre1 = poblacionNueva.get(indice1);
			Individuo padre2 = poblacionNueva.get(indice2);
			
			// Genera 1 hijo tomando la primer parte del padre1 y la segunda del padre2
			Individuo hijo = padre1.reproducirCon(padre2);
			
			//Agregamos a los padres por los hijos
			poblacionNueva.add(hijo);
		}
	}
	
	/**
	 * Aplica seleccion natural elitista a todos los individuos de la poblacion
	 */	
	private void seleccionNatural(double pctSeleccion, ArrayList<Individuo> poblacionNueva) {		
		int individuosAAgregar = (int) (poblacion.size() * pctSeleccion);
		
		// Ordena los individuos por aptitud
		Collections.sort(poblacion);
		Collections.reverse(poblacion);
		
		// Agrega los individuos de mayor aptitud al estanque
		for (int i = 0; i < individuosAAgregar; i++) {
			Individuo individuoACopiar = poblacion.get(i);
			Individuo individuoCopia = new Individuo(individuoACopiar.obtenerGen());
			
			poblacionNueva.add(individuoCopia);
		}
	}
	
	/**
	 * Crea una nueva generacion evolucionando a partir de la actual.
	 */
	public Generacion evolucionar(double pctMutacion, double pctReproduccion, double pctSeleccion){
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		int cantIndividuosAlAzar = poblacion.size() - (int)(poblacion.size() * pctReproduccion + poblacion.size() * pctSeleccion); 
		
		// Tomamos una cierta cantidad de individuos de la poblacion actual al azar
		for (int i = 0; i < cantIndividuosAlAzar; i++) {
			int indice = (int)(Math.random()) * poblacion.size();
			Individuo individuoACopiar = poblacion.get(indice);
			Individuo individuoCopia = new Individuo(individuoACopiar.obtenerGen());
			
			poblacionNueva.add(individuoCopia);
		}
		
		seleccionNatural(pctSeleccion, poblacionNueva);
		reproduccion(pctReproduccion, poblacionNueva);
		mutacion(pctMutacion, poblacionNueva);	
		
		return new Generacion(poblacionNueva, funcionDeAptitud);
	}
	
	/**
	 * Obtiene la aptitud total de la poblacion actual.
	 */	
	public double obtenerAptitudTotal(){
		double aptitudTotal = 0;
		
		for(int i = 0; i < poblacion.size(); i++){
			Individuo individuo = poblacion.get(i);
			aptitudTotal += individuo.obtenerAptitud();					
		}
		
		return aptitudTotal;
	}
	
	/**
	 * Evalua todos los individuos de la poblacion actual para encontrar el mas apto.
	 * 
	 * @return
	 */	
	public Individuo obtenerIndividuoMasApto(){
		Individuo individuoMasApto = poblacion.get(0);
		
		for(int i = 1; i < cantidadIndividuos; i++){
			Individuo individuo = poblacion.get(i);
			
			if (individuoMasApto.compareTo(individuo) == -1){
				individuoMasApto = individuo;
			}
		}
		
		return individuoMasApto;
	}
	
	/**
	 * Evalua todos los individuos de la poblacion actual para encontrar el menos apto.
	 * 
	 * @return
	 */	
	public Individuo obtenerIndividuoMenosApto(){
		Individuo individuoMenosApto = poblacion.get(0);
		
		for(int i = 1; i < cantidadIndividuos; i++){
			Individuo individuo = poblacion.get(i);
			
			if (individuoMenosApto.compareTo(individuo) == 1){
				individuoMenosApto = individuo;
			}
		}
		
		return individuoMenosApto;
	}
	
	/**
	 * Devuelve la poblacion de la generacion actual.
	 */
	public ArrayList<Individuo> obtenerPoblacion(){
		return poblacion;
	}
}
