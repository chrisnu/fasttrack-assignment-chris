package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.controller.dto.HolidayDto;
import com.airfranceklm.fasttrack.assignment.exceptions.EmployeeNotFoundException;
import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.resources.repositories.EmployeeRepository;
import com.airfranceklm.fasttrack.assignment.resources.repositories.HolidayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HolidayService(ModelMapper modelMapper, HolidayRepository holidayRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.holidayRepository = holidayRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<HolidayDto> getHolidays() {
        return holidayRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HolidayDto> getHolidaysByEmployeeId(String employeeId) {
        return holidayRepository.findByEmployeeId(employeeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Holiday convertToEntity(HolidayDto holidayDto) {
        Holiday holiday = modelMapper.map(holidayDto, Holiday.class);
        holiday.setId(holidayDto.getHolidayId());
        String employeeId = holidayDto.getEmployeeId();
        if (!employeeId.isBlank()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                    () -> new EmployeeNotFoundException(employeeId)
            );
            holiday.setEmployee(employee);
        }

        return holiday;
    }

    private HolidayDto convertToDto(Holiday holiday) {
        HolidayDto holidayDto = modelMapper.map(holiday, HolidayDto.class);
        holidayDto.setHolidayId(holiday.getId());
        holidayDto.setEmployeeId(holiday.getEmployee().getId());
        return holidayDto;
    }

}