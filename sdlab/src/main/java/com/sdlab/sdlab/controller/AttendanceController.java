package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Attendance;
import com.sdlab.sdlab.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @RequestMapping(method = GET)
    public List<Attendance> getAllAttendance() {
        //TODO change return value to ResponseEntity?
        List<Attendance> attendance = attendanceService.getAllAttendance();
        for (Attendance a: attendance) {
            System.out.println(a);
        }
        return attendance;
    }

    @RequestMapping(method = GET, value = "/{attendanceId}")
    public ResponseEntity getAttendanceById(@PathVariable Integer attendanceId) {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        if (attendance == null) {
            //attendance not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(attendance);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createAttendance(@RequestBody Attendance attendance) {
        Attendance createdAttendance = attendanceService.createAttendance(attendance);
//        return ResponseEntity.status(HttpStatus.OK).body(createdAttendance);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = PUT, value = "/{attendanceId}")
    public ResponseEntity updateAttendance(@PathVariable Integer attendanceId, @RequestBody Attendance attendance) {
        Attendance attendance1 = attendanceService.getAttendanceById(attendanceId);
        if (attendance1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendance not found!");
        }
        attendance.setId(attendanceId);
        attendanceService.updateAttendance(attendance);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{attendanceId}")
    public ResponseEntity deleteAttendance(@PathVariable Integer attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
