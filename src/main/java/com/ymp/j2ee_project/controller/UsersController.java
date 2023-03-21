/**
 * @author: Yoon Myat Phoo
 * @created: 22/08/2022
 * @project: Blood Bank Management System
 * @package: com.ymp.j2ee_project.UsersController
 */
package com.ymp.j2ee_project.controller;
import com.ymp.j2ee_project.model.*;
import com.ymp.j2ee_project.repository.DonorsRepository;
import com.ymp.j2ee_project.repository.PatientsRepository;
import com.ymp.j2ee_project.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UsersController {

    @Autowired
    private DonorsRepository donorsRepository;

    // comment
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new Users());
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session) {
        model.addAttribute("loginRequest", new Users());
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("user");
        session.removeAttribute("patimsg");
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("user");
        session.removeAttribute("patimsg");
        return "bloodWeb";
    }

    @GetMapping("/logout")
    public String getLogoutPage(Model model, HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        return "index";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        return "about";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute Users usersModel, HttpSession session) {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("user");
        session.removeAttribute("patimsg");
        System.out.println("register request: " + usersModel);

        String response = usersService.registerUser(usersModel.getFullName(), usersModel.getPassword(), usersModel.getEmail(), usersModel.getAddress());
        if (!response.equals("successfully registered")) {
            session.setAttribute("errMsg", response);
            return "register";
        }
        session.setAttribute("msg", response);

        return "login";

        /*return registeredUser==null ? "error_page" : "redirect:/login";*/


    }

    @PostMapping("/login")
    public String login(@ModelAttribute Users usersModel, HttpSession session) {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("user");
        session.removeAttribute("patimsg");

        System.out.println("login request: ");
        System.out.println("startId is: " + usersModel.getId());
        Users user = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            System.out.println("userId is: " + user.getId());

            return "bloodWeb";

        } else {
            return "error_page";
        }
    }

    static List<String> bloodList = null;

    static {
        bloodList = new ArrayList<>();
        bloodList.add("A+");
        bloodList.add("A-");
        bloodList.add("B+");
        bloodList.add("B-");
        bloodList.add("AB+");
        bloodList.add("AB-");
        bloodList.add("O+");
        bloodList.add("O-");

    }

    @GetMapping("/donor")
    public String getDonorPage(Model model, HttpSession session) {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        model.addAttribute("donorRequest", new Donors());
        model.addAttribute("bloodList", bloodList);
        return "donor";
    }

    @PostMapping("/donor")
    public String donor(@ModelAttribute DonorRequest donorsModel, Users userModel, Model model, HttpSession session) throws ParseException, ParseException {

        Long userId = (Long) session.getAttribute("userId");
        System.out.println("toInputDonorUserId: " + userId);
        donorsModel.setUserId(userId);
        Long id = usersService.
                donorsForm(donorsModel);
        System.out.println("Request donorsModel: " + donorsModel);
        model.addAttribute("bloodList", bloodList);
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        session.setAttribute("msg", "successfully donate!,Your donor id is " + id);
        return "donor";
    }

    @GetMapping("/patient")
    public String getPatientPage(@ModelAttribute PatientsRequest patientsModel, Model model, HttpSession session) throws ParseException {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        model.addAttribute("bloodList", bloodList);
        model.addAttribute("patientRequest", new Patients());
        return "patient";
    }

    @PostMapping("/patient")
    public String patient(@ModelAttribute PatientsRequest patientsModel, Model model, HttpSession session) throws ParseException {

        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        Long userId = (Long) session.getAttribute("userId");
        patientsModel.setUserId(userId);
        System.out.println("Request patientsModel: " + patientsModel);
        Long id = usersService.patientsForm(patientsModel);
        model.addAttribute("bloodList", bloodList);
        session.setAttribute("patimsg", "required blood successfully added!, Patient id is: " + id);
        return "patient";
    }

    @GetMapping("/back")
    public String back(Model model) {
        return "bloodWeb";
    }


    @GetMapping("/donorsList")
    public String showDonorList(Model model, HttpSession session) throws ParseException {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        Users user = (Users) session.getAttribute("user");


        Long userId = (Long) session.getAttribute("userId");
        if (user.getRoleId() == 1) {
            List<Donors> donorsList = usersService.ListDonors();
            model.addAttribute("donorsList", donorsList);

            return "donorListAdmin";
        } else {
            List<Donors> userDonors = usersService.listUserId(userId);

            model.addAttribute("userDonors", userDonors);
            System.out.println("userDonor: " + userDonors);
            return "donorList";
        }
    }


    @GetMapping("/addNewDonor")
    public String addNewEmployee(Model model) {
        Donors donors = new Donors();
        model.addAttribute("donors", donors);
        model.addAttribute("bloodList", bloodList);
        return "donor";
    }


    @GetMapping("/addNewPatient")
    public String addNewPatient(Model model) {
        Patients patients = new Patients();
        model.addAttribute("patients", patients);
        model.addAttribute("bloodList", bloodList);
        return "patient";
    }

    @GetMapping("/deleteDonors")
    public String deleteDonors(@RequestParam Long donorId) {
        donorsRepository.deleteById((donorId));
        return "redirect:/donorsList";
    }

    @GetMapping("/deletePatients")
    public String deletePatients(@RequestParam Long patientId) {
        patientsRepository.deleteById((patientId));
        return "redirect:/patientsList";
    }

    @GetMapping("/patientsList")
    public String showPatientList(Model model, HttpSession session) {
        session.removeAttribute("msg");
        session.removeAttribute("errMsg");
        session.removeAttribute("patimsg");
        Users user = (Users) session.getAttribute("user");


        Long userId = (Long) session.getAttribute("userId");
        if (user.getRoleId() == 1) {
            List<Patients> patientsList = usersService.ListPatients();
            model.addAttribute("patientsList", patientsList);

            return "patientListAdmin";
        } else {
            List<Patients> patientUserList = usersService.PatientUserId(userId);
            model.addAttribute("patientUserList", patientUserList);
            return "patientList";
        }
    }

    @GetMapping("/searchDonor")
    public String showDonorList(Donors donors, Model model, String keyword, HttpSession session) {
        List<Donors> donorsList;
        if (keyword == null || keyword.trim().equals("")) {
            donorsList = usersService.ListDonors();
        } else {
            donorsList = usersService.getDonorByKeyword(keyword);
            model.addAttribute("donorsList", donorsList);
        }

        Users user = (Users) session.getAttribute("user");

        model.addAttribute("donorsList", donorsList);
        if (user.getRoleId() == 1)
            return "donorListAdmin";
        else return "donorList";
    }

    @GetMapping("/searchPatient")
    public String showPatientList(Donors donors, Model model, String keyword, HttpSession session) {
        List<Patients> patientsList;
        if (keyword == null || keyword.trim().equals("")) {
            patientsList = usersService.ListPatients();
        } else {
            patientsList = usersService.getPatientByKeyword(keyword);
            model.addAttribute("patientsList", patientsList);
        }

        Users user = (Users) session.getAttribute("user");

        model.addAttribute("patientsList", patientsList);
        if (user.getRoleId() == 1)
            return "patientListAdmin";
        else return "patientList";
    }


}
