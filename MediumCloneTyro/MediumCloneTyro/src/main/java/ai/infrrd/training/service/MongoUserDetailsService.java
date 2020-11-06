package ai.infrrd.training.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserDto> user = repository.findByEmail(email);

//	    if(user == null) {
//	      throw new UsernameNotFoundException("User not found");
//	    }
//
//	    List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
//
//	    return new User(user.getUsername(), user.getPassword(), authorities);
		user.orElseThrow(() -> new UsernameNotFoundException("Invalid Email"));
		
		return user.map(UserDetailsImpl::new).get();
	}

}
