package eecs3311.group.p.Marketplace.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 *
 * This interface automatically provides CRUD (Create, Read, Update, Delete) operations
 * for the User entity, as well as custom query methods defined below.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their unique username.
     *
     * @param username The username to search for.
     * @return An {@link Optional} containing the {@link User} if found, or an empty Optional if not.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their unique email address.
     *
     * @param email The email address to search for.
     * @return An {@link Optional} containing the {@link User} if found, or an empty Optional if not.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their password reset token.
     *
     * @param token The password reset token to search for.
     * @return An {@link Optional} containing the {@link User} if found, or an empty Optional if not.
     */
    Optional<User> findByPasswordResetToken(String token);
}