package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public void addCar(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   public Car getCar(int id) {
      return sessionFactory.getCurrentSession().get(Car.class, id);
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(String model, int series) {
      return (User) sessionFactory
              .getCurrentSession()
              .createQuery("from User where car = (from Car where model =: model and series =: series)")
              .setParameter("model", model)
              .setParameter("series", series)
              .stream().findFirst().orElse(null);
   }
}
