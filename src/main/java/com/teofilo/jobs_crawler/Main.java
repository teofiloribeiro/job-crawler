package com.teofilo.jobs_crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teofilo.jobs_crawler.crawler.DetailCrawler;
import com.teofilo.jobs_crawler.crawler.JobCrawler;
import com.teofilo.jobs_crawler.entities.JobKey;

public class Main {
	
	public static final List <String> jobLinks = new ArrayList<String>();
	public static final Map<JobKey, Float> jobSalaryMap = new HashMap<JobKey, Float>();
	public static int pagesToCrawler = 5;

	public static void main(String[] args) throws InterruptedException {	
		System.out.println("Jobs Crawler was initialized!");
		
		
		
		for (int i=0; i < pagesToCrawler; i++) {
			JobCrawler crawler = new JobCrawler("https://www.vagas.com.br/vagas-de-?a%5B%5D=24&a%5B%5D=67&q=&pagina="+ i +"&_=1588188718216");
			new Thread(crawler, "JobCrawler - Thread: " + i).start();			
		}
		System.out.println("FOR ENDED");
		
		while (pagesToCrawler>0 || !jobLinks.isEmpty()) {
			System.out.println(".");
			if(!jobLinks.isEmpty()) {
				System.out.println("....");
				DetailCrawler crawler = new DetailCrawler(jobLinks.remove(0));		
				new Thread(crawler, "DetailCrawler").start();
			}
		}
		
		
		
	}
}
