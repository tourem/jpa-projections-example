package com.larbotech.jpa.projections.repository;


import com.larbotech.jpa.projections.view.CustomEmployeeView;
import com.larbotech.jpa.projections.view.EmployeeByCloseProjectionView;
import com.larbotech.jpa.projections.view.EmployeeByOpenProjectionView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@Sql(scripts = "/projection-insert-data.sql")
@Sql(scripts = "/projection-clean-up-data.sql", executionPhase = AFTER_TEST_METHOD)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Close Projection
     * In Close Projection, the getter methods of interface match exactly with the getter methods of Entity’s properties.
     */
    @Test
    public void whenUsingClosedProjections_thenViewWithRequiredPropertiesIsReturned() {
        List<EmployeeByCloseProjectionView> employeeByCloseProjectionViews = employeeRepository.findByFirstName("A");
        assertThat(employeeByCloseProjectionViews).hasSize(2);
    }

    /**
     * Open Projection
     * In Open Projection we will create an interface with getter methods of selective properties only but in addition to that, we also use SpEL expression.
     * The SpEL expression will help us to define a new property from existing properties.
     */
    @Test
    public void whenUsingOpenProjections_thenViewWithRequiredPropertiesIsReturned() {
        List<EmployeeByOpenProjectionView> employeeByOpenProjectionViews = employeeRepository.findByLastName("B");
        assertThat(employeeByOpenProjectionViews).hasSize(1);

        EmployeeByOpenProjectionView employee = employeeByOpenProjectionViews.get(0);
        assertThat(employee.getFullName()).isEqualTo("A B");
        assertThat(employee.getDepartmentName()).isEqualTo("Development");
    }

    /**
     * Dynamic Projection
     * It is possible to define what projection type to return from the query method at runtime.
     * To achieve this, just include a new argument to query method of type Class and set it with your projection type
     */
    @Test
    public void whenUsingDynamicProjections_thenObjectWithRequiredPropertiesIsReturned() {
        List<EmployeeByCloseProjectionView> employeeByCloseProjectionViews = employeeRepository.findByFirstName("A", EmployeeByCloseProjectionView.class);
        assertThat(employeeByCloseProjectionViews).hasSize(2);
    }

    @Test
    public void whenUsingClassBasedCustomObjectProjections_thenDtoWithRequiredPropertiesIsReturned() {
        List<CustomEmployeeView> customEmployeeViews = employeeRepository.findAllWithCustomObject();
        assertThat(customEmployeeViews).hasSize(3);

    }

}