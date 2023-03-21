/**
 * @author: Yoon Myat Phoo
 * @created: 22/08/2022
 * @project: Blood Bank Management System
 * @package: com.ymp.j2ee_project.UserService
 */
package com.ymp.j2ee_project.service;

import com.ymp.j2ee_project.model.*;
import com.ymp.j2ee_project.repository.DonorsRepository;
import com.ymp.j2ee_project.repository.PatientsRepository;
import com.ymp.j2ee_project.repository.UsersRepository;
import com.ymp.j2ee_project.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorsRepository donorsRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    public String registerUser(String fullName, String password, String email, String address) {
        try {
            if (!ValidationUtil.isValidString(email) || !ValidationUtil.isValidString(password) ||
                    !ValidationUtil.isValidString(fullName) || !ValidationUtil.isValidString(address)) {
                return "input is empty";
            } else {
                List<Users> userList = usersRepository.findByEmail(email);
                if (!userList.isEmpty()) {
                    System.out.println("Duplicate login");
                    return "duplicate user";
                }
                Users usersModel = new Users();
                usersModel.setFullName(fullName);
                usersModel.setPassword(password);
                usersModel.setEmail(email);
                usersModel.setAddress(address);
                usersModel.setRoleId(2L);

                usersRepository.save(usersModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "system error";
        }

        return "successfully registered";

    }


    public Users authenticate(String email, String password) {
       /* if(!ValidationUtil.isValidString(email)){

        }*/

        List<Users> userList = usersRepository.findByEmailAndPassword(email, password);
        System.out.println("userList: " + userList);
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

    public Long donorsForm(DonorRequest donorRequest) throws ParseException {

        Donors donorModel = new Donors();

        donorModel.setDonorName(donorRequest.getDonorName());
        donorModel.setAge(donorRequest.getAge());
        donorModel.setUserId(donorRequest.getUserId());

        donorModel.setBlood(donorRequest.getBlood());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = dateFormat.parse(donorRequest.getDate());
        donorModel.setDate(d1);

        donorModel.setPhoneNo(donorRequest.getPhoneNo());
        donorModel.setDisease(donorRequest.getDisease());
        donorModel.setAddress(donorRequest.getAddress());
        donorModel.setGender(donorRequest.getGender());

        Donors donors = donorsRepository.save(donorModel);
        System.out.println("donors: " + donors);
        Long donorId = donors.getId();

        Optional<Donors> ListDonor = donorsRepository.findById(donorId);
        return donorId;
    }

    public Long patientsForm(PatientsRequest patientsRequest) throws ParseException {

        Patients patientsModel = new Patients();
        patientsModel.setPatientName(patientsRequest.getPatientName());
        patientsModel.setAge(patientsRequest.getAge());
        patientsModel.setUserId(patientsRequest.getUserId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = dateFormat.parse(patientsRequest.getDate());
        patientsModel.setDate(d1);
        patientsModel.setGender(patientsRequest.getGender());
        patientsModel.setPhoneNo(patientsRequest.getPhoneNo());
        patientsModel.setBlood(patientsRequest.getBlood());
        patientsModel.setAddress(patientsRequest.getAddress());

        Patients patients = patientsRepository.save(patientsModel);

        return patients.getId();

    }

    public List<Donors> getDonorByKeyword(String keyword) {
        return donorsRepository.findByKeyword(keyword);
    }

    public List<Donors> ListDonors() {

        List<Donors> listAll = (List<Donors>) donorsRepository.findAll();

        return listAll;
    }

    public List<Donors> listUserId(Long userId) {
        List<Donors> listUserId = donorsRepository.findByUserId(userId);
        return listUserId;
    }


    public List<Patients> getPatientByKeyword(String keyword) {
        return patientsRepository.findByKeyword(keyword);
    }

    public List<Patients> ListPatients() {

        List<Patients> list = (List<Patients>) patientsRepository.findAll();
        return list;
    }

    public List<Patients> PatientUserId(Long id) {
        List<Patients> patientUsers = patientsRepository.findByUserId(id);
        return patientUsers;
    }


}


