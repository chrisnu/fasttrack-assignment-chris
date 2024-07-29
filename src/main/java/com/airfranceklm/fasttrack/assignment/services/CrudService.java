package com.airfranceklm.fasttrack.assignment.services;

public interface CrudService<T, U> {
    T convertToEntity(U dto);

    U convertToDto(T entity);
}
