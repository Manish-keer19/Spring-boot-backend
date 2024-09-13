package net.engineeringdigest.journalApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class Car {

  @Autowired
  private Dog dog;

  @GetMapping("/ok")
  public String ok() {
    return dog.fun();

  }

}
