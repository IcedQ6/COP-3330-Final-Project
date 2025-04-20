package allClasses;

import java.util.Scanner;
import java.util.ArrayList;

// COP 3330 Final Project
// Luke Whipple, Daniel Scariti, Ivan Soliven, Michael Tran

public class FinalProject {
	
	private static boolean breakFromOptions;

	public static void main(String[] args) {
		ArrayList<Person> personnel = new ArrayList<Person>();
		Scanner scn = new Scanner(System.in);
		boolean stillWorking = true;
		
		System.out.println("Welcome to the Personnel Management System!\n");
		
		while (stillWorking) {
			System.out.println("Choose one of the options:\r\n"
					+ "1- Enter the information of a faculty\r\n"
					+ "2- Enter the information of a student\r\n"
					+ "3- Print tuition invoice for a student\r\n"
					+ "4- Print faculty information\r\n"
					+ "5- Enter the information of a staff member\r\n"
					+ "6- Print the information of a staff member\r\n"
					+ "7- Delete a person\r\n"
					+ "8- Exit Program");
			
			int menuChoice = recieveInt(responseType.OptionSelect, scn);
			
			switch (menuChoice) {
			case 1:
				Person nf = addFaculty(scn);
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				personnel.add(nf);
				break;
			case 2:
				Person np = addStudent(scn);
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				personnel.add(np);
				break;
			case 5:
				Person ns = addStaff(scn);
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				personnel.add(ns);
				break;
			
			case 8:
				stillWorking = false;
				break;
			}
		}
		
		//personel.add(new Student("john doe", "jd7538", 3.9, 12));
		
		
		
	}
	
