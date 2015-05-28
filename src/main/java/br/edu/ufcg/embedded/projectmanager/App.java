package br.edu.ufcg.embedded.projectmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.ufcg.embedded.projectmanager.listener.ListenersController;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		ListenersController.startListeners();
	}
}
