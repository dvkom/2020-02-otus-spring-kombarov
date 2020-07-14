package ru.dvkombarov.app.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchCommands {

    private final Job libraryMigrationJob;
    private final JobLauncher jobLauncher;

    public BatchCommands(Job libraryMigrationJob,
                         JobLauncher jobLauncher) {
        this.libraryMigrationJob = libraryMigrationJob;
        this.jobLauncher = jobLauncher;
    }

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "start")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(
                libraryMigrationJob, new JobParametersBuilder()
                        .toJobParameters()
        );
    }
}
