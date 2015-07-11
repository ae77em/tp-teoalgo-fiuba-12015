package algoritmo.genetico;

import java.util.ArrayList;

public class AlgoritmoGenetico {

    private ArrayList<Generacion> generaciones = new ArrayList<>();
    private int tamanioPoblacion;

    private double porcentajeMutacion;
    private double porcentajeReproduccion;
    private double porcentajeSeleccion;

    private FuncionDeAptitud funcionDeAptitud;
    private FuncionDeCorte funcionDeCorte;

    /**
     * Crea un nuevo Algoritmo Genetico con los parametros indicados
     */
    public AlgoritmoGenetico(int tamPoblacion, double pctMutacion, double pctReproduccion, double pctSeleccion, FuncionDeAptitud funcionDeAptitud, FuncionDeCorte funcionDeCorte) throws Exception {
        if (pctMutacion + pctReproduccion + pctSeleccion > 1) {
            System.out.println("La suma de porcentajes debe estar entre 0 y 1");
            throw new Exception();
        }

        tamanioPoblacion = tamPoblacion;

        porcentajeMutacion = pctMutacion;
        porcentajeReproduccion = pctReproduccion;
        porcentajeSeleccion = pctSeleccion;

        this.funcionDeAptitud = funcionDeAptitud;
        this.funcionDeCorte = funcionDeCorte;

        // Genero la primer poblacion
        crearGeneracionInicial();
    }

    /**
     * Genera la primera generacion (esta es aleatoria)
     */
    private void crearGeneracionInicial() {
        generaciones.add(new Generacion(tamanioPoblacion, funcionDeAptitud));
    }

    /**
     * Evoluciona la ultima generacion para obtener la siguiente
     */
    private void crearGeneracionSiguiente() {
        Generacion nuevaGeneracion = obtenerGeneracionActual().evolucionar(porcentajeMutacion, porcentajeReproduccion, porcentajeSeleccion);
        this.generaciones.add(nuevaGeneracion);
    }

    /**
     * Genera n generaciones hasta que se cumpla la condicion de fin
     */
    public void iniciar() {
        // Mientras no se cumpla la condicion de fin, sigo evolucionando la poblacion
        while (funcionDeCorte.condicionDeFin(this)) {
            try {
                crearGeneracionSiguiente();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * Devuelve la cantidad de iteraciones realizadas
     */
    public int obtenerNumeroIteraciones() {
        return generaciones.size();
    }

    /**
     * Devuelve todas las generaciones generadas
     */
    public ArrayList<Generacion> obtenerHistoriaGeneraciones() {
        return generaciones;
    }

    /**
     * Devuelve la ultima generacion
     */
    public Generacion obtenerGeneracionActual() {
        return generaciones.get(generaciones.size() - 1);
    }
}
