package com.teofilo.crawler;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teofilo.crawler.entities.JobKey;
import com.teofilo.crawler.runnable.DetailCrawler;
import com.teofilo.crawler.runnable.JobCrawler;
import com.teofilo.crawler.synchronization.Semaphore;

public class Main {
	
	public static final List <String> jobLinks = new ArrayList<String>();
	public static final Map<JobKey, Float> jobSalaryMap = new HashMap<JobKey, Float>();
	public static int pagesToCrawler = 10;
	private static int detailId = 0;

	public static void main(String[] args) throws IOException {	
		Semaphore semaphore = new Semaphore();
		System.out.println("Jobs Crawler was initialized!");
		
		if(args.length < 1) {
			throw new InvalidParameterException("No arguments!");
		}
		
		String query = args[0];
		String params = query.replace(" ", "-");
		
		for (int i=0; i < pagesToCrawler; i++) {
			JobCrawler crawler = new JobCrawler("https://www.vagas.com.br/vagas-de-" + params + "?&q="+ params +"&pagina="+ i +"&_=" + System.currentTimeMillis());
			new Thread(crawler, "JobCrawler - Thread: " + i).start();
		}
		
		while (pagesToCrawler>0 || !jobLinks.isEmpty()) {
			System.out.println(".");
			if(!jobLinks.isEmpty()) {
				DetailCrawler crawler = new DetailCrawler(jobLinks.remove(0), semaphore);
				new Thread(crawler, "DetailCrawler" + detailId++).start();
			}
		}	
	}
}
