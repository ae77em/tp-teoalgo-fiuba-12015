package ar.edu.inteligencia_artificial_fiuba.tp1;

public class Semana {

	public static int LUNES = 0;
	public static int MARTES = 0;
	public static int MIERCOLES = 0;
	public static int JUEVES = 0;
	public static int VIERNES = 0;
	
	private Materia[][] dias;
	
	public Semana(){
		dias = new Materia[5][5];
		/*
		for(int i=0;i<5;i++){			
			dias[i] = new Materia[5];			
		}
		*/
	}
	
	public Semana(Integer[] organismo){
		dias = new Materia[5][5];
		/*
		for(int i=0;i<5;i++){			
			dias[i] = new Materia[5];			
		}*/
		for(int i=0; i < 5; i++){
			for(int j=0; j < 5; j++){
				dias[i][j] = new Materia(TipoMateria.values()[organismo[i*5+j]]);
			}
		}
	}
	
	public Materia getMateria(int dia, int hora){
		return dias[dia][hora];		
	}
	
	public void setMateriaDiaHora(int dia, int hora, Materia materia){
		this.dias[dia][hora] = materia;
	}
	
}
