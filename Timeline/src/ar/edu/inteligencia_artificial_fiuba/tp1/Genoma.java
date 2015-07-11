
package ar.edu.inteligencia_artificial_fiuba.tp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.encog.ml.genetic.GeneticAlgorithm;
import org.encog.ml.genetic.genes.IntegerGene;
import org.encog.ml.genetic.genome.BasicGenome;
import org.encog.ml.genetic.genome.Chromosome;


@SuppressWarnings("serial")
public class Genoma extends BasicGenome {

	private static boolean SOLO_ORGANISMOS_VALIDOS = true;
	
	private Chromosome cromosoma;

	public Genoma(final GeneticAlgorithm owners) {

		Integer[] organismo = new Integer[25];
		
		if(SOLO_ORGANISMOS_VALIDOS){
			ArrayList<Integer> listaOpciones = new ArrayList<Integer>();
			for(int i=0; i < 3; i++){
				for(int j=0; j < 8; j++){
					listaOpciones.add(j);
				}
				if(i == 2) listaOpciones.add(0);
			}
			Collections.shuffle(listaOpciones, new Random(System.nanoTime()));
			listaOpciones.toArray(organismo);
		}else{
			for(int i=0; i < 25; i++){
				organismo[i] = (int)Math.round(Math.random()*(TipoMateria.values().length-1));
			}
		}
		
		this.cromosoma = new Chromosome();
		this.getChromosomes().add(this.cromosoma);
		
		for(int i=0;i<25;i++){
			IntegerGene gen = new IntegerGene();
			gen.setValue(organismo[i]);
			this.cromosoma.getGenes().add(gen);
		}
		setOrganism(organismo);
				
		encode();
		
	}

	@Override
	public void decode() {
		Chromosome chromosome = this.getChromosomes().get(0);
		Integer[] organism = new Integer[chromosome.size()];
		
		for(int i=0;i<chromosome.size();i++)
		{
			IntegerGene gene = (IntegerGene)chromosome.get(i);
			organism[i] = gene.getValue();
		}
		
		setOrganism(organism);
	}

	@Override
	public void encode() {
		Chromosome chromosome = this.getChromosomes().get(0);
		
		Integer[] organism = (Integer[])getOrganism();

		for(int i=0;i<chromosome.size();i++)
		{
			IntegerGene gene = (IntegerGene)chromosome.get(i);
			gene.setValue(organism[i]);
		}
	}

}
