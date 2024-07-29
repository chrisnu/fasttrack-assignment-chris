package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.controller.dto.HolidayDto;
import com.airfranceklm.fasttrack.assignment.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${apiPrefix}/holidays")
public class HolidaysApi {

    private final HolidayService holidayService;

    @Autowired
    public HolidaysApi(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public ResponseEntity<List<HolidayDto>> getHolidays(@RequestParam(name = "employeeId", required = false) String employeeId) {
        if (StringUtils.hasText(employeeId)) {
            return new ResponseEntity<>(holidayService.getHolidaysByEmployeeId(employeeId), HttpStatus.OK);
        }
        return new ResponseEntity<>(holidayService.getHolidays(), HttpStatus.OK);
    }

    @GetMapping(path = "/{holidayId}")
    public ResponseEntity<HolidayDto> getHolidaysPerEmployee(@PathVariable(name = "holidayId") String holidayId) {
        return new ResponseEntity<>(holidayService.getHolidayById(UUID.fromString(holidayId)), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayDto> addHoliday(@RequestBody HolidayDto holiday) {
        return new ResponseEntity<>(holidayService.addHoliday(holiday), HttpStatus.OK);
    }

    @DeleteMapping (path = "/{holidayId}")
    public ResponseEntity<HolidayDto> deleteHoliday(@PathVariable(name = "holidayId") String holidayId) {
        holidayService.deleteHolidayById(UUID.fromString(holidayId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping (path = "/{holidayId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayDto> updateHoliday(@RequestBody HolidayDto holiday, @PathVariable(name = "holidayId") String holidayId) {
        return new ResponseEntity<>(holidayService.updateHoliday(UUID.fromString(holidayId), holiday), HttpStatus.OK);
    }

}
