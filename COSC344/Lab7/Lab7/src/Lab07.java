/*
    File: Lab07.java
    March 2024
*/

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

/**
 * This program reads a user's database properties file and connects to MariaDB.
 *
 * @author Nigel Stanger
 */

public class Lab07 {

    /**
     * Use this to read from and write to the console.
     * See
     * <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Console.html>
     * and the examples below.
     */
    private static Console console = System.console();

    public static void main(String[] args) {
        DbProperties props = new DbProperties();
        String url = String.format("jdbc:mariadb://%s:%s/%s", props.getDbHost(), props.getDbPort(), props.getDbName());
        while (true) {
            try (Connection con = DriverManager.getConnection(url, props.getDbUser(), props.getDbPassword());) {
                // console.printf("Successfully connected to database '%s'.%n",
                // con.getCatalog());
                String response = console.readLine("1. Show employee information\n" +
                        "2. Update employee salary\n" +
                        "3. Delete an employee\n" +
                        "4. Generate a report for an employee\n" +
                        "5. Quit the app\n");

                if (response.equals("1")) {
                    try (PreparedStatement stmt = con.prepareStatement("select * from employee");) {
                        ResultSet result = stmt.executeQuery();
                        while (result.next()) {
                            // This isn't very pretty but it gets the job done. A real application should
                            // be a bit more elegant! (If nothing else it would probably have a GUI.)
                            console.printf(
                                    "Employee_Num: %d, First Name: %s, Middle_Name: %s, Surname: %s Birth_Date: %tF, Address: %s, Salary: %d, Gender %s, IRD_Num: %d, Department_Num: %d, Supervisor_Num: %d\n",
                                    result.getInt("Employee_Num"), result.getString("First_Name"),
                                    result.getString("Middle_Initial"),
                                    result.getString("Surname"), result.getDate("Birth_Date"),
                                    result.getString("Address"),
                                    result.getInt("Salary"), result.getString("Gender"), result.getInt("IRD_Num"),
                                    result.getInt("Department_Num"),
                                    result.getInt("Supervisor_Num"));
                            /*
                             * %d = integer
                             * %s = string
                             * %tF = date in ISO-8601 (YYYY-MM-DD) format
                             * %.2f = decimal with 2 decimal places
                             * %n = linebreak (platform-independent)
                             * See <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/
                             * Formatter.html>
                             * for the complete syntax.
                             */
                        }
                    }
                } else if (response.equals("2")) {

                    String updateStr = "update employee set Salary = ? where Employee_Num = ?";
                    try (PreparedStatement stmt = con.prepareStatement(updateStr);) {
                        Integer employeeNum = Integer.parseInt(console.readLine("Enter employee_num: "));
                        if (countRows(con, employeeNum, "employee", "Employee_Num") == 0) {
                            System.out.println("No employee with id " + employeeNum);
                            continue;
                        }
                        Integer salary = Integer.parseInt(console.readLine("Enter new salary: "));
                        stmt.setInt(1, salary);
                        stmt.setInt(2, employeeNum);
                        String confirmation = console.readLine("Confirm you want to delete [y/n]:");
                        if (confirmation.equalsIgnoreCase("Y")) {

                            Integer rows = stmt.executeUpdate();
                            if (rows == 1) {
                                System.out.println("Updated successful");
                            } else {
                                System.out.println("Update failed");
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                } else if (response.equals("3")) {

                    Integer employeeNum = Integer
                            .parseInt(console.readLine("Enter employee_num you want to delete: "));
                    if (countRows(con, employeeNum, "employee", "Employee_Num") == 0) {
                        System.out.println("No employee with id " + employeeNum);
                        continue;
                    }
                    String deleteDependent = "delete from dependent where Employee_Num = ?";
                    // Delete dependents
                    try (PreparedStatement stmt = con.prepareStatement(deleteDependent);) {

                        stmt.setInt(1, employeeNum);
                        int beforeDeleteCount = countRows(con, employeeNum, "dependent", "Employee_num");
                        ResultSet result = stmt.executeQuery();
                        int afterDeleteCount = countRows(con, employeeNum, "dependent", "Employee_num");
                        System.out.println(beforeDeleteCount - afterDeleteCount + " dependents were deleted");

                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    // Delete works_on
                    String deleteWorksOn = "delete from works_on where Employee_Num = ?";
                    try (PreparedStatement stmt = con.prepareStatement(deleteWorksOn);) {

                        stmt.setInt(1, employeeNum);
                        int beforeDeleteCount = countRows(con, employeeNum, "works_on", "Employee_num");
                        ResultSet result = stmt.executeQuery();
                        int afterDeleteCount = countRows(con, employeeNum, "works_on", "Employee_num");
                        System.out.println(beforeDeleteCount - afterDeleteCount + " works_on rows were deleted");

                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    // Set dep manager null.
                    String updateManagerNum = "update department set Manager_Num = null where Manager_Num = ?";
                    try (PreparedStatement stmt = con.prepareStatement(updateManagerNum);) {

                        stmt.setInt(1, employeeNum);
                        int countManagers = countRows(con, employeeNum, "department", "Manager_Num");
                        Integer rows = stmt.executeUpdate();
                        System.out.println("Employee was a manager for " + countManagers + " departments");
                        System.out.println(rows + " rows were set to no manager");

                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    // Set supervisor num to null
                    String updateSupervisors = "update employee set Supervisor_Num = null where Supervisor_Num = ?";
                    try (PreparedStatement stmt = con.prepareStatement(updateSupervisors);) {

                        stmt.setInt(1, employeeNum);
                        int countManagers = countRows(con, employeeNum, "employee", "Supervisor_Num");
                        Integer rows = stmt.executeUpdate();
                        System.out.println("Employee was a supervisor for " + countManagers + " employees");
                        System.out.println(rows + " rows were set have no supervisors");

                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    // Delete employee
                    String updateStr = "delete from employee where Employee_Num = ?";
                    try (PreparedStatement stmt = con.prepareStatement(updateStr);) {
                        stmt.setInt(1, employeeNum);
                        int numRowsBefore = countRows(con, employeeNum, "employee", "Employee_num");

                        ResultSet result = stmt.executeQuery();
                        int numRowsAfter = countRows(con, employeeNum, "employee", "Employee_num");
                        System.out.println(numRowsBefore - numRowsAfter + " employee rows deleted");

                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                } else if (response.equals("4")) {
                    Integer employeeNum = Integer
                            .parseInt(console.readLine("Enter a employee number: "));

                    if (countRows(con, employeeNum, "employee", "Employee_Num") == 0) {
                        System.out.println("No employee with id " + employeeNum);
                        continue;
                    }
                    try (PreparedStatement stmt = con.prepareStatement(
                            "select * from employee inner join department using (Department_Num) where Employee_Num=?");) {
                        stmt.setInt(1, employeeNum);
                        ResultSet result = stmt.executeQuery();
                        result.next();
                        System.out.println("-".repeat(5) + " Basic Employee Information " + "-".repeat(5));
                        // This isn't very pretty but it gets the job done. A real application should
                        // be a bit more elegant! (If nothing else it would probably have a GUI.)
                        console.printf(
                                "Employee Number: %d\nName: %s %s %s\nGender %s\nIRD_Num: %d\nDepartment: %s\n",
                                result.getInt("Employee_Num"),
                                result.getString("First_Name"),
                                result.getString("Middle_Initial"),
                                result.getString("Surname"),
                                result.getString("Gender"),
                                result.getInt("IRD_Num"),
                                result.getString("Name"));
                        /*
                         * %d = integer
                         * %s = string
                         * %tF = date in ISO-8601 (YYYY-MM-DD) format
                         * %.2f = decimal with 2 decimal places
                         * %n = linebreak (platform-independent)
                         * See <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/
                         * Formatter.html>
                         * for the complete syntax.
                         */

                    }
                    try (PreparedStatement stmt = con.prepareStatement(
                            "select * from employee inner join works_on using (Employee_Num) inner join project using (Project_Num) where Employee_Num=?");) {
                        stmt.setInt(1, employeeNum);
                        ResultSet result = stmt.executeQuery();
                        System.out.println("-".repeat(5) + " Projects Worked On " + "-".repeat(5));
                        while (result.next()) {

                            // This isn't very pretty but it gets the job done. A real application should
                            // be a bit more elegant! (If nothing else it would probably have a GUI.)
                            console.printf(
                                    "%s: %d\n",
                                    result.getString("Name"),
                                    result.getInt("Hours"));
                            /*
                             * %d = integer
                             * %s = string
                             * %tF = date in ISO-8601 (YYYY-MM-DD) format
                             * %.2f = decimal with 2 decimal places
                             * %n = linebreak (platform-independent)
                             * See <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/
                             * Formatter.html>
                             * for the complete syntax.
                             */
                        }

                    }
                } else if (response.equals("5")) {
                    break;
                }

            } catch (SQLException ex) {
                // This is not particularly sophisticated ;)
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }

    public static int countRows(Connection con, Integer employeeNum, String table, String colName) {
        String sqlStr = "select COUNT(*) from " + table + " where " + colName + " = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlStr);) {
            stmt.setInt(1, employeeNum);
            ResultSet result = stmt.executeQuery();
            result.next();
            return result.getInt("COUNT(*)");
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

} // end class TestLogin
