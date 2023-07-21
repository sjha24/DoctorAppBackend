package com.saurav.DoctorApp.service;

import com.saurav.DoctorApp.model.AuthenticationToken;
import com.saurav.DoctorApp.repository.IAuthenticationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    IAuthenticationTokenRepo authenticationTokenRepo;
    public boolean authenticate(String email, String authenticateToken){
        AuthenticationToken authToken = authenticationTokenRepo.findFirstByTokenValue(authenticateToken);
        if(authToken == null){
            return false;
        }
        String tokenConnectedEmail = authToken.getPatient().getPatientEmail();
        return (tokenConnectedEmail.equals(email));
    }
}
