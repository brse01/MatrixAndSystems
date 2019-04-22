package br.ufc.algebra.matrixandsystems.calculationsSystems;

public class CalculationDecompoLU {

	public static void solucion(double[][] mat, double[][] lower, double[][] upper) {
		int length = mat.length;

		// Triangular matriz
		for (int i = 0; i < length; i++) {

			// upper Triangular
			for (int k = 0; k < length; k++) {
				// Somatorio de L(i,j)* U(j,k)
				double sum = 0.0;
				for (int j = 0; j < length; j++)
					sum += (lower[i][j] * upper[j][k]);

				upper[i][k] = mat[i][k] - sum;
			}
			for (int k = i; k < length; k++) {
				if (i == k)
					lower[i][i] = 1; // Diagonal as 1
				else {
					int sum = 0;
					for (int j = 0; j < i; j++)
						sum += (lower[k][j] * upper[j][i]);

					lower[k][i] = (mat[k][i] - sum) / upper[i][i];
				}
			}
		}
	}

	public static void main(String[] args) {
		double mat[][] = { { 2, -2, 1,-3 }, { 1, 3, -2, 1 }, { 0, 0, 0 ,0} };
		int length = mat.length;
		double lower[][] = new double[length][length];
		double upper[][] = new double[length][length];

		CalculationDecompoLU.solucion(mat, lower, upper);

		String aux = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				aux = aux + upper[i][j] + " ";
			}
			aux = aux + "\n";
		}

		System.out.println("####################################");
		System.out.println(aux);

	}
}