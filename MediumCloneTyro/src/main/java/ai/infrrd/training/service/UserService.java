package ai.infrrd.training.service;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;

public interface UserService {
	
	public boolean addUser(UserDto user)throws BusinessException;

}
