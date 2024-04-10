package com.fileUploadExample.File.Upload.Example.service;

import com.fileUploadExample.File.Upload.Example.entity.Customer;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fileUploadExample.File.Upload.Example.repo.custRepo;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JReportService {

    @Autowired
    private custRepo repository;


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Reports";
        List<Customer> customers = repository.findAll();
        System.out.println("List of Customers:");
        for (Customer customer : customers) {

            System.out.println(customer.toString());

        }

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:upload.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("list1" , customers);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\customers.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\customers.pdf");
        }

        return "\n" +
                "The report has been successfully generated. <a href='" + path + "'>Click here to access the report</a>.";

    }
}