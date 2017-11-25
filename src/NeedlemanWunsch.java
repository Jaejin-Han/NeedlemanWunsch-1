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
		printMatrix();
		fillOutMatrix();
		printMatrix();
		
		
		
	}
	
	public static void initializeMatrix(String inputOne, String inputTwo){
		matrix = new String[(inputTwo.length() + 2)][inputOne.length() +2];
		matrix[0][0] = "x";
		
		
		String[] first = inputOne.split("");
		String[] second = inputTwo.split("");
		
		
		//put first sequence in the first row of matrix
		int j = 0;
		for (int i = 1; i <= first.length; i++) {
			matrix[0][i] = first[j];
//			System.out.println(first[j]);
			
			j++;
		}
		
		
		
		//put second sequence in the first column of the matrix
		int k = 0;
		for (int i = 1; i <= second.length; i++){
//			System.out.println("i: " + i);
//			System.out.println("k: " + k);
			matrix[i][0] = second[k];
//			System.out.println(second[k]);
			
			k++;
		}
		
		System.out.println("row length: " +matrix.length);
		System.out.println("col length: " +matrix[0].length);
		
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
	
	
	
	public static void fillOutMatrix(){
		for (int col = 2; col < matrix[0].length; col++){
			for (int row = 2; row < matrix.length; row++){
				System.out.println("row: " + row + ", col: " + col);
				System.out.println("top letter: " + matrix[0][col]);
				System.out.println("top: " + matrix[row-1][col]);
				System.out.println("left letter: " + matrix[row][0]);
				System.out.println("left: " + matrix[row][col-1]);
				System.out.println("corner: "+ matrix[row-1][col-1]);
				
				
				
				
				int top = Integer.parseInt(matrix[row-1][col]);
				int left = Integer.parseInt(matrix[row][col-1]);
				int corner = Integer.parseInt(matrix[row-1][col-1]);
				
				
				//check if you need to change to indel score
				int topScore = top - 2;
				int leftScore = left - 2;
				int cornerScore;
				String topLetter = matrix[0][col];
				String leftLetter = matrix[row][0];
				
				
				//get corner score
				if (topLetter.equals(leftLetter)){
					System.out.println("going here");
					cornerScore = corner + matchScore;
				} else {
					cornerScore = corner + mismatchScore;
				}
				
				int max = 0;
				
				
				String traceback = "";
				
				if (cornerScore >= topScore && cornerScore >= leftScore){
					max = cornerScore;
					System.out.println("cornerScore wins: " + max);
					//traceback matrix
					if (cornerScore == max){
						traceback += "d";
					}
				} else if (topScore >= cornerScore && topScore >= leftScore){
					max = topScore;
					System.out.println("topScore wins: " + max);
					//traceback matrix
					if (topScore == max){
						traceback += "u";
					}
				} else {
					max = leftScore;
					System.out.println("leftScore wins: " + max);
					//traceback matrix
					if (leftScore == max){
						traceback += "l";
					}
				}
				
				matrix[row][col] = Integer.toString(max);
				System.out.println("entering into the matrix");
				System.out.println();
				
				
				
			}
			System.out.println("next row");
		}
		
	}
	
	public static void printMatrix(){
		for (int i = 0; i < matrix.length; i++){
			for (int j =0; j < matrix[0].length;j++){
				System.out.printf("%s \t", matrix[i][j]);
			}
			System.out.println();
		}
	
	}
	
	public static void main(String[] args){
		NeedlemanWunsch test = new NeedlemanWunsch(1, -1, -2);
		test.inputSequences("GAAC", "CATTG");
		
	}
	
	

}
