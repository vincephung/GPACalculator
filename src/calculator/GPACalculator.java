package calculator;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import calculator.GPACalculator;
public class GPACalculator {
	
	/**
	 * Gets the name of the type of assignemnt : such as quiz, exam, project WITHOUT its weight.
	 * Instead of ExamOne 150 FinalExam 200 Quiz 50, it will return: ExamOne, Final Exam, Quiz
	 * @param weights
	 * @return
	 */
	public static ArrayList<String> getAssignmentNames(ArrayList<String> weights){
		ArrayList<String> assignmentNames = new ArrayList<>();
		String name = "";
		
		for(int i = 0; i < weights.size(); i++) {
			name = weights.get(i);
			name = name.substring(0,name.indexOf(" "));
			assignmentNames.add(name);
		}
		return assignmentNames;
	}
	/**
	 * Calculates current grade
	 * @param weights
	 * @return
	 */
	public static String calculateGrade(ArrayList<String> weights, ArrayList<String> assignmentNames, ArrayList<String> userInputtedGrades, int []assignmentNameCounter, boolean percentage ){
		double grade = 0;
		double subTotal = 0;
		String convertedNumberOnly2 = "";
		String numberOnly = "";
		// NEED TO CONVERT THE STRING percentges to numbers
		
		
		// average for each category, so quiz grades could be 50%, exams 25%
		double[] subGrade = new double[assignmentNameCounter.length];
		for(int i = 0; i < assignmentNames.size(); i++) {
			subTotal = 0;
			// while j is LESS than the number of user inputted grades, so if there are 4 quizzes, it will add the 4 quizzes together
			for(int j = 0; j < assignmentNameCounter[i]; j++) {
				// convert the STRING percentages to numbers so 15% becomes just 0.15
				int percentLocation = userInputtedGrades.get(i).indexOf("%");
				String convertedNumberOnly = userInputtedGrades.get(i).substring(0,percentLocation);
				//Need to remove decimal place, so if user inputted 50.50% change this to 0.5050 not 0.50.50%
				int indexOfDecimal = convertedNumberOnly.indexOf(".");
				// if there is a decimal point , 95.62% convert this to 9562
				if (indexOfDecimal != -1) {
					convertedNumberOnly2 = convertedNumberOnly.substring(0,indexOfDecimal) + convertedNumberOnly.substring(indexOfDecimal+1);
					numberOnly = "0." + convertedNumberOnly2;
					
				}
				// need to change PERCENT 15% to 0.15 if you don't put 0. it will be 15.0 instead of 0.15
				
				else {
					numberOnly = "0." + convertedNumberOnly;

				}
				double currentNumber = Double.parseDouble(numberOnly);
				subTotal = subTotal + currentNumber;
			}
			// ex. add all quiz grades up and divide by number of quizzes
			// messing up because assignment name counter is equal to 0
			if(assignmentNameCounter[i] == 0) {
				subGrade[i] = 0;
			}else {
			subGrade[i] = subTotal/assignmentNameCounter[i];
			}
		}
		
		// for each subgrade, quizzes tests etc, find out their percentage, so if your quiz average was an 80% and quizzes are worth 20% of your grade
		//find out what percentage grade that is. so 80% * 20% is 16% of your TOTAL grade.
		double [] weightPercent = new double[weights.size()];
		
		double [] userTotals = new double[weights.size()];
		// if user inputted percentages, run this
		
		if(percentage == true) {
			for(int k = 0; k < weights.size(); k++) {
				//convert string example: midtermOne 15% to 0.15
				int spaceLocation = weights.get(k).indexOf(" ");
				int percentLocation = weights.get(k).indexOf("%");
				// MIGHT BE +1 OR MIGHT NOT BE
				String convertedWord = weights.get(k).substring(spaceLocation+1,percentLocation); // MIGHT NOT BE +1
				// need to change PERCENT 15% to 0.15 if you don't put 0. it will be 15.0 instead of 0.15
				String currWord = "0." + convertedWord;
				double currCategoryWeight = Double.parseDouble(currWord);
				weightPercent[k] = currCategoryWeight;
				if(subGrade[k] == 0) {
					userTotals[k] = 0;
				}else {
				userTotals[k] = weightPercent[k] * subGrade[k];
				}
			}
			
			// if user chose numbers run this
		}else {
			double [] subTotals = createNumbersArray(weights);
			for(int i = 0; i < weights.size(); i++) {
				userTotals[i] = subTotals[i] * subGrade[i];
			}
		}
		
		// add up all of your percentages, so if user had 15% in quizzes, 30% on exams, add that together to get final grade.
		for(int i = 0; i < weights.size(); i++) {
			grade = grade + userTotals[i];
		}
		grade = grade * 100;
		String returnGrade = grade + "%";
		int indexDecimal = returnGrade.indexOf(".");
		returnGrade = returnGrade.substring(0,indexDecimal) + returnGrade.substring(indexDecimal, indexDecimal+3) + "%";
		return returnGrade;
	}
	
	// changes Weights of the class syllabus
	//ACCOUNT for if user chose percentages or numbers
	// if user chose numbers, will convert the weights of these categories to percentages
	// so if exams are worth 300 points, quizzes 50, homework 25, convert these numbers to percentages 
	// CHANGE THIS inside of the arraylist
	private static double[] createNumbersArray(ArrayList<String> weights) {
		//array to hold totals of each category
		double [] subTotals = new double[weights.size()];
		// might need to be INT not double
		double totalWeight = 0;
		for(int i = 0; i < weights.size(); i++) {
			int indexOfSpace = weights.get(i).indexOf(" ");
			String onlyNumber = weights.get(i).substring(indexOfSpace);
			double currNumber = Double.parseDouble(onlyNumber);
			subTotals[i] = currNumber;
			totalWeight = totalWeight + currNumber;
		}
		for(int j = 0; j<subTotals.length; j++) {
			subTotals[j] = subTotals[j]/totalWeight;
		}
		return subTotals;
	}
	
}
