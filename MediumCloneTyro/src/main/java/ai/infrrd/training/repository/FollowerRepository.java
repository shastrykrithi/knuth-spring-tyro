package ai.infrrd.training.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.infrrd.training.model.Users;

public interface FollowerRepository extends MongoRepository<Users, String> {
	

}
