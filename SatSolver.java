package javaprograms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class SatSolver {
	public static void main(String[] args) throws Exception {

		File file = new File("input.cnf");
		Scanner scanner = new Scanner(file);
		FileWriter fw = new FileWriter("output.cnf");
		try {
			String token = scanner.next();
			String result;
			while (token.equals("c")) {
				scanner.nextLine();
				token = scanner.next();
			}
			if (!token.equals("p")) {
				throw new Exception();
			}
		} catch (NoSuchElementException e) {
			throw new Exception("Header not found");
		}

		int numVariables, numClauses;
		try {
			String cnf = scanner.next();
			if (!cnf.equals("cnf")) {
				throw new Exception();
			}
			numVariables = scanner.nextInt();
			numClauses = scanner.nextInt();
		} catch (NoSuchElementException e) {
			throw new Exception();
		}

		int[][] arr = new int[numClauses][numVariables];

		for (int i = 0; i < numClauses; i++) {
			for (int j = 0; j < numVariables; j++) {
				int literal = scanner.nextInt();
				int a = literal;
				if (a != 0 && j + 1 == Math.abs(a)) {
					arr[i][j] = literal;
				} else if (a != 0 && j + 1 != Math.abs(a)) {
					arr[i][j + 1] = literal;
				}

				else if (a == 0) {
					if (i > j) {
						j--;
					}
				}

			}
		}
		int[][] copiedArr = new int[numClauses][numVariables];
		for (int i = 0; i < numClauses; i++) {
			for (int j = 0; j < numVariables; j++) {
				copiedArr[i][j] = arr[i][j];
			}

		}

		for (int i1 = 0; i1 != (1 << numVariables); i1++) {

			String s = Integer.toBinaryString(i1);
			while (s.length() != numVariables) {
				s = "0" + s;

			}
			for (int j3 = 0; j3 < numVariables; j3++) {

				char subStr = s.charAt(j3);
				for (int j4 = 0; j4 < numClauses; j4++) {
					if (copiedArr[j4][j3] > 0) {
						copiedArr[j4][j3] = Integer.parseInt(String.valueOf(subStr));
					} else if (copiedArr[j4][j3] == 0) {
						copiedArr[j4][j3] = 3;
					}

					else if (copiedArr[j4][j3] < 0) {
						if (Integer.parseInt(String.valueOf(subStr)) == 1) {
							copiedArr[j4][j3] = 0;
						}
						if (Integer.parseInt(String.valueOf(subStr)) == 0) {
							copiedArr[j4][j3] = 1;
						}

					}

				}

			}
			int counter = 0;
			for (int u = 0; u < numClauses; u++) {
				for (int i = 0; i < numVariables; i++) {
					if (copiedArr[u][i] == 1) {
						counter++;
						break;
					}
				}
			}
			if (counter == numClauses) {
				fw.write("SAT\n");
				for (int i = 0; i < numVariables; i++) {
					char subStr = s.charAt(i);
					if (Integer.parseInt(String.valueOf(subStr)) == 0) {
						int a = -(i + 1);
						String s1 = Integer.toString(a);
						fw.write(s1);
						fw.write(" ");
					} else if (Integer.parseInt(String.valueOf(subStr)) == 1) {
						int a = i + 1;
						String s1 = Integer.toString(a);
						fw.write(s1);
						fw.write(" ");
					}
				}
				break;
			} else if (!s.contains("0")) {
				fw.write("UNSAT\n");
				break;
			}

			for (int i = 0; i < numClauses; i++) {
				for (int j = 0; j < numVariables; j++) {
					copiedArr[i][j] = arr[i][j];
				}

			}

		}
		fw.close();
	}

}
