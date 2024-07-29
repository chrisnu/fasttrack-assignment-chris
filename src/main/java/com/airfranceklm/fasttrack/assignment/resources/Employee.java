package com.airfranceklm.fasttrack.assignment.resources;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee implements EntityCopy<Employee> {

    @Id
    @GeneratedValue(generator = "employee-id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "employee-id", strategy = "com.airfranceklm.fasttrack.assignment.resources.generators.EmployeeIdGenerator")
    private String id;
    private String name;

    @Override
    public Employee copyValue(Employee copy) {
        this.name = copy.name;
        return this;
    }
}
