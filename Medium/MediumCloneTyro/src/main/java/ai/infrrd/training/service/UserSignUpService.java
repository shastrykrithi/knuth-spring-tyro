package ai.infrrd.training.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.repository.UserRepository;

@Service
public class UserSignUpService implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private MongoUserDetailsService userDetailsService;
	
	@Override
	public boolean addUser(UserDto userData) throws BusinessException {
		Users user=new Users();
		if(userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
			if(userData.getEmail().matches("^\\S+@\\S+\\.\\S+$")) {
				if(userData.getUsername().matches("[A-Z][a-z]*")) {
					user.setUsername(userData.getUsername());
				} else {
					throw new BusinessException("Should contain only Alphabets");
				}
				user.setEmail(userData.getEmail());
			} else {
				throw new BusinessException("Should be in the format xxx@yyy.zzz");
			}
			user.setPassword(passwordEncoder.encode(userData.getPassword()));
		} else {
			throw new BusinessException("Should contain at least one lowercase letter, one upper case letter, one numeric, one special character and should be at least of 8 characters long and not more than 32 characters");
		}
		userRepo.save(user);
		return true;
	}

	public Optional<UserDto> getByEmailAndPassword(String email, String password) throws BusinessException {
		UserDetails user=userDetailsService.loadUserByUsername(email);
		if(passwordEncoder.matches(password, user.getPassword())) {
			return userRepo.findByEmail(email);
		}
		else {
			throw new BusinessException("Password not match");
		}
	}

}

