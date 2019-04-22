package br.ufc.algebra.matrixandsystems.calculationsSystems;

import java.util.HashMap;
import java.util.Map;

public class CalculationGramSchimidt {

	public static Map<Integer, float[]> transforma(float[][] valores) {
		Map<Integer, float[]> vetores = new HashMap<>();
		for (int i = 0; i < valores.length; i++) {
			vetores.put(i, valores[i]);
		}
		return vetores;
	}

	private static float[] multiplicarValorPorVetor(float[] ci, float c) {
		float result[] = new float[ci.length];
		for (int i = 0; i < ci.length; i++) {
			result[i] = ci[i] * c;
		}
		return result;
	}

	private static float multiplicarDoisVetor(float[] c1, float[] c2) {
		float result = 0;
		for (int i = 0; i < c2.length; i++) {
			result += c1[i] * c2[i];
		}
		return result;
	}

	private static float[] subtracaoEntreDoisVetores(float[] c1, float[] c2) {
		float result[] = new float[c1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = c1[i] - c2[i];
		}
		return result;
	}

	private static float[] auxSubtra(Map<Integer, float[]> parcial, int tam) {
		float r[] = new float[tam];
		r = parcial.get(0);
		for (int i = 1; i < parcial.size(); i++) {
			r = subtracaoEntreDoisVetores(r, parcial.get(i));
		}
		return r;
	}

	private static float[] formula(Map<Integer, float[]> result, float[] ci) {
		int tam = ci.length;
		float r[] = new float[tam];
		float x1, x2;
		float[] x3 = new float[tam];
		Map<Integer, float[]> parcial = new HashMap<>();
		for (int i = 0; i < result.size(); i++) {
			x1 = multiplicarDoisVetor(ci, result.get(i));
			x2 = multiplicarDoisVetor(result.get(i), result.get(i));
			x3 = multiplicarValorPorVetor(result.get(i), (x1 / x2));
			parcial.put(i, x3);
		}
		r = subtracaoEntreDoisVetores(ci, auxSubtra(parcial, tam));
		return r;
	}

	public static Map<Integer, float[]> gramSchimidt(Map<Integer, float[]> vetores) {
		float[] w1 = vetores.get(0);
		Map<Integer, float[]> result = new HashMap<>();
		result.put(0, w1);
		for (int i = 1; i < w1.length; i++) {
			result.put(i, formula(result, vetores.get(i)));
		}
		return result;
	}	
}