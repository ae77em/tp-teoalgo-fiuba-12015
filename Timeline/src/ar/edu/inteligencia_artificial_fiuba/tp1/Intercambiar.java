package ar.edu.inteligencia_artificial_fiuba.tp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.encog.ml.genetic.crossover.Crossover;
import org.encog.ml.genetic.genome.Chromosome;

public class Intercambiar implements Crossover {

	@Override
	public void mate(Chromosome mother, Chromosome father,
			Chromosome offspring1, Chromosome offspring2) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> ordenRandom = new ArrayList<Integer>(8);
		for(int i=0; i<8; i++){
			ordenRandom.add(i);
		}
		Collections.shuffle(ordenRandom, new Random(System.nanoTime()));
		for(int i=0; i<7; i++){
			int countN1 = 0;
			int countN2 = 0;
			int n1 = ordenRandom.get(i);
			int n2 = ordenRandom.get(i+1);
			for(int j=0; j<8; j++){
				if(ordenRandom.get(i).equals(n1)){
					if(countN1 > 2) continue;
					countN1++;
					ordenRandom.set(i, n2);
				}else if(ordenRandom.get(i).equals(n2)){
					if(countN2 > 2) continue;
					countN2++;
					ordenRandom.set(i, n1);
				}
			}
		}
	}

}
