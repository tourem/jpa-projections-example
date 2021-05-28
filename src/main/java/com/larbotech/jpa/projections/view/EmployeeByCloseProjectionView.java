package com.larbotech.jpa.projections.view;


public interface EmployeeByCloseProjectionView {
    Long getId();

    String getFirstName();

    String getLastName();

    DepartmentRs getDepartment();

    interface DepartmentRs {
        String getName();
    }
}
