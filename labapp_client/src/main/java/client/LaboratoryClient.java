package client;

import model.Laboratory;

import java.util.List;

public interface LaboratoryClient {

    public List<Laboratory> getLaboratories();
    public void createLaboratory(Laboratory lab);
    public void updateLaboratory(Laboratory lab);
    public void deleteLaboratory(int id);
}
