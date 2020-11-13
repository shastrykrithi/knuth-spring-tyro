package ai.infrrd.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.repository.UserRepository;

@Service
public class SignUpService{

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean addUser(UserDto userData) throws BusinessException {
		Users user=new Users();
		if (userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()]).{8,32}$")) {
			if (userData.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z]+\\.[A-Za-z]{2,4}$")) {
				if (userData.getUsername().matches("^[A-Za-z0-9]{5,15}$")) {
					user.setUsername(userData.getUsername());
					user.setEmail(userData.getEmail());
					user.setPassword(passwordEncoder.encode(userData.getPassword()));
					userRepo.save(user);
				} else {
					throw new BusinessException("Username must be atleast 5 characters and should not exceed 15 characters");
				}
				
			} else {
				throw new BusinessException("Email format is wrong");
			}
			
		} else {
			throw new BusinessException("Password must contain at least one lowercase letter, one upper case letter, one numeric, one special character[@#$%^&-+=()] and should be at least of 8 characters long and not more than 32 characters");
		}
		
		return true;
	}


}

