package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

//
//    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
//        this.laboratoryRepository = laboratoryRepository;
//    }

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public Laboratory getLabById(int id) {
        Optional<Laboratory> res = laboratoryRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    public Laboratory createLaboratory(Laboratory lab) {
        System.out.println("Lab service create: " + lab);
        return laboratoryRepository.save(lab);
    }

    public Laboratory updateLaboratory(Laboratory lab) {
        Laboratory labToUpdate = laboratoryRepository.getOne(lab.getId());
        labToUpdate.setCurricula(lab.getCurricula());
        labToUpdate.setDate(lab.getDate());
        labToUpdate.setDescription(lab.getDescription());
        labToUpdate.setLabNumber(lab.getLabNumber());
        labToUpdate.setTitle(lab.getTitle());
        return laboratoryRepository.save(labToUpdate);
    }

    public boolean deleteLaboratory(int id) {
        Optional<Laboratory> labToDelete = laboratoryRepository.findById(id);
        //TODO check if lab exists
        laboratoryRepository.delete(labToDelete.get());
        return true;
    }
}
