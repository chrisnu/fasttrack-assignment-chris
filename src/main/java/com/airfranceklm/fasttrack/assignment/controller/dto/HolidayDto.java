package com.airfranceklm.fasttrack.assignment.controller.dto;

import com.airfranceklm.fasttrack.assignment.resources.HolidayStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HolidayDto {
    private UUID holidayId;
    private String employeeId;
    private String holidayLabel;
    private LocalDateTime startOfHoliday;
    private LocalDateTime endOfHoliday;
    private HolidayStatus status = HolidayStatus.REQUESTED;
}
