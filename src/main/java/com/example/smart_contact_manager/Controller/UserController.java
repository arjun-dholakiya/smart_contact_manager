package com.example.smart_contact_manager.Controller;


import java.security.Principal;
import java.util.Optional;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.smart_contact_manager.Dao.ContactRepository;
import com.example.smart_contact_manager.Dao.UserRepository;
import com.example.smart_contact_manager.Helper.Message;
import com.example.smart_contact_manager.entities.Contact;
import com.example.smart_contact_manager.entities.User;


import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByUserName(username);
        model.addAttribute("user", user);
    }

    @GetMapping("/index")
    public String dashboard(Model model) {
        model.addAttribute("title", "User Dashboard");
        return "dashboard";
    }

    @GetMapping("/add_contact")
    public String addContact(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "add_contact";
    }

    @PostMapping("/process_contact")
    public String processcontact(@ModelAttribute Contact contact,Principal principal,Model model){


        try{
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);
    
            contact.setUser(user);
            user.getContacts().add(contact);
    
            this.userRepository.save(user);
            System.out.println("Data = "+contact);

            model.addAttribute("message", new Message("Your Contact Is Added...", "success"));
        }catch(Exception e){
            System.out.println("Error"+e.getMessage());
            e.printStackTrace();
            model.addAttribute("message", new Message("Something Went Wrong...", "danger"));
    }
        
        return "add_contact";
    }

    @GetMapping("/show-contacts/{page}")
    public String showContact(@PathVariable("page") Integer page,Model model,Principal principal){
        model.addAttribute("title","View Contacts");

        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);
       /*  List<Contact> contacts = user.getContacts(); */ 


        Pageable pageable = PageRequest.of(page, 1);

        Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(),pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalpages",contacts.getTotalPages());

        return "show_contacts";
    }

    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cid,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        try {
            Contact contact = this.contactRepository.findById(cid).orElse(null);
            String username = principal.getName();
            User user = this.userRepository.getUserByUserName(username);

            if (contact != null && contact.getUser() != null &&
                Objects.equals(contact.getUser().getId(), user.getId())) {

                this.contactRepository.delete(contact);
                redirectAttributes.addFlashAttribute("message",
                        new Message("Contact deleted successfully!", "success"));
            } else {
                redirectAttributes.addFlashAttribute("message",
                        new Message("You are not authorized to delete this contact!", "danger"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    new Message("Something went wrong while deleting contact.", "danger"));
        }

        return "redirect:/user/show-contacts/0";
    }


    @GetMapping("/contact/{cid}")
    public String showContactDetails(@PathVariable("cid") Integer cid,Model model){

        System.out.println(cid);

        Optional<Contact> contactOptional = this.contactRepository.findById(cid);
        Contact contact = contactOptional.get();

        model.addAttribute("contact",contact);


        return "contact_details";
    }

    // Your Profile COntroller
    @GetMapping("/profile")
    public String yourProfile(Model model){

        model.addAttribute("title","Profile Page");
        return "profile";
    }

}


