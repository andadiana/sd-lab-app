package com.sdlab.sdlab.repository;

import com.sdlab.sdlab.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Integer> {

    public List<Laboratory> getAllByCurriculaContaining(String keyword);
}
