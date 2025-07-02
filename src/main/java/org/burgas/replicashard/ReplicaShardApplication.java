package org.burgas.replicashard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ReplicaShardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplicaShardApplication.class, args);
	}

}
