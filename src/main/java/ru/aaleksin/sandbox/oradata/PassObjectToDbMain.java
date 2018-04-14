package ru.aaleksin.sandbox.oradata;

import oracle.sql.*;
import ru.aaleksin.sandbox.AppConfig;
import ru.aaleksin.sandbox.entities.Employee;
import ru.aaleksin.sandbox.interfaces.IEmployeeService;
import ru.aaleksin.sandbox.services.RandomEmployeeService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by aaleksin on 24.03.2018.
 */
public class PassObjectToDbMain {

    public static final String EMPLOYEE_ROW_TYPE_DESCRIPTOR = "EMPLOYEE_ROW_TYPE";
    public static final String EMPLOYEE_TABLE_DESCRIPTOR = "EMPLOYEE_TABLE";
    public static final String SET_EMPLOYEE_SQL = "{ call common_pkg.set_employee(?) }";
    private static IEmployeeService employeeService = new RandomEmployeeService();

    public static void main(String[] args)
            throws ClassNotFoundException, SQLException
    {
        DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
        String url = AppConfig.URL;
        Connection conn =
                DriverManager.getConnection(url,AppConfig.LOGIN,AppConfig.PASSWORD);
        conn.setAutoCommit(false);

        StructDescriptor recDescriptor =
                StructDescriptor.createDescriptor(EMPLOYEE_ROW_TYPE_DESCRIPTOR,conn);


        ArrayDescriptor arrayDescriptor =
                ArrayDescriptor.createDescriptor(EMPLOYEE_TABLE_DESCRIPTOR, conn);

        CallableStatement stmt =
                conn.prepareCall(SET_EMPLOYEE_SQL);

        Object[] array_of_records = new Object[5];
        Object[] java_record_array   = new Object[11];


        for (Integer i=0; i< 5; i++) {
            Employee employee = employeeService.getEmployee();
            java_record_array[0] = employee.getCommissionPct();
            java_record_array[1] = employee.getDepartmentId();
            java_record_array[2] = null;
            java_record_array[3] =  employee.getFirstName();
            java_record_array[4] = new DATE();
            java_record_array[5] = employee.getJobId();
            java_record_array[6] = employee.getLastName();
            java_record_array[7] = employee.getManagerId();
            java_record_array[8] = employee.getPhone();
            java_record_array[9] = employee.getSalary();
            java_record_array[10] = employee.getEmail();

            STRUCT oracle_record = new STRUCT(recDescriptor, conn, java_record_array);
            array_of_records[i] = oracle_record;
        }

        ARRAY oracle_array = new ARRAY(arrayDescriptor, conn, array_of_records);

        stmt.setObject(1, oracle_array);
        stmt.execute();
        conn.commit();
    }
}
