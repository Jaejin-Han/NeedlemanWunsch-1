import java.util.Arrays;	
public class NeedlemanWunsch {
	private static String sequence1;
	private static String sequence2;
	private static int matchScore;
	private static int mismatchScore;
	private static int indelScore;
	private static String[][] matrix;
	private static String[][] tracebackMatrix;
	
	public NeedlemanWunsch(int matchScore, int mismatchScore, int indelScore) {
			
			this.matchScore  = matchScore;
			this.mismatchScore = mismatchScore;
			this.indelScore = indelScore;
			
	}
	
	public static void inputSequences(String sequenceOne, String sequenceTwo) {
		
		initializeMatrix(sequenceOne, sequenceTwo);
		initializeTraceback(sequenceOne, sequenceTwo);
//		printMatrix();
		fillOutMatrix();
		
		printMatrix();
		System.out.println();
		alignmentScoring();
		
		
		
	}
	
	public static void initializeMatrix(String inputOne, String inputTwo){
		matrix = new String[(inputTwo.length() + 2)][inputOne.length() +2];
		matrix[0][0] = "x";
		matrix[0][1] = "x";
		matrix[1][0] = "x";
		
		String[] first = inputOne.split("");
		String[] second = inputTwo.split("");
		
		
		//put first sequence in the first row of matrix
		int j = 1;
		for (int i = 2; i <= first.length; i++) {
			matrix[0][i] = first[j];
//			System.out.println(first[j]);
			
			j++;
		}
		
		
		
		//put second sequence in the first column of the matrix
		int k = 1;
		for (int i = 2; i <= second.length; i++){
//			System.out.println("i: " + i);
//			System.out.println("k: " + k);
			matrix[i][0] = second[k];
//			System.out.println(second[k]);
			
			k++;
		}
		
//		System.out.println("row length: " +matrix.length);
//		System.out.println("col length: " +matrix[0].length);
		
		//initialize row
		int initIndex = 0;
		for (int i = 1; i< matrix[0].length; i++){
			
			matrix[1][i] = Integer.toString(initIndex);
//			printMatrix();
//			System.out.println("i: " + i);
			initIndex += -2;
		}
		
		//initialize column
		initIndex = 0;
		for (int i = 1; i< matrix.length; i++){
			matrix[i][1] = Integer.toString(initIndex);
			initIndex += -2;
		}
	}
	
	public static void initializeTraceback(String inputOne, String inputTwo){
		tracebackMatrix = new String[(inputTwo.length() + 2)][inputOne.length() +2];
		tracebackMatrix[0][0] = "x";
		tracebackMatrix[0][1] = "x";
		tracebackMatrix[1][0] = "x";
		tracebackMatrix[1][1] = "_";
		
		String[] first = inputOne.split("");
		String[] second = inputTwo.split("");
		
		
		//put first sequence in the first row of matrix
		int j = 1;
		for (int i = 2; i <= first.length; i++) {
			tracebackMatrix[0][i] = first[j];
//			System.out.println(first[j]);
			
			j++;
		}
		
		
		
		//put second sequence in the first column of the matrix
		int k = 1;
		for (int i = 2; i <= second.length; i++){
//			System.out.println("i: " + i);
//			System.out.println("k: " + k);
			tracebackMatrix[i][0] = second[k];
//			System.out.println(second[k]);
			
			k++;
		}
		
//		System.out.println("row length: " +tracebackMatrix.length);
//		System.out.println("col length: " +tracebackMatrix[0].length);
		
		//initialize row
		for (int i = 2; i< tracebackMatrix[0].length; i++){
			
			tracebackMatrix[1][i] = "l";
//			printMatrix();
//			System.out.println("i: " + i);
		}
		
		//initialize column
		for (int i = 2; i< tracebackMatrix.length; i++){
			tracebackMatrix[i][1] = "u";
		}
	}
	
