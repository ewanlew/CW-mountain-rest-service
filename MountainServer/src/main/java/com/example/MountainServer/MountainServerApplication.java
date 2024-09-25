/**
 * Class just starts the server, nothing else
 *
 * @Author Ewan Lewis
 */

package com.example.MountainServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MountainServerApplication {

	/**
	 * Starts the REST server
	 * @param args args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MountainServerApplication.class, args);
	}

}
