package dap2;

/**
 *
 * @author dpf
 */
public class NGrande implements N {

	private String numero;
	
	public NGrande(String numero) {
		if (numero.length() == 0) {
			this.numero = "0";
		} else if (!numero.matches("^([0-9]*)$")) {
			throw new NumberFormatException();
		} else {
			this.numero = numero;
			while (this.numero.startsWith("0") && this.numero.length() > 1) {
				this.numero = this.numero.substring(1);
			}
		}
	}

	public NGrande(final int numero) {
		this(String.valueOf(numero));
	}

	public NGrande() {
		this("0");
	}

	@Override
	public int numCifras() {
		return numero.length();
	}

	@Override
	public N[] separa(final int indice) {
		NGrande[] separado = new NGrande[2];

		if (indice >= numero.length()) {
			separado[0] = new NGrande();
			separado[1] = new NGrande(numero);
		} else {
			separado[0] = new NGrande(numero.substring(0, numero.length() - indice));
			separado[1] = new NGrande(numero.substring(numero.length() - indice, numero.length()));
		}
		return separado;
	}

	@Override
	public N suma(final N numero) {
		int carry = 0;
		int c, c1, c2;

		NGrande a = this;
		NGrande b = new NGrande(numero.toString());

		StringBuilder sum = new StringBuilder();

		String num1 = new StringBuilder(a.numero).reverse().toString();
		String num2 = new StringBuilder(b.numero).reverse().toString();

		int max = a.numCifras() > b.numCifras() ? a.numCifras() : b.numCifras();
		for (int i = 0; i <= max; i++) {
			try {
				c1 = Integer.parseInt("" + num1.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				c1 = 0;
			}
			try {
				c2 = Integer.parseInt("" + num2.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				c2 = 0;
			}
			c = c1 + c2 + carry;
			carry = c / 10;
			c = c % 10;
			sum.append(Math.abs(c));
		}
		return new NGrande(sum.reverse().toString());
	}

	@Override
	public N resta(final N numero) {
		int carry = 0;
		int c, c1, c2;

		NGrande a = this;
		NGrande b = new NGrande(numero.toString());

		StringBuilder res = new StringBuilder();

		String num1 = new StringBuilder(a.numero).reverse().toString();
		String num2 = new StringBuilder(b.numero).reverse().toString();

		int max = a.numCifras();
		for (int i = 0; i < max; i++) {
			try {
				c1 = Integer.parseInt("" + num1.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				c1 = 0;
			}
			try {
				c2 = Integer.parseInt("" + num2.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				c2 = 0;
			}
			c2 += carry;
			if (c1 < c2) {
				c1 += 10;
				carry = 1;
			} else {
				carry = 0;
			}
			c = c1 - c2;
			res.append(Math.abs(c));
		}
		return new NGrande(res.reverse().toString());
	}

	@Override
	public N producto(final N numero) {
		int carry;
		int c, c1, c2;
		
		NGrande a = this;
		NGrande b = new NGrande(numero.toString());

		String num1 = new StringBuilder(a.numero).reverse().toString();
		String num2 = new StringBuilder(b.numero).reverse().toString();

		if (num2.length() > num1.length()) {
			String temp = num2;
			num2 = num1;
			num1 = temp;
		}

		N[] sumandos = new NGrande[num2.length()];
		StringBuilder p;
		for (int i = 0; i < num2.length(); i++) {
			p = new StringBuilder();
			carry = 0;
			try {
				c2 = Integer.parseInt("" + num2.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				c2 = 0;
			}
			for (int z = 0; z < i; z++) {
				p.append("0");
			}
			if (c2 != 0) {
				for (int j = 0; j <= num1.length(); j++) {
					try {
						c1 = Integer.parseInt("" + num1.charAt(j));
					} catch (IndexOutOfBoundsException e) {
						c1 = 0;
					}
					c = c1 * c2 + carry;
					carry = c / 10;
					c = c % 10;
					p.append(c);
				}
			}
			NGrande sumando = new NGrande(p.reverse().toString());
			sumandos[i] = sumando;
		}
		N sum = sumandos[0];
		for (int i = 1; i < sumandos.length; i++) {
			sum = sum.suma(sumandos[i]);
		}
		return new NGrande(sum.toString());
	}

	@Override
	public N karatsuba(final  N numero, final int limite) {
		
		NGrande a = this;
		NGrande b = new NGrande(numero.toString());
		
		if (limite <= 0 || a.numCifras() <= limite || b.numCifras() <= limite) {
			return a.producto(b);
		}

		int n = (Math.max(a.numCifras(), b.numCifras()))/2;

		N[] as = a.separa(n);
		N[] bs = b.separa(n);

		N aL = as[0];
		N aR = as[1];
		N bL = bs[0];
		N bR = bs[1];

		N z0 = aR.karatsuba(bR, limite);
		N z1 = aL.suma(aR).karatsuba(bL.suma(bR), limite);
		N z2 = aL.karatsuba(bL, limite);

		return z2.desplaza(2*n).suma(z1.resta(z2.suma(z0)).desplaza(n)).suma(z0);
	}
	
	@Override
	public N desplaza(final int desplazamientos) {
		StringBuilder nuevoValor = new StringBuilder(numero);
		for (int i = 0; i < desplazamientos; i++) {
			nuevoValor.append("0");
		}
		NGrande nuevo = new NGrande(nuevoValor.toString());
		return nuevo;
	}
	
	@Override
	public String toString() {
		return numero;
	}
	
	@Override
	public boolean equals(Object obj) {
		NGrande n1 = (NGrande)obj;
		return this.toString().equals(n1.toString());
	}
	
}
