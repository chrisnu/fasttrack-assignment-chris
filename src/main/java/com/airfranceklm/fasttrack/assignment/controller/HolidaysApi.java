package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.controller.dto.HolidayDto;
import com.airfranceklm.fasttrack.assignment.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

}
