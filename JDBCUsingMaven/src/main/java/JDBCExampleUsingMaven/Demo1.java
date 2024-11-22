package JDBCExampleUsingMaven;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class Demo1 {

	public static void createTable(Statement s) throws SQLException {
		s.execute("create table if not exists student(id int ,name varchar(50),age int)");
		System.out.println("Table created successfully!!!");
	}

	public static void insertData(Statement s) throws SQLException {
//		take input from user 
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter student id : ");
		int id = sc.nextInt();

		System.out.println("Enter student name : ");
		String name = sc.next();

		System.out.println("Enter student age : ");
		int age = sc.nextInt();

//		place the user input values into the query 
		String sql = String.format("insert into student values('%d','%s','%d')", id, name, age);

		s.executeUpdate(sql);
		System.out.println("Data successfully inserted into the table ");

		sc.close();
	}

	public static void fetchData(Statement s) throws SQLException {
		ResultSet rs = s.executeQuery("select * from student");
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

	public static void updateData(Statement s) throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id whose name is to update : ");
		int id = sc.nextInt();

		System.out.println("Enter new name : ");
		String name = sc.next();

		String sql = String.format("update student set name='%s' where id='%d'", name, id);
		s.executeUpdate(sql);
		System.out.println("Student data updated successfully!!!");
		sc.close();

	}

	public static void deleteData(Statement s) throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id to delete : ");
		int id = sc.nextInt();

		String sql = String.format("delete from student where id='%d'", id);
		s.executeUpdate(sql);
		System.out.println("Student data deleted successfully!!!");
		sc.close();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Run JDBC code into maven folder
//		load and register the mysql jdbc driver in the pom.xml file 

//		load the driver
		Class.forName("com.mysql.cj.jdbc.Driver");

//		Make the connection
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mavenCodes", "root", "root");

//		create statement or PreparedStatement to execute queries 
		Statement s = con.createStatement();

		System.out.println("1.Insert Data ");
		System.out.println("2.display Data ");
		System.out.println("3.Update Data ");
		System.out.println("4.Delete Data ");
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter choice : ");
		int choice=sc.nextInt();
		switch (choice) {
		case 1:
			insertData(s);
			System.out.println();
			break;
		case 2:
			System.out.println("Fetching details.....");
			fetchData(s);
			break;
		case 3:
			updateData(s);
			System.out.println();
			break;
		case 4:
			deleteData(s);
			break;
			
		default:
			System.out.println("Enter choice between (1-4)");
		}
		
		sc.close();
	}

}
