package ai.infrrd.training.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.model.Topics;

@Repository
public interface TopicRepository extends MongoRepository<Topics, String> {

	List<Topics> findAll();
	boolean existsByTopicName(String topicName);
	boolean existsById(String topicID);
	Topics findByTopicName(String topicName);
	Optional<Topics> findById(String topicID);
	HashSet<Topics> findByTopicNameStartsWithIgnoreCase(String pattern);
}
