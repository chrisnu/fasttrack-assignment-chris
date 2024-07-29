package com.airfranceklm.fasttrack.assignment.resources;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee implements EntityClone<Employee> {

    @Id
    private String id;
    private String name;

    @Override
    public Employee copyValue(Employee copy) {
        this.name = copy.name;

        return this;
    }
}
