package com.project.FERMS.controllers;

import com.project.FERMS.models.Maintenance;
import com.project.FERMS.services.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private final MaintenanceService maintenanceService;

    @PostMapping(path = "/register")
    public ResponseEntity registerMaintenance(@RequestBody Maintenance maintenance) {
        maintenanceService.addMaintenance(maintenance);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    public List<Maintenance> listMaintenance() {
        return maintenanceService.listMaintenance();
    }

    @GetMapping(path = "/display/{id}")
    public Maintenance listMaintenanceById(@PathVariable Integer id) {
        return maintenanceService.listMaintenanceById(id);
    }

    @PutMapping(path = "/update/{id}")
    public Maintenance updateMaintenance(@PathVariable Integer id,@RequestBody Maintenance maintenance) {
        maintenance.setId(id);
        return maintenanceService.addMaintenance(maintenance);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteMaintenance(@PathVariable Integer id) {
        maintenanceService.deleteMaintenance(id);
    }

    @GetMapping(path = "/EquipmentMaintenance/{id}")
    public List<Maintenance> ListMaintenanceByProduct(@PathVariable Integer id) {
        return maintenanceService.listMaintenanceByEquipment(id);
    }

    @GetMapping(path = "/technicianMaintenance/{id}")
    public List<Maintenance> ListMaintenanceByTechnician(@PathVariable Integer id) {
        return maintenanceService.listMaintenanceByTechnician(id);
    }

}
