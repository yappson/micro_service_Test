package com.fileUploadExample.File.Upload.Example.Config;

import com.fileUploadExample.File.Upload.Example.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProcessor.class);

    @Override
    public Customer process(Customer customer) throws Exception {
        try {
            if (StringUtils.isEmpty(customer.getEmail()) || StringUtils.isEmpty(customer.getFirstName()) || StringUtils.isEmpty(customer.getLastName())) {
                throw new ProcessingException("Missing data for customer: " + customer.toString());
            }
            // Validate email
            if (!customer.getEmail().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")) {
                throw new ProcessingException("Invalid email for customer: " + customer.getEmail());
            }

            if (customer.getCountry().equals("United States")) {
                return customer;
            } else {
                return null;
            }
        } catch (ProcessingException e) {

            CsvUtil.appendCustomerToCsv(customer, "D:\\SysBAckup\\Spring Batch Jobs\\File-Upload-Example\\src\\main\\java\\com\\fileUploadExample\\File\\Upload\\Example\\uploaded\\skipped_records.csv");
            logger.error("Error processing customer: {}", customer.toString(), e);
           throw  new ProcessingException("error");
        }
    }
}
