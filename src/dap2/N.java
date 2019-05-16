package dap2;

/**
 * Representa un número natural de cualquier tamaño
 * 
 * @author dpf
 */
public interface N {
    
    /**
     * Método que devuelve el número de cifras
     * 
     * @return némero de cifras
     */
    public int numCifras();

    /**
     * Separa el número en dos partes cortando por un índice dado
     * 
     * @param indice indice a partir del cual se realiza el corte
     * @return array de tamaño 2 que contiene la primera parte del número en la
     * posición 0 y la segunda en la 1
     */
    public N[] separa(final int indice);

    /**
     * Suma dos números con el algoritmo de la escuela
     * 
     * @param numero número a ser sumado con este
     * @return número que contiene la suma
     */
    public N suma(N numero);

    /**
     * Resta dos números con el algoritmo de la escuela
     * 
     * @param numero número que se resta a este
     * @return número que contiene la resta
     */
    public N resta(N numero);

    /**
     * Multiplica dos números con el algoritmo de la escuela
     * 
     * @param numero número que se multiplica con este
     * @return número que contiene la multiplicación
     */
    public N producto(N numero);

    /**
     * Multiplica dos números con el algoritmo de Karatsuba
     * 
     * @param numero número que se multiplica con este
     * @param limite número de cifras a partir del cual se deja de utilizar el 
     * algoritmo de la escuela para utilizar karatsuba
     * @return número que contiene la multiplicación
     */
    public N karatsuba(N numero, final int limite);

    /**
     * Este método añade ceros a la derecha, es como multiplicar por una 
     * potencia de 10
     * 
     * @param desplazamientos potencia de 10 por la que se multiplica el número
     * @return 
     */
    public N desplaza(final int desplazamientos);

}
