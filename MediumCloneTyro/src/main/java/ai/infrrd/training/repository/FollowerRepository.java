package ai.infrrd.training.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.model.Users;

@Repository
public interface FollowerRepository extends MongoRepository<Users, String> {

}
