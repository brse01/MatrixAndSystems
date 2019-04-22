package br.ufc.algebra.matrixandsystems.calculationsSystems;

public class CalculationVerifySolucion {
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
	
	private static double[][] getMatrix(double linearDouble[][]) {
		double linearDoubleAux[][] = new double[linearDouble.length][linearDouble[0].length - 1];
		for (int i = 0; i < linearDouble.length; i++) {
			for (int j = 0; j < linearDouble[0].length - 1; j++) {
				linearDoubleAux[i][j] = linearDouble[i][j];
			}
		}
		return linearDoubleAux;
	}
	
	public static boolean sistemaHomogenio(double m[][]){
		int k = m.length;
		for (int i = 0; i < m.length; i++) {
			if(m[i][k] != 0) return false;
		}				
		return true;	
	}
	
	
	public String resultadoSistema(double sistema[][]){
		
				
		return "";
	}
}
