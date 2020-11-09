package ai.infrrd.training.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.repository.UserRepository;

@Component
public class MongoUserDetailsService implements UserDetailsService{

	@Autowired
	 private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto user = repository.findByEmail(username);
		if(user==null)
			throw new UsernameNotFoundException("Invalid UserName");
		else
			return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
		//user.orElseThrow(() -> new UsernameNotFoundException("Invalid UserName"));
		
		//return user.map(UserDetailsImpl::new).get();
		
	}

}
