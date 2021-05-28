package com.larbotech.jpa.projections.view;

import org.springframework.beans.factory.annotation.Value;


public interface EmployeeByOpenProjectionView {
    Long getId();

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    @Value("#{target.department.name}")
    String getDepartmentName();
}
