package com.fileUploadExample.File.Upload.Example.repo;

import com.fileUploadExample.File.Upload.Example.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface custRepo extends JpaRepository<Customer,Integer> {
}
