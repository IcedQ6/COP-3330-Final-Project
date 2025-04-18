
package allClasses;
 //Ivan
 
public class Faculty extends Employee {
    private String rank;

    public Faculty(String fullName, String id, String department, String rank) {
        super(fullName, id, department);
        this.rank = rank;
    }

    @Override
    public void print() {
        System.out.printf("%s\nID: %s\n%s, %s\n", fullName, id, rank, department);
    }
}
