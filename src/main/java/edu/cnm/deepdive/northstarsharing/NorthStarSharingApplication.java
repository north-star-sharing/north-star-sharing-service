package edu.cnm.deepdive.northstarsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * Serves as the main entry point for the resource server application.
 */
@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class NorthStarSharingApplication {

  /**
   * Main application method launches Spring, passing the command-line arguments to it. These
   * arguments can be used to set (or override) application property values.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(NorthStarSharingApplication.class, args);
  }

}
