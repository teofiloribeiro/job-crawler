package com.teofilo.jobs_crawler.crawler;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.teofilo.jobs_crawler.Main;
import com.teofilo.jobs_crawler.entities.JobKey;


public class DetailCrawler implements Runnable{

	private String url;
	
	public DetailCrawler(String url) {
		this.url = url;
	}
	
	public void run () {

		String page = new PageDownloader(url).downlad();
		Document doc = Jsoup.parse(page);
		
		Elements locationElement = doc.getElementsByClass("info-localizacao");
		String location = locationElement.get(0).text();
		
		Elements salaryElement = doc.getElementsByClass("icone-salario");
		String salaryText = salaryElement.get(0).nextElementSibling().child(1).text(); //Return R$ 1000.00
		//TODO REFACT
		
		Float salary = this.parseSalary(salaryText);
		
		Elements roleElement = doc.getElementsByClass("job-hierarchylist__item job-hierarchylist__item--level");
		String role = roleElement.text();
		
		JobKey jobKey = new JobKey(location, role);
		Float avarageSalary = salary;
		if(Main.jobSalaryMap.containsKey(jobKey)) {
			avarageSalary = (Main.jobSalaryMap.get(jobKey) + salary) / 2;
		}
		
		Main.jobSalaryMap.put(jobKey, avarageSalary);
		
		//System.out.printf("Location: %s Role: %s Salary Avarage: %.2f \n", location, role, avarageSalary);
		
		//System.out.println("Process ended");
		System.out.println("===================================");
		for ( Map.Entry<JobKey, Float> entry : Main.jobSalaryMap.entrySet()) {
			System.out.println(entry.getKey() + " | " + entry.getValue());
		}
		System.out.println("===================================");
	}
	
	/*
	 * CASES
	 * A combinar 
	 * R$ 4.000
	 * R$ 4000.00
	 * R$ 4.000.00
	 * R$ 4000.00 a 5000.00 get first
	 * 
	 * */	
	private Float parseSalary(String text) {
		Float result = 1000.00f;
		if(text.startsWith("R$ ") && text.length() > 8) {
			text = text.replace(".", "");
			result = Float.parseFloat(text.substring(3, 7));
		}

		return result;
	}
	
}
