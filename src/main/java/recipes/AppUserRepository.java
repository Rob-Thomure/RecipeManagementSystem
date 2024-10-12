package recipes;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, String> {
    Optional<AppUser> findAppUserByUsername(String username);
    boolean existsAppUserByUsername(String username);
}