	public static void fillOutMatrix(){
		for (int col = 2; col < matrix[0].length; col++){
			for (int row = 2; row < matrix.length; row++){
				int top = Integer.parseInt(matrix[row-1][col]);
				int left = Integer.parseInt(matrix[row][col-1]);
				int corner = Integer.parseInt(matrix[row-1][col-1]);
				
				int topScore = top + indelScore;
				int leftScore = left + indelScore;
				int cornerScore;
				
				String topLetter = matrix[0][col];
				String leftLetter = matrix[row][0];
				
				
				//get corner score
				if (topLetter.equals(leftLetter)){
					cornerScore = corner + matchScore;
				} else {
					cornerScore = corner + mismatchScore;
				}
				
				int max = 0;
				
				
				String traceback = "";
				
				if (cornerScore >= topScore && cornerScore >= leftScore){
					max = cornerScore;
					//traceback matrix
					if (cornerScore == max){
						traceback += "d";
					}
				}
				if (topScore >= cornerScore && topScore >= leftScore){
					max = topScore;
					//traceback matrix
					if (topScore == max){
						traceback += "u";
					}
				} 
				if (leftScore >= cornerScore && leftScore >= topScore){
					max = leftScore;
					//traceback matrix
					if (leftScore == max){
						traceback += "l";
					}
				}
				
				tracebackMatrix[row][col] = traceback;
				matrix[row][col] = Integer.toString(max);
			}
		}
		
	}
	
	public static void alignmentScoring(){
		System.out.println("ALIGNMENT AND SCORING:");
		int row = tracebackMatrix.length - 1;
		int col = tracebackMatrix[0].length - 1;
		
		String first = "";
		String second = "";
		String score = "";
		int numericScore = 0;
		
		String current = tracebackMatrix[row][col];;
		String topLetter;
		String leftLetter;
		
		String match = Integer.toString(matchScore);
		String mismatch = Integer.toString(mismatchScore);
		String indel = Integer.toString(indelScore);
		
		while (!current.equals("_")){
			current = tracebackMatrix[row][col];
			topLetter = tracebackMatrix[0][col];
			leftLetter = tracebackMatrix[row][0];
			
			if (current.substring(0, 1).equals("d")){
				first = "\t" + topLetter + first;
				second = "\t" + leftLetter + second;
				if (topLetter.equals(leftLetter)){
					score = "\t+" + match + score;
					numericScore += matchScore;
				} else {
					score = "\t" + mismatch + score;
					numericScore += mismatchScore;
				}
				
				row-=1;
				col-=1;
			} else if (current.substring(0, 1).equals("l")){
				first = "\t" +topLetter + first;
				second = "\t_" + second;
				score = "\t" + indel + score;
				
				numericScore += indelScore;
				col-=1;
			} else if (current.substring(0, 1).equals("u")){
				first = "\t_" + first;
				second = "\t" + leftLetter + second;
				score = "\t" + indel + score;
				
				numericScore += indelScore;
				row-=1;
			}
		}
		
		System.out.println(first);
		System.out.println(second);
		System.out.println(score);
		String line = "\t";
		String tabs = "";
		for (int i = 0; i < (second.length()/2); i++){
			line += "________";
			tabs += "\t";
		}
		System.out.println(line);
		System.out.println(tabs + "= " + numericScore);
	}
	
	public static void printMatrix(){
		System.out.println("Needleman Wunsch");
		System.out.println("Match: +" + matchScore + ", Mismatch: " + mismatchScore + ", Gap: " + indelScore);
		System.out.println();
		
		System.out.println("SCORE TABLE:");
		for (int i = 0; i < matrix.length; i++){
			for (int j =0; j < matrix[0].length;j++){
				System.out.printf("%s \t", matrix[i][j]);
			}
			System.out.println();
		}
	
		System.out.println();
		
		System.out.println("TRACEBACK TABLE:\t(d = diagonal, u = up, l = left)");
		for (int i = 0; i < tracebackMatrix.length; i++){
			for (int j =0; j < tracebackMatrix[0].length;j++){
				System.out.printf("%s \t", tracebackMatrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		NeedlemanWunsch test = new NeedlemanWunsch(5, -1, -2);
		test.inputSequences("TCCTA", "TCATA");
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("TIME: " + 
		
				totalTime + "ms");
		
	}
	
	

}
