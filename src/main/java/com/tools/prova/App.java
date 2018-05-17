package com.tools.prova;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class App {
    private static final String PATH_IN = "/dados/in/";
    private static final String PATH_OUT = "/dados/out/";

    public static void main(String[] args) {
        List<String> parameters = Arrays.asList(args);
        String pathIn = parameters.stream()
                .filter(p -> p.startsWith("--pathIn:"))
                .map(p -> p.replace("--pathIn:", "").trim())
                .findFirst()
                .orElse(PATH_IN);

        String pathOut = parameters.stream()
                .filter(p -> p.startsWith("--pathOut:"))
                .map(p -> p.replace("--pathOut:", "").trim())
                .findFirst()
                .orElse(PATH_OUT);

        App obj = new App();
        obj.run(pathIn, pathOut);
    }

    private void run(String pathIn, String pathOut) {

        String[] springConfig = {"spring/batch/jobs/jobs.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        try {
            List<Path> files = Files.list(Paths.get(pathIn))
                    .filter(f -> f.getFileName().toString().endsWith(".dat"))
                    .collect(Collectors.toList());

            files.forEach(path -> {
                try {
                    Job job = (Job) context.getBean("job");
                    JobParametersBuilder builder = new JobParametersBuilder();
                    builder.addString("input.file.name", pathIn.concat(path.getFileName().toString()));
                    builder.addString("output.file.name", pathOut.concat(path.getFileName().toString()).concat(".proc"));

                    JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
                    System.out.println("Exit Status : " + execution.getStatus());

                } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                    e.printStackTrace();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
