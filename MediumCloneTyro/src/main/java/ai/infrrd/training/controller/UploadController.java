package ai.infrrd.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.infrrd.training.service.CloudinaryService;
import ai.infrrd.training.service.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class UploadController {

	@Autowired
	CloudinaryService cloudinaryService;
	
	@Autowired
	ResponseModel responseModel;
	
	@PostMapping("/upload")
	@ApiOperation(value = "Upload Profile Photo", notes = "Add a Photo to your Profile", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		String url = cloudinaryService.uploadFile(file);
        //return responseModel.setData("Result", url);
		return "File uploaded successfully: File path :  " + url;
	}
}
