package ai.infrrd.training.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

	Users findByUsername(String username);

	Optional<UserDto> findByEmail(String email);

	Optional<Users> findById(String id);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsById(String id);

}
