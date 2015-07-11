package ar.edu.inteligencia_artificial_fiuba.tp1;

public class Materia {

	private TipoMateria tipo;
	
	public static int getTotalHoras(TipoMateria tipo){
		int horas = 0;;
		switch(tipo){
			case Historia:
				horas = 4;
				break;
			case CienciasNaturales:
			case Civica:
			case Computación:
			case Filosofía:
			case Geografía:
			case Lengua:
			case Matematicas:
				horas = 3;
				break;
			default:
				break;
		};
		return horas;
	}
	
	public Materia(TipoMateria tipo){
		
		this.tipo = tipo;
		
	}
	
	public TipoMateria getTipo(){
		return this.tipo;
	}
	
}
