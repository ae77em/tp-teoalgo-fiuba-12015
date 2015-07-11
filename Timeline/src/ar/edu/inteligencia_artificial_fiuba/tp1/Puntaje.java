
package ar.edu.inteligencia_artificial_fiuba.tp1;

import org.encog.ml.genetic.genome.CalculateGenomeScore;
import org.encog.ml.genetic.genome.Genome;

public class Puntaje implements CalculateGenomeScore {

	
	@Override
	public double calculateScore(Genome genome) {
		
		double puntaje = 0.0;
		
		Semana semana = new Semana((Integer[])genome.getOrganism());
		
		//Primero se chequea que el organismo sea valido
		int[][] horasPorMateria = new int[TipoMateria.values().length][1];
		for(int i=0; i < TipoMateria.values().length; i++){
			horasPorMateria[i][0] = 0;
		}
		for(int i=0; i < 5; i++){
			for(int j=0; j < 5; j++){
				int idx = semana.getMateria(i, j).getTipo().ordinal();
				horasPorMateria[idx][0]++;
				if(horasPorMateria[idx][0] > Materia.getTotalHoras(TipoMateria.values()[idx])){
					return 0;
				}
			}
		}

		//Si es valido se le calcula el puntaje
		
		//Restriccion 1: Profesor de historia no puede los miercoles
		boolean flag = VerificarMateriaEnDia(semana, TipoMateria.Historia, Semana.MIERCOLES);
		puntaje = puntaje + (flag ? 0: 1);
		
		//Restriccion2: Profesora de Lengua no puede los lunes
		flag = VerificarMateriaEnDia(semana, TipoMateria.Lengua, Semana.LUNES);
		puntaje = puntaje + (flag ? 0: 1);
		
		//Restriccion 3: Profesora de Filosofia no puede los Viernes
		flag = VerificarMateriaEnDia(semana, TipoMateria.Filosofía, Semana.VIERNES);
		puntaje = puntaje + (flag ? 0: 1);
		
		//Restriccion 4: Mas de una hora por dia
		for(int i=0; i < TipoMateria.values().length; i++){
			flag = VerificarMateriaCantidadHorasDia(semana, TipoMateria.values()[i]);
			puntaje = puntaje + (flag ? 0: 1);
		}
		
		//Restriccion 5: Horas no consecutivas
		for(int i=0; i < TipoMateria.values().length; i++){
			flag = !VerificarMateriaEnHorasConsecutivas(semana, TipoMateria.values()[i]);
			puntaje = puntaje + (flag ? 0: 1);
		}
		
		return puntaje;
	}
	
	private boolean VerificarMateriaCantidadHorasDia(Semana semana, TipoMateria materia){
		boolean flag = false;
		for(int i=0; i < 5; i++){
			int count = 0;
			for(int j=0; j < 5; j++){
				count = count + (semana.getMateria(i, j).getTipo().equals(materia)?1:0);
			}
			if(count > 1){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	private boolean VerificarMateriaEnHorasConsecutivas(Semana semana, TipoMateria materia){
		boolean flag = false;
		for(int i=0; i < 5; i++){
			for(int j=0; j < 4; j++){
				if(semana.getMateria(i, j).getTipo().equals(materia)&&semana.getMateria(i, j+1).getTipo().equals(materia)){
					flag = true;
					break;
				}
			}
		}		
		return flag;
	}
	
	private boolean VerificarMateriaEnDia(Semana semana, TipoMateria materia, int dia){
		boolean flag = false;
		for(int i=0;i<5;i++){
			if(semana.getMateria(dia,i).getTipo().equals(materia)){
				flag = true;
				break;
			}
		}
		return flag;
		
	}

	public boolean shouldMinimize() {
		return true;
	}

}
