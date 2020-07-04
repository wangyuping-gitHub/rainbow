package org.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class HelloService {
    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "helloFallback",commandKey = "helloKey")
    public String helloService(){
        long start = System.currentTimeMillis();

        String result = restTemplate.getForEntity("http://service-provider/hello",String.class).getBody();
        long end = System.currentTimeMillis();
        logger.info("Spend time : " + (end-start));
        return result;
    }
    public String helloFallback(){
        return "error";
    }
}
