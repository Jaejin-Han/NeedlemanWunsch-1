import java.util.Arrays;	
public class NeedlemanWunsch {
	private static String sequence1;
	private static String sequence2;
	private static int matchScore;
	private static int mismatchScore;
	private static int indelScore;
	private static String[][] matrix;
	
	public NeedlemanWunsch(int matchScore, int mismatchScore, int indelScore) {
			
			this.matchScore  = matchScore;
			this.mismatchScore = mismatchScore;
			this.indelScore = indelScore;
			
	}
	
	public static void inputSequences(String sequenceOne, String sequenceTwo) {
		sequence1 = sequenceOne;
		sequence2 = sequenceTwo;
		
		initializeMatrix(sequenceOne, sequenceTwo);
		
		
		
		
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
		test.inputSequences("GAACT", "CATTG");
		test.printMatrix();
	}
	
	

}
