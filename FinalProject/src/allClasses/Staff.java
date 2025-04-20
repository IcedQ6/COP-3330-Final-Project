package allClasses;

// Daniel Scariti

public class Staff extends Employee
{
	private String status; // Part-time or Full-time

	public Staff(String fullName, String id, String department, String status)
	{
		super(fullName, id, department);
		this.status = status;
	}

	@Override
	public void print() {
		System.out.printf("%s\nID: %s\n%s, %s\n", fullName, id, department, status);
	}
}
