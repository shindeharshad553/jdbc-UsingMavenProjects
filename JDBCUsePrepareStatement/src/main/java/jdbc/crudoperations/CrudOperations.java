package jdbc.crudoperations;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.Connection;

public class CrudOperations {

	public static void createTable(Connection con) throws SQLException {
		PreparedStatement ps = con
				.prepareStatement("create table if not exists student(id int ,name varchar(50),age int)");
		ps.execute();
	}

	public static void insertData(Connection con) throws SQLException {
//		take input from user 
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter student id : ");
		int id = sc.nextInt();

		System.out.println("Enter student name : ");
		String name = sc.next();

		System.out.println("Enter student age : ");
		int age = sc.nextInt();

//		place the user input values into the query 
		PreparedStatement ps = con.prepareStatement("Insert into student values (?,?,?)");
		ps.setInt(1, id);
		ps.setString(2, name);
		ps.setInt(3, age);
		ps.executeUpdate();
		System.out.println("Data successfully inserted into the table ");

		sc.close();
	}

	public static void fetchData(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement("select * from student");
		ResultSet rs = ps.executeQuery();
		System.out.println("ID\t" + "NAME\t" + "AGE");
		while (rs.next()) {
//			print id 
			System.out.print(rs.getInt(1) + "\t");

//			print name
			System.out.print(rs.getString(2) + "\t");

//			print age 
			System.out.print(rs.getInt(3));
			System.out.println();
		}
	}

	public static void updateData(Connection con) throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id whose name is to update : ");
		int id = sc.nextInt();

		System.out.println("Enter new name : ");
		String name = sc.next();

		PreparedStatement ps = con.prepareStatement("update student set name=? where id=?");
		ps.setString(1, name);
		ps.setInt(2, id);
		ps.executeUpdate();
		System.out.println("Student data updated successfully!!!");
		sc.close();

	}

	public static void deleteData(Connection con) throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id to delete : ");
		int id = sc.nextInt();

		PreparedStatement ps = con.prepareStatement("delete from student where id=?");
		ps.setInt(1, id);
		ps.executeUpdate();
		System.out.println("Student data deleted successfully!!!");
		sc.close();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mavenCodes", "root", "root");
		if (con != null) {
//			Run JDBC code into maven folder
//			load and register the mysql jdbc driver in the pom.xml file 

			System.out.println("1.Insert Data ");
			System.out.println("2.display Data ");
			System.out.println("3.Update Data ");
			System.out.println("4.Delete Data ");

			Scanner sc = new Scanner(System.in);
			System.out.println("Enter choice : ");
			int choice = sc.nextInt();
			
//			used to create a table in database 
			createTable(con);
			
			
			switch (choice) {
			case 1:
				insertData(con);
				System.out.println();
				break;
			case 2:
				System.out.println("Fetching details.....");
				fetchData(con);
				break;
			case 3:
				updateData(con);
				System.out.println();
				break;
			case 4:
				deleteData(con);
				break;

			default:
				System.out.println("Enter choice between (1-4)");
			}

			sc.close();
		}
	}
}
