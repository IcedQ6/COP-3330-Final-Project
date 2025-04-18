package allClasses;

// Rageheavy's code (replace with real name)

public class Student extends Person {
	private double gpa;
	private int creditHours;
	//adding the additional requirement 
	private static final double tuition_per_credit = 236.45;
    	private static final double admin_fee = 52.00; 
    	private static final double discount = 0.25;

	//parameterized constructor to initialize a Student object with specific values
	public Student(String fullName, String id, double gpa, int creditHours){
		setFullName(fullName);
		setId(id);
		this.gpa = gpa;
		this.creditHours = creditHours;

	}
	//default constructor creates a Student object with default values
	public Student() {
		setFullName("");
		setId("");
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
		String fullName = scanner.nextLine;

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
