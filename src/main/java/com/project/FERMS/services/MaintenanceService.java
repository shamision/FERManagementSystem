package com.project.FERMS.services;

import com.project.FERMS.models.Maintenance;
import com.project.FERMS.models.Report;
import com.project.FERMS.repositories.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
            int pId = maintenance.getEquipment().getId();
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
            int tId = maintenance.getEmployee().getId();
            if(techId == tId) {
                technicianMaintenances.add(maintenance);
            }
        }
        return technicianMaintenances;
    }

    public List<Maintenance> generateCustomerReport(int custId) {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<Maintenance> filteredMaintenance = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate.minusDays(7);
        int cost = 0;
        Map<String, Object> parameters =new HashMap<String, Object>();
        List<Report> list = new ArrayList<>();
        String customerName = "";
        try {
            String filePath = "D:\\Final Year Project\\Final year project\\FERMS\\src\\main\\resources\\Invoice.jrxml";
            for (Maintenance maintenance : maintenances) {

                LocalDate maintenanceDate = maintenance.getDateCreated();

                int customerId = maintenance.getEquipment().getCustomer().getId();

                int customerCost = maintenance.getCost();

                if (custId == customerId && (maintenanceDate.isAfter(previousDate) || maintenanceDate.isEqual(previousDate)) && (maintenanceDate.isBefore(currentDate) || maintenanceDate.isEqual(currentDate))) {

                    filteredMaintenance.add(maintenance);

                    cost += customerCost;

                    parameters.put("customerName",maintenance.getEquipment().getCustomer().getName());

                    parameters.put("totalCost", cost);

                    LocalDateTime localDateTime = maintenance.getDateCreated().atStartOfDay();
                    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                    Date date = Date.from(instant);

                    Report report = new Report(maintenance.getEquipment().getProductName(), date, maintenance.getCost());


                    list.add(report);

                    customerName = maintenance.getEquipment().getCustomer().getName();
                }
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            JasperReport report1 = JasperCompileManager.compileReport(filePath);

            JasperPrint print = JasperFillManager.fillReport(report1, parameters, dataSource);

            JasperExportManager.exportReportToPdfFile(print,"D:\\Final Year Project\\Report\\"+customerName+" maintenance report.pdf");

            System.out.println("Report created!!");

        }catch (Exception e){
            e.printStackTrace();
        }
        return filteredMaintenance;

    }

}
