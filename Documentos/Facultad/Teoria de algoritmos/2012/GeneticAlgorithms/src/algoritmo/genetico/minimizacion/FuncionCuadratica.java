package algoritmo.genetico.minimizacion;

import algoritmo.genetico.FuncionDeAptitud;
import algoritmo.genetico.Individuo;

public class FuncionCuadratica implements FuncionDeAptitud {

    private int limiteInferiorIntervalo;
    private int longitudIntervalo;

	// Atributos para representar las constantes de una funcion cuadratica
    // de tipo ax2 + bx + c
    private double a;
    private double b;
    private double c;

    /**
     * Constructor - Toma una funcion en formato string como parametro
     *
     * @param a
     * @param b
     * @param c
     * @param limiteInferiorIntervalo
     * @param longitudIntervalo
     */
    public FuncionCuadratica(double a, double b, double c, int limiteInferiorIntervalo, int longitudIntervalo) {
        this.a = a;
        this.b = b;
        this.c = c;

        this.limiteInferiorIntervalo = limiteInferiorIntervalo;
        this.longitudIntervalo = longitudIntervalo;
    }

    /**
     * Evalua el valor de la funcion para el gen del individuo actual.
     *
     * @param individuo
     * @return
     */
    @Override
    public double evaluar(Individuo individuo) {
        double puntoDeEvaluacion = transformarGenAValor(individuo);
        double valor = a * Math.pow(puntoDeEvaluacion, 2) + b * puntoDeEvaluacion + c;

        return valor;
    }

    /**
     * Transforma el valor entero del gen del individuo en un valor del
     * intervalo [-10, 10]
     */
    private double transformarGenAValor(Individuo individuo) {
        return limiteInferiorIntervalo + individuo.obtenerGen() * longitudIntervalo / (Math.pow(2, 7) - 1);
    }
}
