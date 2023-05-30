package com.project.FERMS.services;

import com.project.FERMS.models.Maintenance;
import com.project.FERMS.repositories.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public Maintenance addMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> listMaintenance() {
        return maintenanceRepository.findAll();
    }

    public Maintenance listMaintenanceById(Integer id) {
        return maintenanceRepository.findById(id).orElseThrow(()-> new IllegalStateException("Maintenance doesn't exist"));
    }

    public void deleteMaintenance(Integer id) {
        maintenanceRepository.deleteById(id);
    }

    public List<Maintenance> listMaintenanceByEquipment(int prodId) {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<Maintenance> productMaintenance = new ArrayList<>();

        for (Maintenance maintenance : maintenances) {
            int pId = maintenance.getProduct().getId();
            if (prodId == pId) {
                productMaintenance.add(maintenance);
            }
        }
        return productMaintenance;
    }

    public List<Maintenance> listMaintenanceByTechnician(int techId) {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<Maintenance> technicianMaintenances = new ArrayList<>();

        for (Maintenance maintenance : maintenances) {
            int tId = maintenance.getTechnician().getId();
            if(techId == tId) {
                technicianMaintenances.add(maintenance);
            }
        }
        return technicianMaintenances;
    }

}
