package phase3Project;

public class RestAssuredTestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssuredJava a = new RestAssuredJava("http://localhost:8088/employees");
		
		//Get All Employees
		a.getAllEmployees();
		int id_created = a.createNewEmployee("John", "Abraham","john@zyx.com", "10100");
		a.updateEmployee(id_created, "Tom","Perera");
		a.getEmployeeByID(id_created, 200);
		a.deleteEmployeeByID(id_created);
		a.getEmployeeByID(id_created, 400);
		a.getAllEmployees();

	}

}
