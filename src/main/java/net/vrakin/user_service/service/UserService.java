package net.vrakin.user_service.service;

import java.util.List;

import net.vrakin.user_service.dto.UserRequestDTO;
import net.vrakin.user_service.entity.User;
import java.util.Optional;

/**
 * Інтерфейс сервісу для роботи з об'єктами {@link User}.
 *
 * <p>Цей сервіс надає базові CRUD-операції, а також додаткові методи для пошуку користувачів за ім'ям,
 * email, телефоном.</p>
 *
 * @author Valentyn Vrakin
 * @version 1.0
 */
public interface UserService {

    /**
     * Зберігає користувача в базі даних.
     *
     * @param user Об'єкт {@link User}, який потрібно зберегти.
     * @return Збережений об'єкт {@link User}.
     */
    User save(User user);

    /**
     * Знаходить користувача за його унікальним ідентифікатором.
     *
     * @param id Унікальний ідентифікатор користувача.
     * @return Об'єкт {@link Optional}, що містить користувача, якщо він знайдений.
     */
    Optional<User> findById(Long id);

    /**
     * Повертає список усіх користувачів.
     *
     * @return Список об'єктів {@link User}.
     */
    List<User> findAll();

    /**
     * Видаляє користувача за його унікальним ідентифікатором.
     *
     * @param id Унікальний ідентифікатор користувача.
     */
    void deleteById(Long id);

    /**
     * Зберігає список користувачів у базі даних.
     *
     * @param entities Список об'єктів {@link User}, які потрібно зберегти.
     */
    void saveAll(List<User> entities);

    /**
     * Видаляє всіх користувачів із бази даних.
     */
    void deleteAll();

    /**
     * Знаходить користувача за його іменем.
     *
     * @param name Ім'я користувача.
     * @return Об'єкт {@link Optional}, що містить користувача, якщо він знайдений, або порожній об'єкт, якщо ні.
     */
    Optional<User> findByName(String name);

    /**
     * Знаходить користувача за його email.
     *
     * @param email Email користувача.
     * @return Об'єкт {@link Optional}, що містить користувача, якщо він знайдений, або порожній об'єкт, якщо ні.
     */
    Optional<User> findByEmail(String email);

    /**
     * Знаходить користувача за його номером телефону.
     *
     * @param phone Номер телефону користувача.
     * @return Об'єкт {@link Optional}, що містить користувача, якщо він знайдений, або порожній об'єкт, якщо ні.
     */
    Optional<User> findByPhone(String phone);

    /**
     * Знаходить користувачів, чиї імена відповідають заданому шаблону.
     *
     * @param namePattern Шаблон імені (наприклад, "John%", "%Doe").
     * @return Список об'єктів {@link User}, що відповідають шаблону.
     */
    List<User> findByNameLike(String namePattern);
    /**
     * Створює користувача в базі даних.
     *
     * @param dto Об'єкт {@link UserRequestDTO}, який потрібно створити.
     * @return Створений об'єкт {@link User}.
     */
    User createUser(UserRequestDTO dto);
}
