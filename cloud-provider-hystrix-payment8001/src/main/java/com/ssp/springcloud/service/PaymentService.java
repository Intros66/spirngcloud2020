package com.ssp.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_Ok(Integer id){
            return "线程池： " + Thread.currentThread().getName()+ "paymentInfo_OK,id: " + id + "\t"+ "O(∩_∩)O";
    }

    /**
     * 超时访问
     * @param id
     * @return
     */
    //3秒内走这个方法，超过3s走  paymentInfo_TimeOutHandler
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")})
    public String paymentInfo_TimeOut(Integer id){
       // int age = 20/0;
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName()+ "paymentInfo_TimeOut,id: " + id + "\t"+ "┭┮﹏┭┮" + " 耗时(s)" + timeNumber;
       // return "线程池： " + Thread.currentThread().getName()+ "paymentInfo_TimeOut,id: " + id + "\t"+ "┭┮﹏┭┮" + " 耗时(s)";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： " + Thread.currentThread().getName()+ "系统繁忙，请稍后再试: " + id + "\t"+ "/(ㄒoㄒ)/~~"  ;
    }

}