	// Enter faculty
	private static Person addFaculty(Scanner scn) {
		String fullName = null, id = null, department = null, rank = null;
		
		System.out.println("Please enter the name of the new faculty member: ");
		fullName = recieveString(responseType.Generic, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the ID of the new faculty member: ");
		id = recieveString(responseType.ID, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the department of the new faculty member: ");
		department = recieveString(responseType.Department, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the rank of the new faculty member: ");
		rank = recieveString(responseType.Rank, scn);
		if (breakFromOptions) return null;
		
		return new Faculty(fullName, id, department, rank);
	}
	
	// Enter staff
	private static Person addStaff(Scanner scn) {
		String fullName = null, id = null, department = null, status = null;
		
		System.out.println("Please enter the name of the new faculty member: ");
		fullName = recieveString(responseType.Generic, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the ID of the new faculty member: ");
		id = recieveString(responseType.ID, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the department of the new faculty member: ");
		department = recieveString(responseType.Department, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the rank of the new faculty member: ");
		status = recieveString(responseType.Status, scn);
		if (breakFromOptions) return null;
		
		return new Staff(fullName, id, department, status);
	}
	
	// Enter student
	private static Person addStudent(Scanner scn) {
		String fullName = null, id = null;
		double GPA = 0.0;
		int CreditHours = 0;
		
		System.out.println("Please enter the name of the new student: ");
		fullName = recieveString(responseType.Generic, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the ID of the new student: ");
		id = recieveString(responseType.ID, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the GPA of the new student: ");
		GPA = recieveDouble(responseType.GPA, scn);
		if (breakFromOptions) return null;
		
		System.out.println("Please enter the number of credit hours the new student is taking: ");
		CreditHours = recieveInt(responseType.CH, scn);
		if (breakFromOptions) return null;
		
		return new Student(fullName, id, GPA, CreditHours);
	}
	
	// Print invoice for student
	
	// Print faculty information
	
	// Print staff information
	
	// Delete personel
	
	// Exit program
	
	// String exception handler
	// Generic (No special check)
	// ID Exception (yyXXXX) [also catch duplicates]
	// GPA format (x.x)
	// Department names (Mathematics, Engineering, English)
	// Status (part or full time)
	// Rank (Professor or Adjunct)
	// Sort (GPA or Name)
	private enum responseType {
		Generic, ID, GPA, CH, Department, Status, Rank, Sort, OptionSelect;
	}
	
	private static int recieveInt(responseType type, Scanner scanner) {
		int response = 0;
		boolean isValid = false;
		int invalidCount = 0;
		
		while (!isValid) {
			try {
				String stringResponse = scanner.nextLine();
				
				switch (type) {
				case CH:
					response = Integer.parseInt(stringResponse);
					
					if (response >= 0) {
						isValid = true;						
					}
					else {throw new InvalidEntryForType("Invalid response for Credit Hours"); }
					
					break;
					
				case OptionSelect:
					response = Integer.parseInt(stringResponse);
					
					if (response > 0 && response < 9) {
						isValid = true;						
					}
					else {throw new InvalidEntryForType("Invalid response for Menu option"); }
					
					break;
					
				default:
					System.out.println("Please input a response type!");
					break;
				}
			}
			catch (InvalidEntryForType m) {
				invalidCount++;
				if (invalidCount >= 3 && type != responseType.OptionSelect) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return 0;
				}
				else {
					System.out.println(m + "\n Please try again: ");
				}
			}
			
			catch (NumberFormatException e) {
				invalidCount++;
				if (invalidCount >= 3 && type != responseType.OptionSelect) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return 0;
				}
				else if (type == responseType.CH) {
					System.out.println("Invalid response for Credit Hours\n Please try again: ");
				}
				else {
					System.out.println(e + "\n Please try again: ");
				}			
			}
			
			catch (Exception e) {
				invalidCount++;
				if (invalidCount >= 3) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return -1;
				}
				else {
					System.out.println(e + "\n Please try again: ");
				}
			}
		}
		
		return response;
	}
	
	private static Double recieveDouble(responseType type, Scanner scanner) {
		double response = 0.0;
		boolean isValid = false;
		int invalidCount = 0; // Number of times an invalid option can be made.
		
		while (!isValid) {
			try {
				String stringResponse = scanner.nextLine();
				
				switch (type) {
				case GPA:
					if (isNumeric(stringResponse) && Double.parseDouble(stringResponse) >= 0.0 && Double.parseDouble(stringResponse) <= 4.0) {
						response = Double.parseDouble(stringResponse);
						isValid = true;						
					}
					else {throw new InvalidEntryForType("Invalid response for GPA"); }
					
					break;
					
				default:
					System.out.println("DEBUG Please input a response type!");
					break;
				}
			}
			catch (InvalidEntryForType m) {
				invalidCount++;
				if (invalidCount >= 3) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return -1.0;
				}
				else {
					System.out.println(m + "\n Please try again: ");
				}
			}
			
			catch (Exception e) {
				invalidCount++;
				if (invalidCount >= 3) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return -1.0;
				}
				else {
					System.out.println(e + "\n Please try again: ");
				}
			}
		}
		
		return response;
	}
	
	private static String recieveString(responseType type, Scanner scanner) {
		String response = null;
		boolean isValid = false;
		int invalidCount = 0; // Number of times an invalid option can be made.
		
		while (!isValid) {
			try {
				response = scanner.nextLine();
				
				switch (type) {
				case ID:
					String firstTwo = response.substring(0, 2);
					String lastFour = response.substring(2);
					if (!isNumeric(firstTwo) && isNumeric(lastFour) && response.length() == 6) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for ID! Must be LetterLetterDigitDigitDigitDigit");
					}
					
					break;
					
				case Department:
					if (response.compareToIgnoreCase("Engineering") == 0 || response.compareToIgnoreCase("Mathematics") == 0 || response.compareToIgnoreCase("English") == 0 ) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for Department! Must be Mathematics, Engineering, or English");
					}
					break;
					
				case Status:
					if (response.compareToIgnoreCase("Part-time") == 0 || response.compareToIgnoreCase("Full-time") == 0) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for Status! Must be part-time or full-time (including the '-')");
					}
					
					break;
					
				case Rank:
					if (response.compareToIgnoreCase("Professor") == 0 || response.compareToIgnoreCase("Adjunct") == 0) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for Rank! Must be professor or adjunct");
					}
					
					break;
					
				case Sort:
					if (response.compareToIgnoreCase("Name") == 0 || response.compareToIgnoreCase("GPA") == 0) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for Sort! Must be name or GPA");
					}
					
					break;
					
				default:
					isValid = true;
					break;
				}
			}
			catch (InvalidEntryForType m) {
				invalidCount++;
				if (invalidCount >= 3) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return null;
				}
				else {
					System.out.println(m + "\n Please try again: ");
				}
			}
			
			catch (Exception e) {
				invalidCount++;
				if (invalidCount >= 3) {
					System.out.println("Too many invalid responses. Returning to menu.");
					breakFromOptions = true;
					return null;
				}
				else {
					System.out.println(e + "\n Please try again: ");
				}
				
			}
		}
		
		return response.toLowerCase();
	}
	
	
	
	private static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		    
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	// Report
	// All faculty by department
	// All staff (unsorted)
	// All students (ask for GPA or Name)
}

