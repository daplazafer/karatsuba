package dap2;

import fundamentos.Grafica;
import java.util.Random;

/**
 *
 * @author dpf
 */
public class TestKaratsuba {

	private final static int LIMITE = 300; // Límite elegido para Karatsuba
	private final static int PRUEBAS = 10; // Cantidad de pruebas para  testTiempo()
	private final static int TAM_MAX_PRUEBAS = 2000; // Tamaño máximo de los números de prueba

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//testFuncionalidad();
		testTiempo();
	}

	/**
	 * Hace la multiplicación del informe y comprueba si el resultado es el
	 * esperado.
	 *
	 * Ya que los números no tienen muchas cifras se utiliza un límite de 10.
	 *
	 */
	public static void testFuncionalidad() {
		new Thread() {
			@Override
			public void run() {

				int limite = 10;

				N n0 = new NGrande("4728474577674616161839303495756453524262737373736272827367382726364784585746383647437");
				N n1 = new NGrande("13142635242626253673748586970695656453524123131425364758696968574634535423535475869457362618191726363727252424253738");

				N r0 = n0.karatsuba(n1, limite);

				System.out.print("Test funcionalidad (límite = " + limite + "): ");

				N r = new NGrande("62144616628408701352907532294392454200678186409076582569248091917428034055657239700154822244646944932206087383650472199908808429608671453280623294504596920333030087635854343636011349681635531221369506");

				System.out.println(r0.equals(r));
			}
		}.start();
	}

	/**
	 * Realiza varias pruebas de tiempo para multiplicar números por el
	 * algoritmo de Karatsuba
	 *
	 */
	public static void testTiempo() {

		new Thread() {
			@Override
			public void run() {
				int salto = TAM_MAX_PRUEBAS / PRUEBAS;

				Grafica g = new Grafica("Test tiempo (límite = " + LIMITE + ")", "Cifras", "Tiempo /ms");

				long[] tt = new long[(TAM_MAX_PRUEBAS / salto) + 1];
				long[] tk = new long[(TAM_MAX_PRUEBAS / salto) + 1];

				int tests = 10;
				long[] ttk = new long[tests], ttt = new long[tests];
				long tmk, tmt;

				for (int i = 0; i <= TAM_MAX_PRUEBAS / salto; i++) {
					N n0, n1, n2, n3;
					String aleatorio0, aleatorio1;

					aleatorio0 = aleatorio(i * salto);
					aleatorio1 = aleatorio(i * salto);

					n0 = new NGrande(aleatorio0);
					n1 = new NGrande(aleatorio1);
					n2 = new NGrande(aleatorio0);
					n3 = new NGrande(aleatorio1);

					for (int j = 0; j < tests; j++) {

						ttk[j] = System.currentTimeMillis();
						n0.karatsuba(n1, LIMITE);
						ttk[j] = System.currentTimeMillis() - ttk[j];

						ttt[j] = System.currentTimeMillis();
						n2.producto(n3);
						ttt[j] = System.currentTimeMillis() - ttt[j];

					}

					tmk = 0;
					tmt = 0;
					for (int j = 0; j < tests; j++) {
						tmk += ttk[j];
						tmt += ttt[j];
					}
					tk[i] = tmk / tests;
					tt[i] = tmt / tests;

				}

				g.ponColor(Grafica.rojo);
				g.ponTitulo("Tradicional");
				for (int i = 0; i < TAM_MAX_PRUEBAS / salto; i++) {
					g.inserta(i * salto, tt[i]);
				}

				g.otraGrafica();

				g.ponColor(Grafica.azul);
				g.ponTitulo("Karatsuba");
				for (int i = 0; i < TAM_MAX_PRUEBAS / salto; i++) {
					g.inserta(i * salto, tk[i]);
				}

				g.pinta();

			}
		}.start();
	}

	/**
	 * Genera un String con un número aleatorio del tamaño dado
	 *
	 * @param tam número de cifras deseado
	 * @return cadena con el número generado aleatoriamente
	 */
	public static String aleatorio(int tam) {
		StringBuilder numero = new StringBuilder();
		for (int i = 0; i < tam; i++) {
			if (i == 0) {
				numero.append((int) ((long) ((9l) * new Random().nextDouble())) + 1);
			} else {
				numero.append((int) ((long) ((10l) * new Random().nextDouble())));
			}
		}
		return numero.toString();
	}

}
