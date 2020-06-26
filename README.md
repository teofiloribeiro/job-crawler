# job-crawler
A crawler that crawl and calculate average salary jobs on [www.vagas.com.br](https://www.vagas.com.br)  

## Instalation

```sh
$ git clone https://github.com/teofiloribeiro/job-crawler.git  
$ cd job-crawler  
$ mvn clean install  
```

[Or only .jar file.](https://github.com/teofiloribeiro/job-crawler/tree/master/target)

## Using
You just need to pass your search as parameter and the crawler goes calculate the average salary by hierarchy and location.  

```sh
$ jar {your/jar/location}jobs-crawler-1.0.jar com.teofilo.crawler.Main "Vagas de TI"
```
