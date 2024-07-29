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
    private final HolidayValidator holidayValidator;

    @Autowired
    public HolidayService(ModelMapper modelMapper, HolidayRepository holidayRepository, EmployeeRepository employeeRepository, HolidayValidator holidayValidator) {
        this.modelMapper = modelMapper;
        this.holidayRepository = holidayRepository;
        this.employeeRepository = employeeRepository;
        this.holidayValidator = holidayValidator;
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
        Holiday existingEntity = holidayRepository.findById(holidayId).orElseThrow(
                () -> new HolidayNotFoundException(holidayId.toString())
        );

        // Validate: A holiday must be cancelled at least 5 working days before the start date.
        holidayValidator.init(existingEntity);
        holidayValidator.canCanceledInAdvance(5 * 24);
        holidayRepository.deleteById(holidayId);

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

        // Validate:
        // 1. There should be a gap of at least 3 working days between holidays.
        // 2. A holiday must be planned at least 5 working days before the start date.
        holidayValidator.init(holiday);
        holidayValidator.canPlannedInAdvance(5 * 24);
        holidayValidator.hasGapWithOtherHolidays(3 * 24);

        holidayRepository.saveAndFlush(holiday);
        return convertToDto(holiday);
    }

    public HolidayDto updateHoliday(UUID holidayId, HolidayDto holidayDto) {
        Holiday existingEntity = holidayRepository.findById(holidayId).orElseThrow(
                () -> new HolidayNotFoundException(holidayId.toString())
        );
        Holiday holiday = convertToEntity(holidayDto);

        // Validate:
        // 1. There should be a gap of at least 3 working days between holidays.
        // 2. A holiday must be planned at least 5 working days before the start date.
        holidayValidator.init(existingEntity);
        holidayValidator.canPlannedInAdvance(5 * 24);
        holidayValidator.hasGapWithOtherHolidays(3 * 24);

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