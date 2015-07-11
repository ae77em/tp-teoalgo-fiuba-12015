package algoritmo.genetico;

import java.math.BigInteger;

public class Individuo implements Comparable<Individuo> {

    private final int BITS_GEN = 7;
    
    private double aptitud;
    private byte gen;
    private StringBuilder genBinario;

    /**
     * Constructor - Crea un Individuo con el gen indicado
     */
    public Individuo(byte gen) {
        this.gen = gen;
        this.genBinario = new StringBuilder(Integer.toBinaryString(gen));

        // Completamos el gen con 7 bits para evitar problemas
        if (this.genBinario.length() < 7) {
            this.genBinario = new StringBuilder("0" + "0000000".substring(0, 7 - genBinario.length()) + Integer.toBinaryString(gen));
        }
    }

    /**
     * Constructor - Sobrecargado para las reproducciones
     */
    public Individuo(byte gen, StringBuilder genBinario) {
        this.gen = gen;
        this.genBinario = genBinario;

        // Completamos el gen con 7 bits para evitar problemas
        if (this.genBinario.length() < 7) {
            this.genBinario = new StringBuilder("0000000".substring(0, 7 - genBinario.length()) + Integer.toBinaryString(gen));
        }
    }

    /**
     * Obtiene el gen del individuo
     */
    public byte obtenerGen() {
        return gen;
    }

    /**
     * Evalua la aptitud del individuo
     */
    public void calcularAptitud(FuncionDeAptitud funcionDeAptitud) {
        aptitud = funcionDeAptitud.evaluarAptitud(this);
    }

    /**
     * Devuelve la aptitud del individuo
     */
    public double obtenerAptitud() {
        return aptitud;
    }

    /**
     * Aplica mutacion al individuo actual
     */
    public Individuo mutar(double pctMutacion) {
        StringBuilder genBinarioMutado = new StringBuilder(genBinario);
        String aux;
        // Para cada bit del gen, se calcula si debe mutar o no
        for (int i = 0; i < genBinarioMutado.length(); i++) {
            double numeroRand = Math.random();

            if (numeroRand < pctMutacion) {
                if (String.valueOf(genBinarioMutado.charAt(i)).equals("0")) {
                    genBinarioMutado.setCharAt(i, (char) (genBinarioMutado.charAt(i) + 1));
                } else {
                    genBinarioMutado.setCharAt(i, (char) (genBinarioMutado.charAt(i) - 1));
                }
            }
        }

        aux = genBinarioMutado.toString();
        byte genMutado = (byte) Long.parseLong(aux, 2);

        return new Individuo(genMutado, genBinarioMutado);
    }

    /**
     * Genera un nuevo individuo a partir de reproduccion
     */
    public Individuo reproducirCon(Individuo individuo) {
        int puntoDeCruza = (int) Math.ceil(Math.random() * BITS_GEN);

        // Se corta los genes por un determinado punto y se toma la primer parte del individuo 1 y la segunda parte del individuo 2
        String parteIndividuo1 = genBinario.substring(0, puntoDeCruza);
        String parteIndividuo2 = individuo.genBinario.substring(puntoDeCruza, BITS_GEN);

        StringBuilder genBinarioHijo = new StringBuilder(parteIndividuo1 + parteIndividuo2);
        byte genHijo = (byte) Integer.parseInt(genBinarioHijo.toString());

        // Por ultimo se devuelve un nuevo individuo con el gen indicado 
        return new Individuo(genHijo, genBinarioHijo);
    }

    /**
     * Compara la aptitud de dos individuos
     */
    @Override
    public int compareTo(Individuo rival) {
        if (aptitud < rival.aptitud) {
            return 1;
        } else if (aptitud > rival.aptitud) {
            return -1;
        }

        return 0;
    }
}
