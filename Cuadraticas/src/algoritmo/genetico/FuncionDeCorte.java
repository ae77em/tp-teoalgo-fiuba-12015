package algoritmo.genetico;

public interface FuncionDeCorte {

    /**
     * Metodo que evalua si el algoritmo debe dejar de evolucionar
     */
    boolean condicionDeFin(AlgoritmoGenetico ag);
}
