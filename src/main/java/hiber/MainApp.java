package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("Kevin", "De Bruyne", "cityonelove@mc.com", new Car("Chevrolet Cruze", 24366)));
      userService.add(new User("Elon", "Musk", "tesla@x.com", new Car("Cybertruck", 1)));
      userService.add(new User("Ryan", "Gosling", "neumer@drive.com", new Car("Chrysler 300C", 401)));
      userService.add(new User("Donald", "Trump", "usa@ufc.com", new Car("Cadillac Presidential Limo", 11)));

      userService.listUsers().forEach(System.out::println);

      try {
         System.out.println(userService.getUserByCar("Cybertruck", 1));
      } catch (org.hibernate.exception.DataException e) {
         System.out.println("Водителей с такой машиной несколько в базе данных!");
      }
      context.close();
   }
}
