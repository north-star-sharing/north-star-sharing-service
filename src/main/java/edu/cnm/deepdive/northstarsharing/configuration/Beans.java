package edu.cnm.deepdive.northstarsharing.configuration;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Extra beans for file storage.
 */
@Configuration
public class Beans {

  /**
   * @return a random number generator bean
   */
  @Bean
  public Random random() {
    return new SecureRandom();
  }

  /**
   * @return an ApplicationHome bean
   */
  @Bean
  public ApplicationHome applicationHome() {
    return new ApplicationHome(this.getClass());
  }
}
