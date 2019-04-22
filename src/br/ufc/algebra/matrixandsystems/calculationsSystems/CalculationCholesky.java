package br.ufc.algebra.matrixandsystems.calculationsSystems;

public class CalculationCholesky {
	private int m;
	private double[][] g;

	public double[][] resolve(double[][] a) {
		m = a.length;
		g = new double[m][m]; // inicializa com 0

		// A matriz sempre deve ser simetrica e positiva essas checagens n�o vou
		// colocar por estar fazendo uma implementa��o muito simples, com mais
		// tempo seria bom checar e lan�ar as exce��es.

		// Mesmo processo que a fatora��o LU com um passo a mais G.Gt
		for (int i = 0; i < m; i++) {
			for (int k = 0; k < (i + 1); k++) {
				double sum = 0;
				for (int j = 0; j < k; j++) {
					sum += g[i][j] * g[k][j];
				}
				g[i][k] = (i == k) ? Math.sqrt(a[i][i] - sum) : (1.0 / g[k][k] * (a[i][k] - sum));
			}
		}
		return g;
	}

}
