package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public User authenticated(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();    
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UnauthorizedClientException("Invalid User");
        }
    }

    public void validateSelfOrAdmin(Long userId)    
    {
        User user = authenticated();
        if(!user.getId().equals(userId) && 
            !user.hasHole("ROLE_ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

    }

	public void validateSelf(Long id) {
		User user = authenticated();
		
		if (!user.getId().equals(id) && (!user.hasHole("ROLE_MEMBER") || !user.hasHole("ROLE_VISITOR"))) {
			throw new ForbiddenException("Access denied");
		}
	}

}
