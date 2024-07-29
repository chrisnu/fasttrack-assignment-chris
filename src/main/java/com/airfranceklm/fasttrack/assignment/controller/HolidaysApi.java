package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/holidays")
public class HolidaysApi {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Holiday>> getHolidays() {
        throw new UnsupportedOperationException("TODO");
    }

}
