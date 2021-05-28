package com.larbotech.jpa.projections.repository;


import com.larbotech.jpa.projections.view.CustomEmployeeView;
import com.larbotech.jpa.projections.view.EmployeeByCloseProjectionView;
import com.larbotech.jpa.projections.view.EmployeeByOpenProjectionView;
import com.larbotech.jpa.projections.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<EmployeeByCloseProjectionView> findByFirstName(String firstName);

    List<EmployeeByOpenProjectionView> findByLastName(String lastName);

    // dynamic projection can return EmployeeByOpenProjectionRs or EmployeeByCloseProjectionRs
    <T> List<T> findByFirstName(String firstName, Class<T> tClass);

    @Query("select new com.larbotech.jpa.projections.view.CustomEmployeeView(e.firstName, e.lastName, e.department.name) " +
           "from Employee e")
    List<CustomEmployeeView> findAllWithCustomObject();

    @Query("select e.firstName as "+ CustomEmployeeView.FIRST_NAME +"," +
            " e.lastName as " + CustomEmployeeView.LAST_NAME + ", " +
            " d.name as " + CustomEmployeeView.DEPARTMENT_NAME +
            " from Employee e join e.department d")
    List<Map<String, Object>> findAllWithMapResult();

}
