package hr.covidbatch;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import hr.covidbatch.model.PodaciPoZupanijama;
import hr.covidbatch.model.PodaciUkupno;
import hr.covidbatch.processors.PodaciPoZupanijamaProcessor;
import hr.covidbatch.processors.PodaciUkupnoProcessor;
import hr.covidbatch.readers.PodaciPoZupanijamaReader;
import hr.covidbatch.readers.PodaciUkupnoReader;
import hr.covidbatch.writers.PodaciPoZupanijamaWriter;
import hr.covidbatch.writers.PodaciUkupnoWriter;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EnableScheduling
public class PodaciUkupnoJobConfig implements BatchConfigurer {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("podaciUkupnoJob").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(step()).end().build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("podaciUkupnoStep").<List<PodaciUkupno>, List<PodaciUkupno>>chunk(1)
				.reader(itemReader()).processor(itemProcessor())
				.writer(itemWriter()).build();
	}
	
	@Bean
	public ItemReader<List<PodaciUkupno>> itemReader(){
		ItemReader<List<PodaciUkupno>> itemReader = new PodaciUkupnoReader();
		return itemReader;
	}
	
	@Bean
	public ItemProcessor<List<PodaciUkupno>,List<PodaciUkupno>> itemProcessor(){
		ItemProcessor<List<PodaciUkupno>, List<PodaciUkupno>> itemProcessor = new PodaciUkupnoProcessor();
		return itemProcessor;
	}
	
	@Bean
	public ItemWriter<? super List<PodaciUkupno>> itemWriter(){
		ItemWriter<? super List<PodaciUkupno>> itemWriter = new PodaciUkupnoWriter();
		return itemWriter;
	}
	
	@Scheduled(cron = "0 54 19 * * ?")
	public void schedule() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		jobLauncher.run(processJob(), new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

	PlatformTransactionManager transactionManager;
	  JobRepository jobRepository;
	  JobLauncher jobLauncher;
	  JobExplorer jobExplorer;

	  @Override
	  public JobRepository getJobRepository() {
	    return jobRepository;
	  }

	  @Override
	  public PlatformTransactionManager getTransactionManager() {
	    return transactionManager;
	  }

	  @Override
	  public JobLauncher getJobLauncher() {
	    return jobLauncher;
	  }

	  @Override
	  public JobExplorer getJobExplorer() {
	    return jobExplorer;
	  }

	  @PostConstruct
	  void initialize() throws Exception {

	    if (this.transactionManager == null) {
	      this.transactionManager = new ResourcelessTransactionManager();
	    }

	    MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.transactionManager);
	    jobRepositoryFactory.afterPropertiesSet();
	    this.jobRepository = jobRepositoryFactory.getObject();

	    MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
	    jobExplorerFactory.afterPropertiesSet();
	    this.jobExplorer = jobExplorerFactory.getObject();
	    this.jobLauncher = createJobLauncher();
	  }

	  private JobLauncher createJobLauncher() throws Exception {
	    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	    jobLauncher.setJobRepository(jobRepository);
	    jobLauncher.afterPropertiesSet();
	    return jobLauncher;
	  }
	  
	  
	  @Autowired
		private JobBuilderFactory zupanijeJobBuilderFactory;

		@Autowired
		private StepBuilderFactory zupanijeStepBuilderFactory;

		@Bean
		public Job podacipoZupanijamaJob() {
			return zupanijeJobBuilderFactory.get("podacipoZupanijamaJob").incrementer(new RunIdIncrementer()).listener(zupanijeListener())
					.flow(podacipoZupanijamaStep()).end().build();
		}

		@Bean
		public Step podacipoZupanijamaStep() {
			return zupanijeStepBuilderFactory.get("podacipoZupanijamaStep").<List<PodaciPoZupanijama>, List<PodaciPoZupanijama>>chunk(1)
					.reader(zupanijeItemReader()).processor(zupanijeItemProcessor())
					.writer(zupanijeItemWriter()).build();
		}
		
		@Bean
		public ItemReader<List<PodaciPoZupanijama>> zupanijeItemReader(){
			ItemReader<List<PodaciPoZupanijama>> itemReader = new PodaciPoZupanijamaReader();
			return itemReader;
		}
		
		@Bean
		public ItemProcessor<List<PodaciPoZupanijama>,List<PodaciPoZupanijama>> zupanijeItemProcessor(){
			ItemProcessor<List<PodaciPoZupanijama>, List<PodaciPoZupanijama>> itemProcessor = new PodaciPoZupanijamaProcessor();
			return itemProcessor;
		}
		
		@Bean
		public ItemWriter<? super List<PodaciPoZupanijama>> zupanijeItemWriter(){
			ItemWriter<? super List<PodaciPoZupanijama>> itemWriter = new PodaciPoZupanijamaWriter();
			return itemWriter;
		}
		
		@Scheduled(cron = "0 54 19 * * ?")
		public void zupanijeSchedule() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
			jobLauncher.run(podacipoZupanijamaJob(), new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
		}

		@Bean
		public JobExecutionListener zupanijeListener() {
			return new JobCompletionListener();
		}

		PlatformTransactionManager zupanijeTransactionManager;

		  @PostConstruct
		  void initialize1() throws Exception {

		    if (this.zupanijeTransactionManager == null) {
		      this.zupanijeTransactionManager = new ResourcelessTransactionManager();
		    }

		    MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.zupanijeTransactionManager);
		    jobRepositoryFactory.afterPropertiesSet();
		    this.jobRepository = jobRepositoryFactory.getObject();

		    MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
		    jobExplorerFactory.afterPropertiesSet();
		    this.jobExplorer = jobExplorerFactory.getObject();
		    this.jobLauncher = createJobLauncher();
		  }

		  private JobLauncher createJobLauncher1() throws Exception {
		    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		    jobLauncher.setJobRepository(jobRepository);
		    jobLauncher.afterPropertiesSet();
		    return jobLauncher;
		  }
}