package com.ymp.j2ee_project.controller;


import com.ymp.j2ee_project.model.*;
import com.ymp.j2ee_project.repository.DonorsRepository;
import com.ymp.j2ee_project.repository.PatientsRepository;
import com.ymp.j2ee_project.repository.UsersRepository;
import com.ymp.j2ee_project.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorsRepository donorsRepository;

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
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new Users());
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage(Model model) {
       /* model.addAttribute("loginRequest", new Users());*/
        return "index";
    }
    @GetMapping("/about")
    public String getAboutPage(Model model) {
        /* model.addAttribute("loginRequest", new Users());*/
        return "about";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute Users usersModel) {
        System.out.println("register request: " + usersModel);

        Users registeredUser = usersService.registerUser(usersModel.getEmail(), usersModel.getPassword(), usersModel.getFullName(), usersModel.getAddress());

        /*usersRepository.save(registeredUser);*/
        usersRepository.save(usersModel);
        return "login";

        /*return registeredUser==null ? "error_page" : "redirect:/login";*/


    }

    @PostMapping("/login")
    public String login(@ModelAttribute Users usersModel, Model model) {
        System.out.println("login request: " + usersModel);
        Users authenticated = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
        if (authenticated != null) {
            model.addAttribute("user", authenticated);
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
    public String getDonorPage(Model model) {
        model.addAttribute("donorRequest", new Donors());
        model.addAttribute("bloodList", bloodList);
        return "donor";
    }

    @PostMapping("/donor")
    public String donor(@ModelAttribute DonorRequest donorsModel) throws ParseException {
        System.out.println("Request donorsModel: " + donorsModel);
        usersService.donorsForm(donorsModel);
        return "register_success";

    }

    @GetMapping("/patient")
    public String getPatientPage(Model model) {
        model.addAttribute("patientRequest", new Patients());
        model.addAttribute("bloodList", bloodList);
        return "patient";
    }

    @PostMapping("/patient")
    public ResponseEntity patient(@ModelAttribute PatientsRequest patientsModel) throws ParseException {
/*
        Patients patients = usersService.patientsForm(patientsModel.getPatientName(), patientsModel.getPhoneNo(),
                patientsModel.getAge(), patientsModel.getBlood(), patientsModel.getGender(),
                patientsModel.getDate());*/
        System.out.println("Request patientsModel: " + patientsModel);
        usersService.patientsForm(patientsModel);
        return new ResponseEntity<>("Patient Created Successfully",HttpStatus.OK);
    }
    @GetMapping("/donorsList")
    public String showDonorList(Model model){
        List<Donors> donorsList = usersService.ListDonors();
        model.addAttribute("donorsList",donorsList);
        return "donorList";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long donorId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Donors donors = donorsRepository.findById(donorId).get();
        mav.addObject("employee", donors);
        return mav;
    }

    @GetMapping("/deleteDonors")
    public String deleteEmployee(@RequestParam Long donorId) {
        donorsRepository.deleteById((donorId));
        return "redirect:/list";
    }

    @GetMapping("/patientsList")
    public String showPatientList(Model model){
        List<Patients> patientsList = usersService.ListPatients();
        model.addAttribute("patientsList",patientsList);
        return "patientList";
    }

    @GetMapping("/patientShowUpdateForm")
    public ModelAndView patientShowUpdate(@RequestParam Long patientId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Patients patients = patientsRepository.findById(patientId).get();
        mav.addObject("Patient", patients);
        return mav;
    }

    @GetMapping("/patientDeleteDonors")
    public String patientShowDelete(@RequestParam Long patientId) {
        donorsRepository.deleteById((patientId));
        return "redirect:/list";
    }



}
