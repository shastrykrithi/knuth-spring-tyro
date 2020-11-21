package ai.infrrd.training.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.FollowerRequest;
import ai.infrrd.training.repository.FollowerRepository;
import ai.infrrd.training.repository.UserRepository;

@Service
public class FollowersService {

	@Autowired
	FollowerRepository followerRepository;

	@Autowired
	UserRepository userRepo;

	public HashSet<UserDto> getAllFollowers() throws BusinessException {

		HashSet<UserDto> filteredfollowerList = new HashSet<UserDto>();
		List<Users> allfollowerList = followerRepository.findAll();
		if (!allfollowerList.isEmpty()) {
			for (Users element : allfollowerList) {
				UserDto userDto = new UserDto(element.getId(), element.getUsername());
				filteredfollowerList.add(userDto);
			}
		} else {
			throw new BusinessException("User list is empty");
		}
		return filteredfollowerList;
	}

	public HashSet<UserDto> getUserFollowers(String username) throws BusinessException {
		Users user = userRepo.findByUsername(username);
		HashSet<UserDto> allUsers = getAllFollowers();
		HashSet<UserDto> userFollowers = new HashSet<UserDto>();

		HashSet<UserDto> filteredFollowers = new HashSet<UserDto>();

		if (allUsers.isEmpty()) {
			throw new BusinessException("No Users in DB!!!!");
		}

		if (user.getFollowing() != null) {
			userFollowers = user.getFollowing();
			for (UserDto element : allUsers) {
				if (user.getUsername().equals(element.getUsername())) {
					continue;
				}
				UserDto userdto = new UserDto(element.getId(), element.getUsername());
				if (userFollowers.contains(element)) {
					userdto.setIsfollowing(true);
					filteredFollowers.add(userdto);
				} else {
					userdto.setIsfollowing(false);
					filteredFollowers.add(userdto);
				}
			}

		} else {
			for (UserDto element : allUsers) {
				if (user.getUsername().equals(element.getUsername())) {
					continue;
				}
				UserDto userdto = new UserDto(element.getId(), element.getUsername());
				userdto.setIsfollowing(false);
				filteredFollowers.add(userdto);
			}
		}
		return filteredFollowers;

	}

	public boolean followUser(FollowerRequest followerRequest, String username) throws BusinessException {

		Users user = userRepo.findByUsername(username);

		Optional<Users> optionalUser = userRepo.findById(followerRequest.getFollowRequestID());

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		Users followUser = new Users();

		if (optionalUser.isPresent()) {
			followUser = optionalUser.get();

			if (user.getFollowing() != null) {
				newUserList = user.getFollowing();
			}
			newUserList.add(new UserDto(followUser.getId(), followUser.getUsername()));
		}
		user.setFollowing(newUserList);
		userRepo.save(user);
		return true;
	}

	public boolean unfollowUser(FollowerRequest followerRequest, String username) throws BusinessException {

		Users user = userRepo.findByUsername(username);

		Optional<Users> optionalUser = userRepo.findById(followerRequest.getFollowRequestID());

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		Users followUser = new Users();

		if (optionalUser.isPresent()) {
			followUser = optionalUser.get();
			if (user.getFollowing() != null) {
				newUserList = user.getFollowing();
				newUserList.remove(new UserDto(followUser.getId(), followUser.getUsername()));
				user.setFollowing(newUserList);
				userRepo.save(user);
			} else {
				new BusinessException("User is not following anyone yet!!!!");
			}
		}
		return true;
	}

}
