package ai.infrrd.training.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.model.Articles;

@Repository
public interface ArticleRepository extends MongoRepository<Articles, String> {

	public List<Articles> findAll();

	public List<Articles> findTop4ByOrderByViewsDesc();

	boolean existsById(String postID);
	
	boolean existsByTimestamp(long timestamp);
	
	public Articles findByTimestamp(long timestamp);
	
	@Query("{'user.username': ?0}")
	public List<ArticlesDto> findByUserName(String username);

}
