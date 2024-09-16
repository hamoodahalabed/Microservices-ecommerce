package com.mohammad.camunda;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class CamundaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaApplication.class, args);
//
//		ExternalTaskClient client = ExternalTaskClient.create()
//				.baseUrl("http://localhost:8080/engine-rest")
//				.asyncResponseTimeout(20000)
//				.lockDuration(10000)
//				.maxTasks(1)
//				.build();
//		TopicSubscriptionBuilder subscriptionBuilder = client
//				.subscribe("test");
//
//		subscriptionBuilder.handler((externalTask, externalTaskService) -> {
//
//			System.out.println("Sorry, your tweet has been rejected: ");
//			Map<String, Object> variables = new HashMap<String, Object>();
//			variables.put("time", new Date());
//			externalTaskService.complete(externalTask, variables);
//		});
//		subscriptionBuilder.open();

	}

}