class InvalidEntryForType extends Exception {
	public InvalidEntryForType (String errorMessage) {
		super(errorMessage);
	}
}

abstract class Person {
	private String fullName;
	private String id;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract void print();
	
	public Person(String fullName, String id) {
		this.fullName = fullName;
		this.id = id;
	}
	
	public Person() {
		fullName = null;
		id = null;
	}
}

abstract class Employee extends Person{
	private String department; // Mathematics, Engineering, English

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Employee(String fullName, String id, String department) {
		super(fullName, id);
		this.department = department;
	}
	
	
	
}

class Student extends Person {
	private double gpa;
	private int creditHours;
	//adding the additional requirement 
	private static final double tuition_per_credit = 236.45;
    	private static final double admin_fee = 52.00; 
    	private static final double discount = 0.25;

	//parameterized constructor to initialize a Student object with specific values
	public Student(String fullName, String id, double gpa, int creditHours){
		super(fullName, id);
		this.gpa = gpa;
		this.creditHours = creditHours;

	}
	//default constructor creates a Student object with default values
	public Student() {
		super("", "");
		this.gpa = 0.0;
		this.creditHours = 0; 

	}
	//setters and getters 
	public double getGpa(){
		return gpa;
	}

	public void setGpa(double gpa){
		this.gpa = gpa;
	}
	public int getCreditHours(){
		return creditHours;
	}
	public void setCreditHours(int creditHours){
		this.creditHours = creditHours; 
	}
	//calculation for student, but also apply discount and admin fee plus calculate the cost 
	public double calculateTuition(){
		double tuition = creditHours * tuition_per_credit + admin_fee;
		if (gpa >= 3.85){
			tuition = tuition * (1 - discount);
		}
		// Rounds down to hundredths place.
		tuition = tuition * 100.0;
		int holder = (int) tuition;
		tuition = (double) holder / 100.0;
		return tuition;
	}
	//print the invoice 
	public void printInvoice(){
		System.out.println("Invoice for student: " + getFullName()); 
		System.out.println("Student ID: " + getId()); 		
		System.out.println("GPA: " + gpa);
		System.out.println("Credit Hours: " + creditHours);
		System.out.println("Total: " + calculateTuition()); 
	}
	//override the print for the printinvoice 
	@Override
	public void print(){
		printInvoice();
	}
	//when you pick the option to type in the student information, it take to studentInfo to type all the informations
	public static Student studentInfo(Scanner scanner){
		System.out.print("\nEnter full name: ");
		String fullName = scanner.nextLine();

		String id = "";
		boolean validID = false;
		while(!validID) {
			scanner.nextLine();
			System.out.print("\nEnter student ID (e.g. ab1234): ");
			id = scanner.nextLine();
			try{
				Student temp = new Student();
				temp.setId(id);
				validID = true;
			} 
			catch (IllegalArgumentException e) {
				System.out.println("Invalid ID format. TRY AGAIN!");
			}
		}
	System.out.print("Enter GPA: ");
        double gpa = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Enter number of credit hours: ");
        int creditHours = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Student added.");
        return new Student(fullName, id, gpa, creditHours);
	}
}

class Faculty extends Employee {
    private String rank;

    public Faculty(String fullName, String id, String department, String rank) {
        super(fullName, id, department);
        this.rank = rank;
    }

    @Override
    public void print() {
        System.out.printf("%s\nID: %s\n%s, %s\n", getFullName(), getId(), getDepartment(), rank);
    }
}

class Staff extends Employee
{
	private String status; // Part-time or Full-time

	public Staff(String fullName, String id, String department, String status)
	{
		super(fullName, id, department);
		this.status = status;
	}

	@Override
	public void print() {
		System.out.printf("%s\nID: %s\n%s, %s\n", getFullName(), getId(), getDepartment(), status);
	}
}
