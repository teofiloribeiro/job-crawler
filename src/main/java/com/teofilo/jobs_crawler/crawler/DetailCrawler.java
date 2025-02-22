package com.teofilo.jobs_crawler.crawler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.teofilo.jobs_crawler.Main;
import com.teofilo.jobs_crawler.entities.JobKey;
import com.teofilo.jobs_crawler.util.Semaphore;


public class DetailCrawler implements Runnable{

	private String url;
	private Semaphore semaphore;
	
	public DetailCrawler(String url, Semaphore semaphore) {
		this.url = url;
		this.semaphore = semaphore;
	}
	
	public void run () {
		try {
			String page = new PageDownloader(url).downlad();
			Document doc = Jsoup.parse(page);
			
			Elements locationElement = doc.getElementsByClass("info-localizacao");
			String location = locationElement.get(0).text();
			
			Elements salaryElement = doc.getElementsByClass("icone-salario");
			String salaryText = salaryElement.get(0).nextElementSibling().child(1).text(); //Return R$ 1000.00
			//TODO REFACT
			
			Float salary = parseSalary(salaryText);
			
			Elements roleElement = doc.getElementsByClass("job-hierarchylist__item job-hierarchylist__item--level");
			String role = roleElement.text();
			
			JobKey jobKey = new JobKey(location, role);
			Float avarageSalary = salary;
			
			this.semaphore.acquire();
			
			if(Main.jobSalaryMap.containsKey(jobKey)) {
				avarageSalary = (Main.jobSalaryMap.get(jobKey) + salary) / 2;
			}
			
			Main.jobSalaryMap.put(jobKey, avarageSalary);
			
			System.out.println("===================================");
			for ( Map.Entry<JobKey, Float> entry : Main.jobSalaryMap.entrySet()) {
				System.out.printf("%-90.90s%s%.2f%n",entry.getKey(),"| ",entry.getValue());
			}
			System.out.println("===================================");
						
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Erro Parsing Page!");
		} catch (InterruptedException e) {
			System.out.println("Semaphore Error");
			e.printStackTrace();
		} finally {
			this.semaphore.release();
		}
		
	}
	

	private static Float parseSalary(String text) {
		Float result = 1000f;
		Pattern p = Pattern.compile("([0-9]*([,.][0-9]*))");
		Matcher m = p.matcher(text);
		
		while(m.find()) {
			result = Float.parseFloat(m.group().replace(".", "").replace(",", ""));
			break;
		}
		
		return result;
	}
	
}
