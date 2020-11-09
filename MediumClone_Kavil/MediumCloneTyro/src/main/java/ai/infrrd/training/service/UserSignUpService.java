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
		Users user = new Users();
		if (userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()]).{8,32}$")) {
			if (userData.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z]+\\.[A-Za-z]{2,4}$")) {
				if (userData.getUsername().matches("^[A-Za-z0-9].{5,15}$")) {
					user.setUsername(userData.getUsername());
				} else {
					throw new BusinessException("Username Should not exceed 15 Characters");
				}
				user.setEmail(userData.getEmail());
			} else {
				throw new BusinessException("Email Should be proper");
			}
			user.setPassword(passwordEncoder.encode(userData.getPassword()));
		} else {
			throw new BusinessException(
					"Password should be of atleast 8 characters and should contain atleast one letter, one number and one special character [@#$%^&-+=()]");
		}
		userRepo.save(user);
		return true;
	}

	public Optional<UserDto> getByEmailAndPassword(String email, String password) throws BusinessException {
		UserDetails user = userDetailsService.loadUserByUsername(email);
		if (passwordEncoder.matches(password, user.getPassword())) {
			return Optional.ofNullable(userRepo.findByEmail(email));
		} else {
			throw new BusinessException("Password not match");
		}

	}
}
