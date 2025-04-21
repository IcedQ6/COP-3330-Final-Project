package allClasses;

// COP 3330 Final Project
// Luke Whipple, Daniel Scariti, Ivan Soliven, Michael Tran

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class FinalProject {
	
	private static boolean breakFromOptions;
	static ArrayList<Person> personnel;

	public static void main(String[] args) {
		personnel = new ArrayList<Person>();
		Scanner scn = new Scanner(System.in);
		boolean stillWorking = true;
		
		System.out.println("Welcome to the Personnel Management System!\n");
		
		// DEBUG OBJECTS
//		personnel.add(new Student("john doe", "jd7538", 3.9, 12));
//		personnel.add(new Student("Born Klmoph", "bk3702", 1.9, 15));
//		personnel.add(new Student("Joe Daddy", "jd8920", 1.0, 9));
//		personnel.add(new Student("Obama Barack", "ob9053", 2.7, 14));
//		personnel.add(new Faculty("Richard Crankshaw", "rc4832", "Engineering", "Professor"));
//		personnel.add(new Faculty("Joe Swanson", "js8640", "Mathematics", "Professor"));
//		personnel.add(new Faculty("Ron Killme", "rc8420", "English", "Adjunct"));
//		personnel.add(new Faculty("Rodney Boner", "rb7799", "Mathematics", "Professor"));
//		personnel.add(new Staff("Joey Baggins", "jb1234", "Mathematics", "Part-Time"));
//		personnel.add(new Staff("Scott Woz", "sw9055", "English", "Full-Time"));
//		personnel.add(new Staff("Mister Scary", "ms9990", "Engineering", "Part-Time"));
		
		// Main Menu
		while (stillWorking) {
			System.out.println("\nChoose one of the options:\r\n"
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
			
			// Adds a faculty member to the ArrayList
			case 1:
				Person nf = addFaculty(scn);
				// This skips adding the object if the user entered too many
				// invalid inputs and goes back to menu.
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				// This adds the object then returns to the menu.
				personnel.add(nf);
				break;
				
			// Adds a student to the array list
			case 2:
				Person np = addStudent(scn);
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				personnel.add(np);
				break;
				
			// Prints the info for a student by searching for their ID.
			case 3:
				System.out.println("Please enter the ID of the student you're looking for: ");
				printPersonnel(recieveString(responseType.IDsc, scn), "Student");
				break;
				
			// Prints the info for a faculty member by searching for their ID.
			case 4:
				System.out.println("Please enter the ID of the faculty member you're looking for: ");
				printPersonnel(recieveString(responseType.IDsc, scn), "Faculty");
				break;
				
			// Adds a staff member to the array list.
			case 5:
				Person ns = addStaff(scn);
				if (breakFromOptions) {
					breakFromOptions = false;
					break;
				}
				
				personnel.add(ns);
				break;
				
			// Prints the info for a faculty member by searching for their ID.
			case 6:
				System.out.println("Please enter the ID of the staff member you're looking for: ");
				printPersonnel(recieveString(responseType.IDsc, scn), "Staff");
				break;
				
			// Deletes a person from the system by ID.
			case 7:
				System.out.println("Please enter the ID of the person you want to delete: ");
				deletePersonnel(recieveString(responseType.IDsc, scn));
				break;
			
			// Exits the menu.
			case 8:
				stillWorking = false;
				break;
			}
		}
		// Make report
		
		
		System.out.println("\nOn to the report!\n");
		System.out.println("Would you like to sort students by GPA or by name?");
		
		String stuSort = recieveString(responseType.Sort, scn);

		try {
			FileWriter schoolReport = new FileWriter("report.txt");
		
			// Write all faculty members
			schoolReport.write("\n-----Faculty Members-----\n");
			
			ArrayList<Employee> facultyToPrint = new ArrayList<Employee>();
			
			for (Person i : personnel) {
				if (i.getType().compareToIgnoreCase("Faculty") == 0) facultyToPrint.add((Employee) i);
			}
			
			Collections.sort(facultyToPrint, new EmployeeSorter());
			
			int pos = 1;
			for (Employee i : facultyToPrint) {
				schoolReport.write("\n");
				schoolReport.write(pos + ". " + i);
				pos++;
			}
			
			
			// Write all staff members
			schoolReport.write("\n-----Staff Members-----\n");
			
			ArrayList<Employee> staffToPrint = new ArrayList<Employee>();
			
			for (Person i : personnel) {
				if (i.getType().compareToIgnoreCase("Staff") == 0) staffToPrint.add((Employee) i);
			}
			
			Collections.sort(staffToPrint, new EmployeeSorter());
			
			pos = 1; // reset pos
			for (Employee i : staffToPrint) {
				schoolReport.write("\n");
				schoolReport.write(pos + ". " + i);
				pos++;
			}
			
			
			// Write all students by selected order.
			schoolReport.write("\n-----Students (by " + stuSort + ")-----\n");
			
			ArrayList<Student> studentsToPrint = new ArrayList<Student>();
			
			for (Person i : personnel) {
				if (i.getType().compareToIgnoreCase("Student") == 0) studentsToPrint.add((Student) i);
			}
			
			if (stuSort.compareToIgnoreCase("GPA") == 0) { Collections.sort(studentsToPrint, new GPAStudentSorter());}
			else { Collections.sort(studentsToPrint, new NameStudentSorter());}
			
			pos = 1; // reset pos
			for (Student i : studentsToPrint) {
				schoolReport.write("\n");
				schoolReport.write(pos + ". " + i);
				pos++;
			}
			
			schoolReport.close();
		}
		catch (IOException e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
		
		
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
	private static void printPersonnel(String ID, String type) {
		for (Person i : personnel) {
			if (i.getType().compareToIgnoreCase(type) == 0) {
				if (i.getId().compareToIgnoreCase(ID) == 0) {
					i.print();
					return; // We know that there can't be duplicate IDs, so we can skip checking the rest.
				}
			}
		}
		System.out.println("Sorry, we could not find a " + type + " with the ID " + ID);
	}
	
	private static void deletePersonnel(String ID) {
		for (int i = 0; i < personnel.size(); i++) {
			if (personnel.get(i).getId().compareToIgnoreCase(ID) == 0) {
				personnel.remove(i);
				return; // We know that there can't be duplicate IDs, so we can skip checking the rest.
			}
		}
		System.out.println("Sorry, we could not find a person with the ID " + ID);
	}
	
	
	
	// String exception handler
	// Generic (No special check)
	// ID Exception (yyXXXX) [also catch duplicates]
	// GPA format (x.x)
	// Department names (Mathematics, Engineering, English)
	// Status (part or full time)
	// Rank (Professor or Adjunct)
	// Sort (GPA or Name)
	// Option Select (1-8)
	// IDsc (ID without checking for duplicates)
	private enum responseType {
		Generic, ID, GPA, CH, Department, Status, Rank, Sort, OptionSelect, IDsc;
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
				// Checks if entered ID is valid. Throws an error if the format is incorrect, it's the wrong length, or conflicts with another ID.
				case ID:
					String firstTwo = response.substring(0, 2);
					String lastFour = response.substring(2);
					if (!isNumeric(firstTwo) && isNumeric(lastFour) && response.length() == 6 && !checkForID(response)) {
						isValid = true;
					} else {
						throw new InvalidEntryForType ("Invalid entry for ID! Must be LetterLetterDigitDigitDigitDigit");
					}
					
					break;
				
				// Identical to the previous but doesn't check for ID conflicts.
				case IDsc:
					String first2 = response.substring(0, 2);
					String last4 = response.substring(2);
					if (!isNumeric(first2) && isNumeric(last4) && response.length() == 6) {
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
	
	// Checks all existing IDs with one in parameter. Returns true if match is found, false if not.
	private static boolean checkForID(String test) {
		for (Person i : personnel) {
			if (i.getId().compareToIgnoreCase(test) == 0) {
				return true;
			}
		}
		
		return false;
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

// Custom class for catching invalid responses.
class InvalidEntryForType extends Exception {

	/**
	 * 
	 */
	// I don't know what this does
	private static final long serialVersionUID = 91516995028493762L;

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
	public abstract String getType();
	
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
	
	public String getType() {
		return "Student";
	}
	
	@Override
	public String toString() {
		return getFullName() + "\nID: " + getId() + "\nGPA: " +  gpa +"\nCredit Hours: " + creditHours;
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
    
    public String getType() {
    	return "Faculty";
    }
    
    @Override
	public String toString() {
		return getFullName() + "\nID: " + getId() + "\n" +  getDepartment() +", " + rank;
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
	
	public String getType() {
		return "Staff";
	}
	
	@Override
	public String toString() {
		return getFullName() + "\nID: " + getId() + "\n" +  getDepartment() +", " + status;
	}
}

class EmployeeSorter implements Comparator<Employee> {
	
	public int compare (Employee f1, Employee f2) {
		
		// Compare by department
		int deptCompare = f1.getDepartment().compareToIgnoreCase(f2.getDepartment());
		
		// Then compares by name
		int nameCompare = f1.getFullName().compareToIgnoreCase(f2.getFullName());
		
		// Returns the result. First by department, then by name if those are the same.
		return (deptCompare == 0) ? nameCompare : deptCompare;
	}
}

class GPAStudentSorter implements Comparator<Student> {
	
	public int compare (Student s1, Student s2) {
		
		// Compares GPAs
		int gpaCompare = - (Double.compare(s1.getGpa(), s2.getGpa()));
		
		// Then names
		int nameCompare = s1.getFullName().compareToIgnoreCase(s2.getFullName());
		
		return (gpaCompare == 0) ? nameCompare : gpaCompare;
	}
}

class NameStudentSorter implements Comparator<Student> {
	
	public int compare (Student s1, Student s2) {
		
		// Compares GPAs
		int gpaCompare = - (Double.compare(s1.getGpa(), s2.getGpa()));
		
		// Then names
		int nameCompare = s1.getFullName().compareToIgnoreCase(s2.getFullName());
		
		return (nameCompare == 0) ? gpaCompare : nameCompare;
	}
}

