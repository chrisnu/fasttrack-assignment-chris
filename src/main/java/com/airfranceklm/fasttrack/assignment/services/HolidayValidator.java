package com.airfranceklm.fasttrack.assignment.services;

import com.airfranceklm.fasttrack.assignment.exceptions.ValidationHolidayException;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.resources.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Validator class to validates Holiday object
 */
@Service
public class HolidayValidator implements Validator<Holiday> {

    private final HolidayRepository holidayRepository;
    private Holiday holiday;

    @Autowired
    public HolidayValidator(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public void init(Holiday holiday) {
        this.holiday = holiday;
        isValid();
    }

    void isValid() {
        if (holiday.getEndOfHoliday().isEqual(holiday.getStartOfHoliday()) || holiday.getEndOfHoliday().isBefore(holiday.getStartOfHoliday())) {
            throw new ValidationHolidayException("Start of holiday cannot be after end of holiday");
        }
    }

    void canPlannedInAdvance(long hoursInAdvance) {
        LocalDateTime now = LocalDateTime.now();
        if (holiday.getStartOfHoliday().isAfter(now) && holiday.getStartOfHoliday().isBefore(LocalDateTime.now().plusHours(hoursInAdvance))) {
            throw new ValidationHolidayException("Cannot be planned " + hoursInAdvance + " hours in advance");
        }
    }

    void canCanceledInAdvance(long hoursInAdvance) {
        LocalDateTime now = LocalDateTime.now();
        if (holiday.getStartOfHoliday().isAfter(now) && holiday.getStartOfHoliday().isBefore(now.plusHours(hoursInAdvance))) {
            throw new ValidationHolidayException("Cannot be canceled " + hoursInAdvance + " hours in advance");
        }
    }

    void hasGapWithOtherHolidays(long minGapHours, boolean excludeSelf) {
        LocalDateTime start = holiday.getStartOfHoliday().minusHours(minGapHours);
        LocalDateTime end = holiday.getEndOfHoliday().plusHours(minGapHours);

        int overlappingHolidays;
        if (excludeSelf) {
            overlappingHolidays = holidayRepository.overlappingHolidays(start, end, holiday.getId());
        } else {
            overlappingHolidays = holidayRepository.overlappingHolidays(start, end);
        }

        if (overlappingHolidays > 0) {
            throw new ValidationHolidayException("Overlapping with other holidays. Min gap between holidays is " + minGapHours + " hours");
        }
    }
}
