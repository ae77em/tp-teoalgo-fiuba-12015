
package ar.edu.inteligencia_artificial_fiuba.tp1;

import org.encog.ml.genetic.BasicGeneticAlgorithm;
import org.encog.ml.genetic.GeneticAlgorithm;
import org.encog.ml.genetic.crossover.Splice;
import org.encog.ml.genetic.genes.Gene;
import org.encog.ml.genetic.genes.IntegerGene;
import org.encog.ml.genetic.genome.CalculateGenomeScore;
import org.encog.ml.genetic.mutate.MutateShuffle;
import org.encog.ml.genetic.population.BasicPopulation;
import org.encog.ml.genetic.population.Population;
import org.encog.util.Stopwatch;


public class Resolver {

	public static final int TOTAL_HORAS = 25;
	//public static final int TAMANIO_POBLACION = 600;
	public static final double PORCENTAJE_MUTACION = 0;
	public static final double PORCENTAJE_A_JUNTAR = 0.45;
	public static final double PORCENTAJE_POBLACION_JUNTADA = 0.5;
	public static final int MAXIMO_MISMA_SOLUCION = 1000;
	
	private GeneticAlgorithm genetico;

	
	private void inicializarPoblacion(GeneticAlgorithm ga, int tamanioPoblacion)
	{		
		CalculateGenomeScore puntaje =  new Puntaje();
		ga.setCalculateScore(puntaje);
		Population poblacion = new BasicPopulation(tamanioPoblacion);
		ga.setPopulation(poblacion);

		for (int i = 0; i < tamanioPoblacion; i++) {
			final Genoma genoma = new Genoma(ga);
			ga.getPopulation().add(genoma);
			ga.calculateScore(genoma);
		}
		poblacion.claim(ga);
		poblacion.sort();
	}



	public void mostrarSolucion() {
		
		boolean first = true;
		
		for(Gene gene : genetico.getPopulation().getBest().getChromosomes().get(0).getGenes() )
		{
			if( !first )
			{
				System.out.print(">");
			}
				
			System.out.print( ""+ ((IntegerGene)gene).getValue());
			first = false;
		}
		
		System.out.println("");
	}


	public void resolver() {
		
		int pob=500;
		for(int i=0; i<100; i++){
//			StringBuilder builder = new StringBuilder();

			//pob = 100*(i+1);
			
			genetico = new BasicGeneticAlgorithm();
			
			inicializarPoblacion(genetico, pob);
			genetico.setMutationPercent(PORCENTAJE_MUTACION);
			genetico.setPercentToMate(PORCENTAJE_A_JUNTAR);
			genetico.setMatingPopulation(PORCENTAJE_POBLACION_JUNTADA);
			genetico.setCrossover(new Intercambiar());
			genetico.setMutate(new MutateShuffle());

			int cantSolucionesIguales = 0;
//			int iteration = 1;
			double puntajeSolucionAnterior = Double.MAX_VALUE;

			Stopwatch timer = new Stopwatch();
			timer.start();
			int count=0;
			while (count < 1000*(i+1)) 
			{
				count++;
				genetico.iteration();
//				iteration++;
				double puntajeSolucion = genetico.getPopulation().getBest().getScore();
				
//					builder.setLength(0);
//					builder.append("Iteracion: ");
//					builder.append(iteration);
//					builder.append(" Puntaje: ");
//					builder.append(puntajeSolucion);

//					System.out.println(builder.toString());
				
//				if (Math.abs(puntajeSolucionAnterior - puntajeSolucion) < 1.0 && puntajeSolucion > 0) 
//				{
//					cantSolucionesIguales++;
//				} 
//				else 
//				{
//					cantSolucionesIguales = 0;
//				}

				puntajeSolucionAnterior = puntajeSolucion;
			}
			
			timer.stop();

			System.out.println("Solucion Buena Encontrada:");
			mostrarSolucion();
			System.out.println("Puntaje:"+puntajeSolucionAnterior);
			System.out.println("Poblacion: #"+pob);
			System.out.println("Iteraciones: #"+1000*(i+1));
			System.out.println("Tiempo de Corrida: "+timer.getElapsedMilliseconds()+"ms.");
			
		}

		
	}
}
