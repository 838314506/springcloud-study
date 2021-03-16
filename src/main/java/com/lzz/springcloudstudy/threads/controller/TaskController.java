package com.lzz.springcloudstudy.threads.controller;

import com.lzz.springcloudstudy.threads.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/asyncExecuter")
    public String asyncExecuter(){
        try {
            Future<String> t1 = taskService.asyncExecute1();
            Future<String> t2 = taskService.asyncExecute2();
            Future<String> t3 = taskService.asyncExecute3();
            while (true){
                if (t1.isDone() && t2.isDone() && t3.isDone()){
                    log.info("execute all task");
                    break;
                }
            }
            log.info("n" + t1.get() + "n" + t2.get() + "n" + t3.get());
        }catch (Exception e){
            log.error("error executing task for {}",e.getMessage());
        }
        return "ok";
    }
}
