package com.ymp.j2ee_project.service;


import com.ymp.j2ee_project.model.*;
import com.ymp.j2ee_project.repository.DonorsRepository;
import com.ymp.j2ee_project.repository.PatientsRepository;
import com.ymp.j2ee_project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorsRepository donorsRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    public Users registerUser(String fullName, String password, String email, String address) {
        if (email == null || password == null) {
            return null;
        } else {
            List<Users> userList = usersRepository.findByEmail(email);
            if (userList != null || userList.size() > 0) {
                System.out.println("Duplicate login");
                return null;
            }
            Users usersModel = new Users();
            usersModel.setFullName(fullName);
            usersModel.setPassword(password);
            usersModel.setEmail(email);
            usersModel.setAddress(address);

            Users user = usersRepository.save(usersModel);
            return user;
        }
    }

    public Users authenticate(String email, String password) {
        List<Users> userList = usersRepository.findByEmailAndPassword(email, password);
        if (userList == null || userList.size() == 0) {
            System.out.println("Wrong credentials");
            return null;
        }

        if (userList.size() > 1) {
            System.out.println("Something went wrong, contact to administrator");
            return null;
        }
        return userList.get(0);
    }

    public void donorsForm(DonorRequest donorRequest) throws ParseException {

        Donors donorModel = new Donors();

        donorModel.setDonorName(donorRequest.getDonorName());
        donorModel.setAge(donorRequest.getAge());

        donorModel.setBlood(donorRequest.getBlood());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = dateFormat.parse(donorRequest.getDate());
        donorModel.setDate(d1);

        donorModel.setPhoneNo(donorRequest.getPhoneNo());
        donorModel.setDisease(donorRequest.getDisease());
        donorModel.setAddress(donorRequest.getAddress());
        donorModel.setGender(donorRequest.getGender());

        donorsRepository.save(donorModel);

    }

    public void patientsForm(PatientsRequest patientsRequest) throws ParseException {

        Patients patientsModel = new Patients();
        patientsModel.setPatientName(patientsRequest.getPatientName());
        patientsModel.setAge(patientsRequest.getAge());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = dateFormat.parse(patientsRequest.getDate());
        patientsModel.setDate(d1);
        patientsModel.setGender(patientsRequest.getGender());
        patientsModel.setPhoneNo(patientsRequest.getPhoneNo());
        patientsModel.setBlood(patientsRequest.getBlood());
        patientsModel.setAddress(patientsRequest.getAddress());

        patientsRepository.save(patientsModel);


    }

    public List<Donors> ListDonors() {

        return (List<Donors>) donorsRepository.findAll();
    }

    public List<Patients> ListPatients() {

        return (List<Patients>) patientsRepository.findAll();
    }



}


