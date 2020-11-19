package ai.infrrd.training.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.model.Articles;

@Repository
public interface ArticleRepository extends MongoRepository<Articles, String> {

	

	public List<Articles> findAll();

	@Query("{$orderby: {views:-1}, $limit:4}")
	List<Articles> findArticles(int views);
}
