package org.launchcode.techjobs.persistent.controllers;

import org.hibernate.annotations.GeneratorType;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

//    1. Add a private field of EmployerRepository type called employerRepository
//    to EmployerController. Give this field an @Autowired annotation.
    @Autowired
    private EmployerRepository employerRepository;

//    2.Add an index method that responds at /employers with a list of all employers in the database.
//    This method should use the template employers/index.
//    To figure out the name of the model attribute you should use to pass employers into the view,
//    review this template.
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll());

        return "employer/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        employerRepository.save(newEmployer);
        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

//        optEmployer is currently initialized to null. Replace this using the .findById()
//       method with the right argument to look for the given employer object from the data layer.
        Optional<Employer> optEmployer = employerRepository.findById(employerId);

        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
