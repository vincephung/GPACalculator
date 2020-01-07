package calculator;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import calculator.GPACalculator;
import java.util.Arrays;

public class driver {
	public static void main(String []args) {
		
		Scanner sc = new Scanner(System.in);
		//Give user options on what functino they would like to use
		while(true) {
			//check for invalid input
			int choice = 0;
			
			while(true) {
				System.out.println("Enter numbers 1-4 for corresponding choice:");
				System.out.println("Option 1: Manually Input Grade Weights: ");
				System.out.println("Option 2: Import syllabus document: ");
				// need to account for credits and stuff
				System.out.println("Option 3: Calculate Cumulative GPA: "); // if you were to get an A in these two classes, how would it affect you
				//USER NEEDS TO TYPE IN GRADE CUTOFFS, OR ELSE will use the rutgers default 90% A 80% B Etc.
				System.out.println("Option 4: Grade you need on final exam to receive a certain grade: ");
				
				choice = sc.nextInt();
				sc.nextLine();
				if(choice < 1 || choice > 4) {
					System.out.println("Invalid input");
					continue;
				}
				else {
					break;
				}
				
			}
			
			//arraylist for the gradeWeights, this includes name and percent/number Example: MidtermOne: 150 OR MidtermOne: 15%
			ArrayList<String> gradeWeights = new ArrayList<String>();
			//booleans to check if user inputs percentages or numbers
			boolean percentage = false;
			boolean numbers = false;
			String percentOrNumber = "";
			//option 1 is input grades manually
			if(choice == 1) {
				while(true) {
					System.out.print("Please type 'Percentages' or 'Numbers' for the grade weights: ");
					percentOrNumber = sc.nextLine().toLowerCase();
					if((!percentOrNumber.equals("percentages")) && (!percentOrNumber.equals("numbers"))) {
						System.out.println("Invalid input try again");
						continue;
					}
					break;
				}
				//ERROR CHECK AUTOMATICALLY CONVERT SPACE BETWEEN word and number, for example
				// if someone types final SPACE exam 15%, AUTOMATICALLY convert this to finalexam 15% so program doesn't mess up
				while (true) {
					if(percentOrNumber.equals("percentages")) {
						percentage = true;
						System.out.println("Enter assignment name and weight: (Example: Homework 15%)");
					}else if(percentOrNumber.equals("numbers")) {
						numbers = true;
						System.out.println("Enter assignment name and weight: (Example: Homework 150)");
					}else {
						System.out.println("Incorrect input try again");
						continue;
					}
					break;
				}
				// need to error check to make sure they inputted correctly: so if they type in examone 15$# or something, tell them error
				while(true) {
					System.out.println("To remove last added word type 'redo'");
					System.out.print("Enter 'Done' when finished ");
					String inputString = sc.nextLine().toLowerCase();
					
					//redo last input
					if(inputString.equals("redo")) {
						System.out.println(gradeWeights.get(gradeWeights.size()-1) + " was removed");
						gradeWeights.remove(gradeWeights.size()-1);
						continue;
					}
					String nextWeight = inputString;
					//ERROR CHECK AUTOMATICALLY CONVERT SPACE BETWEEN word and number, for example
					// if someone types final SPACE exam 15%, AUTOMATICALLY convert this to finalexam 15% so program doesn't mess up
					if(inputString.contains(" ")) {
						String noSpaceString = inputString.replaceAll(" ", "");
						int indexOfNumber = findDigit(noSpaceString);
					    nextWeight = noSpaceString.substring(0,indexOfNumber) + " " + noSpaceString.substring(indexOfNumber);
					}
					// if user inputs done then move on to next stage
					if(nextWeight.equals("done")) {
						break;
					}
					// add these weights to the arraylist gradeweights
					gradeWeights.add(nextWeight);
					
				}
			}
			//if user selects option 2 which is import a syllabus
			else if(choice == 2) {
				
			}
			// if user chooses option 3 which calculates their cumulative gpa
			else if(choice == 3) {
				
			
			}
			// if user chooses option 4
			else if(choice == 4) {
				
				
			}

			
			
			//after user inputs grade weights
			// user inputs their grades
			// if they type 'show grade' it will run the method to calculate their current grade
			ArrayList<String> assignmentNames = GPACalculator.getAssignmentNames(gradeWeights);
			ArrayList<String> userInputtedGrades = new ArrayList<>();
			//Array to store number of user inputs for each category, so 5 quizzes, 2 midterms 1 final
			int []assignmentNameCounter = new int [assignmentNames.size()];
			
			assignmentNames = GPACalculator.getAssignmentNames(gradeWeights);
			int counter = 1;
			
			// May need to check if user is using Percentages or numbers.
			System.out.println("Enter 'Percentage' or 'Fraction' for input choice: Example (70/80) or 86%");
			String inputChoice = sc.nextLine().toLowerCase();
			if(inputChoice.equals("percentage")) {
				System.out.println("Enter your grades with this format: (Example: 40% or 94%)");
			}else if (inputChoice.equals("fraction")){
				System.out.println("Enter your grades with this format: (Example: 60/70)");
			}
				
				for(int i = 0; i < assignmentNames.size(); i++) {
					System.out.println("Enter your " + assignmentNames.get(i) + " grades: ");
					counter = 1;
					while(true) {
						if(counter!=1) {
							System.out.println("If you would like to know your grade right now type 'grade' ");
							System.out.println("Enter 'next' if you are done with " + assignmentNames.get(i) + " grades.");
							System.out.print("Continue entering your " + assignmentNames.get(i) + " grades: ");
						}
						String inputGrade = sc.nextLine().toLowerCase();

						if(inputGrade.equals("next")) {
							//break SHOULD in theory break out of the while loop and go to next for loop, might need continue instead
							break;
						}
						if(inputGrade.equals("grade")) {
							String grade = GPACalculator.calculateGrade(gradeWeights, assignmentNames, userInputtedGrades, assignmentNameCounter, percentage);
							System.out.println("Your current grade is " + grade);
							continue;
						}
						// ADD these user input grades to a separate arraylist.
						userInputtedGrades.add(inputGrade);
						assignmentNameCounter[i] = counter++;
					}
					//assignmentNameCounter[i] = counter;
				

				}
				//after user is done inputting grades: 
				String grade = GPACalculator.calculateGrade(gradeWeights, assignmentNames, userInputtedGrades, assignmentNameCounter, percentage);
				System.out.println("Your current grade is " + grade);
				
				System.out.println("Type 'y' for restart, 'n' for exit");
				String finish = sc.nextLine();
				if(finish.equals("y")){
					continue;
				}
				else if(finish.equals("n")) {
					break;
				}else {
					break;
				}
	
			
		}
	}
	
	
	// method to find the first digit in a string
	private static int findDigit(String input) {
		int digit = 0;
		for(int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i))){
				digit = i;
				break;

			}
		}
		return digit;

	}
	
	
	
}
