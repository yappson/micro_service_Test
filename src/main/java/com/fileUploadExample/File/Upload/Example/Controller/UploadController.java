package com.fileUploadExample.File.Upload.Example.Controller;

import com.fileUploadExample.File.Upload.Example.entity.Customer;
import com.fileUploadExample.File.Upload.Example.service.JReportService;

import net.sf.jasperreports.engine.JRException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fileUploadExample.File.Upload.Example.repo.custRepo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UploadController {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private custRepo repository;
    @Autowired
    private JReportService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadfile(@RequestParam("file") MultipartFile file) {

        try {
            String tempFilePath =  "D:\\SysBAckup\\Spring Batch Jobs\\File-Upload-Example\\src\\main\\java\\com\\fileUploadExample\\File\\Upload\\Example\\uploaded" + file.getOriginalFilename();
            File convFile = new File(tempFilePath);
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("pathToFile", tempFilePath)
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution run = jobLauncher.run(job, jobParameters);

            // Check if the job execution has any exceptions
            if (run.getAllFailureExceptions().size() > 0) {
                return ResponseEntity.status(303).body("There might be Some Data Not inserted. ");
            }

            return ResponseEntity.ok().body("Data inserted successfully");

        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            return ResponseEntity.ok().body(e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getAll")
    public List<Customer> getAddress() {
        List<Customer> customers = (List<Customer>) repository.findAll();
        return customers;
    }


    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        if (!format.equalsIgnoreCase("html") && !format.equalsIgnoreCase("pdf")) {
            throw new UnsupportedFormatException("Unsupported format: " + format+" Only allowed pdf/html formats");
        }
        return service.exportReport(format);
    }

}
