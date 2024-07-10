package org.t1.timechecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.t1.timechecker.services.ExampleBean;

@Component
@SpringBootApplication
public class TimeCheckerApplication {

	private  ExampleBean exampleBean;

	public TimeCheckerApplication(ExampleBean exampleBean) {
		this.exampleBean = exampleBean;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		//Синхронная запись в БД
		for (int i = 0; i < 100; i++) {
			exampleBean.testMethodSync();
		}
		//Асинхронная запись в БД
		for (int i = 0; i < 100; i++) {
			exampleBean.testMethodAsync();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(TimeCheckerApplication.class, args);
	}
}
