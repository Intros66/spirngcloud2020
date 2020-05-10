package com.ssp.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//打开断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//阀值，判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//快照时间，默认10秒 10秒以内阀值10以上 成功率60一下会熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//表示在一个统计窗口内有50%的请求处理失败，会触发熔断
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNum = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号： " + serialNum;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试...  id： " + id;
    }

}
