package edu.cnm.deepdive.northstarsharing.configuration;

import java.util.Random;
import org.springframework.beans.BeansException;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Implements methods to satisfy dependencies on classes implemented outside of Spring and this
 * application. Using the {@code @Bean} annotation, the instances returned by these methods are made
 * available for injection into other classes.
 */
@Configuration
public class Beans implements ApplicationContextAware {

  private static ApplicationContext context;

  /**
   * Constructs and returns an instance of {@link Random} (or a suitable subclass),
   */
  @Bean
  public Random random() {
    return new Random();
  }

  /**
   * Constructs and returns an {@link ApplicationHome}, reflecting this application's runtime
   * location context.
   */
  @Bean
  public ApplicationHome applicationHome() {
    return new ApplicationHome(this.getClass());
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;

  }

  public static <T> T bean(Class<T> beanType) {
    return context.getBean(beanType);
  }

  public static Object bean(String name) {
    return context.getBean(name);
  }
}
