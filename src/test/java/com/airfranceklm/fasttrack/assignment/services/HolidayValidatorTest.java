package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.exceptions.ValidationHolidayException;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.resources.repositories.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolidayValidatorTest {
    @Mock
    HolidayRepository holidayRepository;
    @InjectMocks
    HolidayValidator holidayValidator;

    @Test
    void isValid() {
        Holiday holiday = new Holiday();
        LocalDateTime now = LocalDateTime.now();

        holiday.setStartOfHoliday(now);
        holiday.setEndOfHoliday(now);
        assertThrows(ValidationHolidayException.class, () -> holidayValidator.init(holiday));

        assertThrows(ValidationHolidayException.class, () -> {
            holiday.setStartOfHoliday(now.plusDays(1));
            holidayValidator.init(holiday);
        });

        assertDoesNotThrow(() -> {
            holiday.setEndOfHoliday(now.plusDays(15));
            holidayValidator.init(holiday);
        });
    }

    @Test
    void canPlannedInAdvance() {
        Holiday holiday = new Holiday();
        LocalDateTime now = LocalDateTime.now();

        holiday.setStartOfHoliday(now);
        holiday.setEndOfHoliday(now.plusDays(1));
        assertDoesNotThrow(() -> {
            holidayValidator.init(holiday);
            holidayValidator.canPlannedInAdvance(15);
        });

        assertThrows(ValidationHolidayException.class, () -> {
            holiday.setStartOfHoliday(now.plusDays(5));
            holiday.setStartOfHoliday(now.plusDays(10));
            holidayValidator.init(holiday);
            holidayValidator.canPlannedInAdvance(15);
        });

        assertDoesNotThrow(() -> {
            holiday.setStartOfHoliday(now.minusDays(15));
            holiday.setStartOfHoliday(now.minusDays(10));
            holidayValidator.init(holiday);
            holidayValidator.canPlannedInAdvance(15);
        });
    }

    @Test
    void canCanceledInAdvance() {
        Holiday holiday = new Holiday();
        LocalDateTime now = LocalDateTime.now();

        holiday.setStartOfHoliday(now);
        holiday.setEndOfHoliday(now.plusDays(1));
        assertDoesNotThrow(() -> {
            holidayValidator.init(holiday);
            holidayValidator.canCanceledInAdvance(15);
        });

        assertThrows(ValidationHolidayException.class, () -> {
            holiday.setStartOfHoliday(now.plusDays(5));
            holiday.setStartOfHoliday(now.plusDays(10));
            holidayValidator.init(holiday);
            holidayValidator.canCanceledInAdvance(15);
        });

        assertDoesNotThrow(() -> {
            holiday.setStartOfHoliday(now.minusDays(15));
            holiday.setStartOfHoliday(now.minusDays(10));
            holidayValidator.init(holiday);
            holidayValidator.canCanceledInAdvance(15);
        });
    }

    @Test
    void hasGapWithOtherHolidays() {
        Holiday holiday = new Holiday();
        LocalDateTime now = LocalDateTime.now();

        holiday.setStartOfHoliday(now);
        holiday.setEndOfHoliday(now.plusDays(1));
        holidayValidator.init(holiday);
        assertThrows(ValidationHolidayException.class, () -> {
            when(holidayRepository.overlappingHolidays(any(), any())).thenReturn(5);
            holidayValidator.hasGapWithOtherHolidays(2 * 24);
        });

        assertDoesNotThrow(() -> {
            when(holidayRepository.overlappingHolidays(any(), any())).thenReturn(0);
            holidayValidator.hasGapWithOtherHolidays(2 * 24);
        });
    }
}