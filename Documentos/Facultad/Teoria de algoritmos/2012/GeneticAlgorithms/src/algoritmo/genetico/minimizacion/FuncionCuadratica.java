package algoritmo.genetico.minimizacion;

import algoritmo.genetico.EcuacionPolinomica;
import algoritmo.genetico.FuncionDeAptitud;
import algoritmo.genetico.Individuo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class FuncionCuadratica implements FuncionDeAptitud, EcuacionPolinomica {

    private static Pattern formatoEcuacion = Pattern.compile("([\\+\\-]?[0-9]*)x\\^2([\\+\\-]?[0-9]*)x([\\+\\-]?[0-9]*)");
    private int limiteInferiorIntervalo;
    private int longitudIntervalo;

    // Atributos para representar las constantes de una funcion cuadratica
    // de tipo ax2 + bx + c
    private double a;
    private double b;
    private double c;
    
    public FuncionCuadratica(String ecuacion,int limiteInferiorIntevalo, int longitudIntervalo) throws IllegalArgumentException{
        
        analizarGramaticalmente(ecuacion);

        this.limiteInferiorIntervalo = limiteInferiorIntevalo;
        this.longitudIntervalo = longitudIntervalo;
    }
    
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

    /**
     * Analiza si la funcion recibida como string tiene el formato requerido, 
     * esto es, ec = ax^2 + bx + c ...
     * 
     * @param ec
     * @throws IllegalArgumentException
     */
    @Override
    public void analizarGramaticalmente(String ec) throws IllegalArgumentException {
        String a1;
        String b1;
        String c1;
        String ecuacion = ec;
        
        ecuacion = ecuacion.replaceAll(" ", "");        
        
        Matcher m = formatoEcuacion.matcher(ecuacion);
        
        if (!m.matches()) {
            throw new IllegalArgumentException("Error de sintaxis en ecuacion " + ecuacion);
        }
        a1 = m.group(1);
        if (a1.length() == 0) {
            a1 = "1";
        }
        if (a1.length() == 1 && (a1.charAt(0) == '+' || a1.charAt(0) == '-')) {
            a1 += "1";
        }
        b1 = m.group(2);
        if (b1.length() == 0) {
            b1 = "1";
        }
        if (b1.length() == 1 && (b1.charAt(0) == '+' || b1.charAt(0) == '-')) {
            b1 += "1";
        }
        c1 = m.group(3);
        
        this.a = Integer.parseInt(a1);
        this.b = Integer.parseInt(b1);
        this.c = Integer.parseInt(c1);        
    }
}
