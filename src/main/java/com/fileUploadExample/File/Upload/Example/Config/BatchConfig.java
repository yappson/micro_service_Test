package com.fileUploadExample.File.Upload.Example.Config;

import com.fileUploadExample.File.Upload.Example.repo.custRepo;
import com.fileUploadExample.File.Upload.Example.entity.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
public class BatchConfig {



    // Batch Step
    @Bean
    public Job runJob(JobRepository jobRepository, JobCompletionNotificationImpl listener,Step steps) {
        return new JobBuilder("Job",jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }


// creating Step

    @Bean
    public Step step1(JobRepository jobRepository, ItemReader<Customer> reader, ItemProcessor<Customer,Customer> processor,
                      ItemWriter<Customer> writer, PlatformTransactionManager transactionManager) {

        return new StepBuilder("step1",jobRepository)
                .<Customer,Customer>chunk(10,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .skipLimit(Integer.MAX_VALUE)
                .build();
    }



    // Creating Reader
    @Bean
    @StepScope
    public FlatFileItemReader<Customer> reader(@Value("#{jobParameters['pathToFile']}") String pathToFile){
        return new FlatFileItemReaderBuilder<Customer>()
                .name("itemReader")
                .resource(new FileSystemResource(pathToFile))
                .linesToSkip(1)
                .delimited()
                .names("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob")
                .targetType(Customer.class)
                .build();
    }

    // Processing The data
    @Bean
    public ItemProcessor<Customer, Customer> itemProcessor(){

        return new CustomerProcessor();
    }


    // Create a Writer
    @Bean
    public RepositoryItemWriter<Customer> writer(custRepo custRepo){
        return new RepositoryItemWriterBuilder<Customer>()
                .methodName("save")
                .repository(custRepo)
                .build();
    }



}



