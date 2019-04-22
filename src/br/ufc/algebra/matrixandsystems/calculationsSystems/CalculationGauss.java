package br.ufc.algebra.matrixandsystems.calculationsSystems;

import br.ufc.algebra.matrixandsystems.utils.AlertUtils;
import javafx.scene.control.Alert.AlertType;

public class CalculationGauss {
	
	private int qtdLinhas, qtdColunas;

	private static final double EPSILON = 1e-10;

	// Elimina��o gaussiana com pivoteamento parcial
	public static double[] resolve(double[][] A, double[] b) {
		int N = b.length;

		for (int p = 0; p < N; p++) {
			int max = p;
			for (int i = p + 1; i < N; i++) {
				if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
					max = i;
				}
			}
			double[] temp = A[p];
			A[p] = A[max];
			A[max] = temp;
			double t = b[p];
			b[p] = b[max];
			b[max] = t;

			if (Math.abs(A[p][p]) <= EPSILON) {
				AlertUtils.showDialog("A matriz � singular ou quase singular",AlertType.INFORMATION );
				throw new RuntimeException("A matriz � singular ou quase singular");
			}

			for (int i = p + 1; i < N; i++) {
				double alpha = A[i][p] / A[p][p];
				b[i] -= alpha * b[p];
				for (int j = p; j < N; j++) {
					A[i][j] -= alpha * A[p][j];
				}
			}
		}

		double[] x = new double[N];
		for (int i = N - 1; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < N; j++) {
				sum += A[i][j] * x[j];
			}
			x[i] = (b[i] - sum) / A[i][i];
		}
		return x;
	}

}
