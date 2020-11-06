package ai.infrrd.training.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
	
	Optional<UserDto> findByUsername(String username);

}
