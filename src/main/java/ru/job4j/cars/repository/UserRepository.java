package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE User SET login = :fLog, password = :fPas WHERE id = :fId")
                    .setParameter("fLog", user.getLogin())
                    .setParameter("fPas", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        }  catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE from User where id = :fid ")
                    .setParameter("fid", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as i ORDER BY i.id ASC", User.class);
            session.getTransaction().commit();
        }  catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query.getResultList();
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as i where i.id = :fId", User.class);
            query.setParameter("fId", userId);
            session.getTransaction().commit();
        }  catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query.uniqueResultOptional();
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as i where i.login LIKE :fKey", User.class);
            query.setParameter("fKey", "%" + key + "%");
            session.getTransaction().commit();
        }  catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query.getResultList();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = null;
        try {
            session.beginTransaction();
            query = session.createQuery(
                    "from User as i where i.login = :fLogin", User.class);
            query.setParameter("fLogin", login);
        }  catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query.uniqueResultOptional();
    }
}