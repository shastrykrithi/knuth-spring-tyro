package ai.infrrd.training.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RecordNotFoundException extends UsernameNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}

}
