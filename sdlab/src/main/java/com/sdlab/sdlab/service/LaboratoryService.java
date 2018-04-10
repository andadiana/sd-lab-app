package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Laboratory;

import java.util.List;

public interface LaboratoryService {

    public List<Laboratory> getAllLaboratories();
    public Laboratory getLabById(int id);
    public Laboratory createLaboratory(Laboratory lab);
    public Laboratory updateLaboratory(Laboratory lab);
    public boolean deleteLaboratory(int id);

    //TODO implement this method
    //public boolean isLabValid(Laboratory lab);

}
