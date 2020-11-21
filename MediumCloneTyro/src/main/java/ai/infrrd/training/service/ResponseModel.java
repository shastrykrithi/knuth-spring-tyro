package ai.infrrd.training.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class ResponseModel {

	private HashMap<String, Object> data;

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(String responseType, Object responseData) {
		HashMap<String, Object> dataSet = new HashMap<>();
		dataSet.put(responseType, responseData);
		this.data = dataSet;
	}
}
