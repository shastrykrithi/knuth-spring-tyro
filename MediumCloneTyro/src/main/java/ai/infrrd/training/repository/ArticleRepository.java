package ai.infrrd.training.repository;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.model.Articles;

@Repository
public interface ArticleRepository extends MongoRepository<Articles, String> {

	
	public List<Articles> findAll();
	public List<Articles> findTop4ByOrderByViewsDesc();
//	@Query("{'user': ?0}")
//	public List<Articles> findByUser(UserDto user);
	boolean existsById(String postID);
//	public  Articles findByPostTitle(String string);
	@Query("{'user.username': ?0}")
	public List<ArticlesDto> findByUserName(String username);

	
}
