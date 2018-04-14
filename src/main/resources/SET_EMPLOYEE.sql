procedure set_employee(emploeeTab EMPLOYEE_TABLE);
/
procedure set_employee(emploeeTab EMPLOYEE_TABLE) is
 begin
  for i in 1..emploeeTab.count loop
   Insert into EMPLOYEES (EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,PHONE_NUMBER,
                         HIRE_DATE,JOB_ID,SALARY,COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID) values
                         (EMPLOYEES_SEQ.NEXTVAL,emploeeTab(i).FIRST_NAME,emploeeTab(i).LAST_NAME,emploeeTab(i).EMAIL,
                         emploeeTab(i).PHONE_NUMBER,emploeeTab(i).HIRE_DATE,emploeeTab(i).JOB_ID,emploeeTab(i).SALARY,
                         emploeeTab(i).COMMISSION_PCT,emploeeTab(i).MANAGER_ID,emploeeTab(i).DEPARTMENT_ID);
  end loop;
 end set_employee;