package com.fileUploadExample.File.Upload.Example.Config;

import com.fileUploadExample.File.Upload.Example.entity.Customer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class CsvUtil {

    public static void appendCustomerToCsv(Customer customer, String filePath) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(customer.getId() + "," + customer.getFirstName() + "," + customer.getLastName() + "," + customer.getEmail() + ","+ customer.getGender() + "," + customer.getContactNo() + "," + customer.getCountry());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//id,firstName,lastName,email,gender,contactNo,country,dob