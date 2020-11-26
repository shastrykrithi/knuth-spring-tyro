package ai.infrrd.training.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.repository.UserRepository;

@Service
public class CloudinaryService {

	@Autowired
	Cloudinary cloudinaryConfig;
	
	@Autowired
	UserRepository userRepo;


	public String uploadFile(MultipartFile file) throws BusinessException {
		try {
			File uploadedFile = convertMultiPartToFile(file);
			Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
			return uploadResult.get("url").toString();
		} catch (Exception e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
	public String putURL(MultipartFile file,String username) throws BusinessException{
		String url=null;
		try {
			url=uploadFile(file);
			Users user = userRepo.findByUsername(username);
			user.setProfileURL(url);
			userRepo.save(user);
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return url;
		
	}
}
