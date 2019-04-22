package br.ufc.algebra.matrixandsystems.calculationsMatrix;

@SuppressWarnings("unused")
public class Matrix {
	private static double matrix[][];

	// cria uma nova matriz
	public static double[][] matrix(int line, int column) {
		return matrix = new double[line][column];
	}

	// calcula a transposta da matriz
	public static double[][] transposed(double[][] a) {
		double[][] result = matrix(a[0].length, a.length);

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				result[j][i] = a[i][j];
			}
		}

		return result;

	}

	// atribui a todos os elemetos 0
	public static double[][] nulL(double a[][]) {
		double[][] result = matrix(a.length, a[0].length);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = 0.0;
			}
		}
		return result;
	}

	public static double[][] diagonal(double a[][]) {
		double[][] result = matrix(a.length, a[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (i != j)
					result[i][j] = 0.0;
				else
					result[i][j] = a[i][j];
			}
		}
		return result;
	}

	// soma de uma matriz

	public static double[][] sum(double a[][], double b[][]) {
		double[][] result = matrix(a.length, a[0].length);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = (a[i][j] + b[i][j]);
			}
		}
		return result;
	}

	// multiplica as matrizes
	public static double[][] multiplication(double a[][], double b[][]) {
		double[][] result = matrix(a.length, b[0].length);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < a[0].length; k++) {
					result[i][j] += (a[i][k] * b[k][j]);
				}
			}
		}
		return result;
	}

	// subtrai as matrizes
	public double[][] subtraction(double a[][], double b[][]) {
		double[][] result = matrix(a.length, b[0].length);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = (a[i][j] - b[i][j]);
			}
		}
		return result;
	}

	public double somaDiagonalPrincipal(double a[][]) {
		double soma = 0.0;
		for (int row = 0; row < a.length; row++) {
			soma += a[row][row];
		}
		return soma;
	}

	public double somaDiagonalSecundaria(double a[][]) {
		double soma = 0.0;
		int n = a.length;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if ((i + j) == (-1))
					soma += a[i][j];
			}
		}
		return soma;
	}

	// CALCULA O DETERMINANTE PELO MÉTODO DE LAPLACE
	public static double laplace(double[][] matriz) {

		validaMatriz(matriz);
		double result = 0;
		if (matriz.length == 1) {
			result = matriz[0][0];
		} else if (matriz.length == 2) {
			result = matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
		} else if (matriz.length == 3) {
			result = matriz[0][0] * matriz[1][1] * matriz[2][2] + matriz[0][1] * matriz[1][2] * matriz[2][0]
					+ matriz[0][2] * matriz[1][0] * matriz[2][1] - matriz[0][2] * matriz[1][1] * matriz[2][0]
					- matriz[0][0] * matriz[1][2] * matriz[2][1] - matriz[0][1] * matriz[1][0] * matriz[2][2];
		} else {
			double[][] aux;
			int i_aux, j_aux, linha, coluna, i;

			for (i = 0; i < matriz.length; i++) {

				if (matriz[0][i] != 0) {
					aux = new double[matriz.length - 1][matriz.length - 1];
					i_aux = 0;
					j_aux = 0;

					for (linha = 1; linha < matriz.length; linha++) {
						for (coluna = 0; coluna < matriz.length; coluna++) {
							if (coluna != i) {
								aux[i_aux][j_aux] = matriz[linha][coluna];
								j_aux++;
							}
						}
						i_aux++;
						j_aux = 0;
					}
					result += Math.pow(-1, i) * matriz[0][i] * laplace(aux);
				}
			}
		}
		return result;
	}

	public static void validaMatriz(double[][] matriz) {
		if (matriz.length != matriz[0].length)
			throw new IllegalArgumentException("A matriz não é quadrada");
	}

	public static double[][] invert(double a[][]) {
		int n = a.length;
		double result[][] = new double[n][n];
		double b[][] = new double[n][n];
		int index[] = new int[n];
		for (int i = 0; i < n; ++i)
			b[i][i] = 1;

		// Transformar a matriz em um triângulo superior...
		gaussian(a, index);

		// Atualize a matriz b [i] [j] com as relações armazenadas
		for (int i = 0; i < n - 1; ++i)
			for (int j = i + 1; j < n; ++j)
				for (int k = 0; k < n; ++k)
					b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];

		// Realizar substituições para trás
		for (int i = 0; i < n; ++i) {
			result[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
			for (int j = n - 2; j >= 0; --j) {
				result[j][i] = b[index[j]][i];
				for (int k = j + 1; k < n; ++k) {
					result[j][i] -= a[index[j]][k] * result[k][i];
				}
				result[j][i] /= a[index[j]][j];
			}
		}
		return result;
	}

	// Método para realizar o Gaussian de rotação parcial
	// eliminação. Aqui o índice [] armazena a ordem de rotação.
	public static void gaussian(double a[][], int index[]) {
		int n = index.length;
		double c[] = new double[n];

		// Inicializa o indice
		for (int i = 0; i < n; ++i)
			index[i] = i;

		// Encontre os fatores de reescalonamento, um de cada linha
		for (int i = 0; i < n; ++i) {
			double c1 = 0;
			for (int j = 0; j < n; ++j) {
				double c0 = Math.abs(a[i][j]);
				if (c0 > c1)
					c1 = c0;
			}
			c[i] = c1;
		}

		// Pesquise o elemento giratório de cada coluna
		int k = 0;
		for (int j = 0; j < n - 1; ++j) {
			double pi1 = 0;
			for (int i = j; i < n; ++i) {
				double pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}

			// Intercambiar linhas de acordo com a ordem de rotação
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i = j + 1; i < n; ++i) {
				double pj = a[index[i]][j] / a[index[j]][j];

				// Registrar relações de rotação abaixo da diagonal
				a[index[i]][j] = pj;

				// Modificar outros elementos de acordo
				for (int l = j + 1; l < n; ++l)
					a[index[i]][l] -= pj * a[index[j]][l];
			}
		}
	}

	public boolean simetrica(double a[][]) {
		boolean ehsimetrica = true;
		for (int i = 0; (i < a.length) && (ehsimetrica == true); i++) {
			for (int j = 0; (j < i) && (ehsimetrica == true); j++) {
				// verificando se os elementos em posicoes simetricas sao iguais
				if (a[i][j] != a[j][i])
					ehsimetrica = false;
			}
		}
		return ehsimetrica;
	}

	public static double[][] escalar(double a[][], int k) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] *= k;
			}
		}
		return a;

	}

	private static double[][] potenciaAux(double A[][], double B[][], int p) {

		if (p == 1)
			return B;
		return potenciaAux(A, multiplication(A, B), p - 1);
	}

	public static double[][] potencia(double A[][], int p) {
		if (p == 0) {
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A.length; j++) {
					A[i][j] = 1;
				}
			}

			return A;

		}
		if (p == 1)
			return A;
		return potenciaAux(A, multiplication(A, A), p - 1);
	}

	public static boolean verificaMatrixQudrada(double a[][], int i) {
		if (i == 1) {
			return (a.length == a[0].length) ? true : false;
		} else {
			return (a.length == a[0].length - 1) ? true : false;
		}
	}

	public static boolean verificarLinhaSistemaAux(double a[][], int i) {
		boolean linhaSoZero = false;
		// FALSE -> LINHA TODA ZERO COM resultado != DE ZERO
		// TRUE -> LINHA OK !
		int j;
		for (j = 0; j < a[0].length - 1; j++) {
			if (a[i][j] != 0) {
				linhaSoZero = true;
			}
		}
		if (a[i][j] != 0 && linhaSoZero == false)
			linhaSoZero = false;

		return linhaSoZero;
	}

	public static boolean verificarSistema(double a[][]) {
		int i;
		for (i = 0; i < a.length; i++) {
			if (verificarLinhaSistemaAux(a, i) == false) {
				break;
			}
		}
		return (i == a.length) ? true : false;
	}

	public static boolean verificarSistemaHomogenio(double a[][]) {
		int k = a.length - 1;
		for (int i = 0; i < a.length; i++) {
			if(a[i][k] != 0.0) return false;			
		}
		return true;
	}

}
