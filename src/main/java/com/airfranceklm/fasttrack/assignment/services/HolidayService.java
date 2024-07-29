package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.controller.dto.HolidayDto;
import com.airfranceklm.fasttrack.assignment.exceptions.EmployeeNotFoundException;
import com.airfranceklm.fasttrack.assignment.exceptions.HolidayNotFoundException;
import com.airfranceklm.fasttrack.assignment.resources.Employee;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.resources.repositories.EmployeeRepository;
import com.airfranceklm.fasttrack.assignment.resources.repositories.HolidayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HolidayService implements CrudService<Holiday, HolidayDto> {

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

    public void deleteHolidayById(UUID holidayId) {
        holidayRepository.findById(holidayId).orElseThrow(
                () -> new HolidayNotFoundException(holidayId.toString())
        );

        holidayRepository.deleteById(holidayId);
    }

    public HolidayDto getHolidayById(UUID holidayId) {
        Holiday existingEntity = holidayRepository.findById(holidayId).orElseThrow(
                () -> new HolidayNotFoundException(holidayId.toString())
        );

        return convertToDto(existingEntity);
    }

    public HolidayDto addHoliday(HolidayDto holidayDto) {
        Holiday holiday = convertToEntity(holidayDto);

        holidayRepository.saveAndFlush(holiday);
        return convertToDto(holiday);
    }

    public HolidayDto updateHoliday(UUID holidayId, HolidayDto holidayDto) {
        Holiday existingEntity = holidayRepository.findById(holidayId).orElseThrow(
                () -> new HolidayNotFoundException(holidayId.toString())
        );
        Holiday holiday = convertToEntity(holidayDto);

        existingEntity = holidayRepository.saveAndFlush(existingEntity.copyValue(holiday));
        return convertToDto(existingEntity);
    }

    @Override
    public Holiday convertToEntity(HolidayDto dto) {
        Holiday holiday = modelMapper.map(dto, Holiday.class);
        holiday.setId(dto.getHolidayId());
        String employeeId = dto.getEmployeeId();
        if (!employeeId.isBlank()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                    () -> new EmployeeNotFoundException(employeeId)
            );
            holiday.setEmployee(employee);
        }

        return holiday;
    }

    @Override
    public HolidayDto convertToDto(Holiday entity) {
        HolidayDto holidayDto = modelMapper.map(entity, HolidayDto.class);
        holidayDto.setHolidayId(entity.getId());
        holidayDto.setEmployeeId(entity.getEmployee().getId());
        return holidayDto;
    }

}