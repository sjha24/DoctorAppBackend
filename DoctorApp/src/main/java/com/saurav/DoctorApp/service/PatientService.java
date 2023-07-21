package com.saurav.DoctorApp.service;

import com.saurav.DoctorApp.model.Appointment;
import com.saurav.DoctorApp.model.AuthenticationToken;
import com.saurav.DoctorApp.model.Patient;
import com.saurav.DoctorApp.model.dataToObj.SignInInput;
import com.saurav.DoctorApp.model.dataToObj.SignupOutput;
import com.saurav.DoctorApp.repository.DoctorRepo;
import com.saurav.DoctorApp.repository.IAuthenticationTokenRepo;
import com.saurav.DoctorApp.repository.PatientRepo;
//import com.saurav.DoctorApp.service.emailUtility.EmailHandler;
import com.saurav.DoctorApp.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    IAuthenticationTokenRepo authenticationTokenRepo;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    DoctorRepo doctorRepo;
    public SignupOutput signupPatient(Patient patient) throws NoSuchAlgorithmException {
        Boolean signupStatus = true;
        String signupStatusMessage = null;
        boolean isExistEmail = false;
        String newEmail = patient.getPatientEmail();
        //check if the patient Email already exist :-
        if(newEmail == null) {
            signupStatus = false;
            signupStatusMessage = "Invalid Email";
            return new SignupOutput(false, signupStatusMessage);
        }
        // check if this patient email already exist or not
        Patient existingPatient = patientRepo.findFirstByPatientEmail(newEmail);
        if(existingPatient != null) {
            signupStatusMessage = "Email already registered !!!";
            signupStatus = false;
            return new SignupOutput(false, signupStatusMessage);
        }
        //hash the password or encrypt the password
        String encryptPassword = PasswordEncrypter.encryptPassword(patient.getPatientPassword());
        //save the patient with the new encrypted password
        patient.setPatientPassword(encryptPassword);
        patientRepo.save(patient);
        signupStatusMessage = "Patient Registered Successfully";
        return new SignupOutput(true,signupStatusMessage);
    }
    public String signInPatient(SignInInput signInInput) throws NoSuchAlgorithmException, MessagingException {
        boolean signInStatus = true;
        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null){
            signInStatus = false;
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;
        }
        Patient existingPatient = patientRepo.findFirstByPatientEmail(signInEmail);
        if(existingPatient == null) {
            signInStatusMessage = "Email Not registered !!!";
            signInStatus = false;
            return signInStatusMessage;
        }
        //match password--->
        String encryptPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
        if(existingPatient.getPatientPassword().equals(encryptPassword)){

            //session should be created since password match and user email is valid

            AuthenticationToken authenticationToken = new AuthenticationToken(existingPatient);
            authenticationTokenRepo.save(authenticationToken);
//            EmailHandler.sendMail(existingPatient.getPatientEmail(),"Email Testing",authenticationToken.getTokenValue());
//            return "Token sent to your email";
            return authenticationToken.getTokenValue();
        }else{
            signInStatusMessage = "Invalid email or password";
            return signInStatusMessage;
        }

    }
    public String signOutPatient(String patientEmail) {
        Patient patient = patientRepo.findFirstByPatientEmail(patientEmail);
        if(patient != null) {
            AuthenticationToken authenticationToken = authenticationTokenRepo.findFirstByPatient(patient);
            authenticationTokenRepo.delete(authenticationToken);
            return "Patient sign out successfully";
        }
        return "Patient sign out cant be done";
    }
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }
    public boolean scheduleAppointment(Appointment appointment) {
        //doctor id
        Integer doctorID = appointment.getDoctor().getDoctorID();
        Boolean isDoctorExist = doctorRepo.existsById(doctorID);
        //patient id
        Integer patientID = appointment.getPatient().getPatientID();
        Boolean isPatientExist = patientRepo.existsById(patientID);
        if(isPatientExist && isDoctorExist){
            appointmentService.saveAppointment(appointment);
            return true;
        }
        return false;
    }

    public void cancelAppointment(String email) {
        //email -> patient -> appointment
        Patient patient = patientRepo.findFirstByPatientEmail(email);
        Appointment appointment = appointmentService.getAppointmentForPatient(patient);
        appointmentService.cancelAppointment(appointment);
    }
}
