package com.sdlab.sdlab.repository;

import com.sdlab.sdlab.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    public List<Attendance> findByLaboratoryId(int labId);
}
