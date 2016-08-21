package test.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import test.service.UserService;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final int MAX_USERS_PER_PAGE = 5;

    private UserService userService;
    private int currentPage = 1;
    private int maxNumberOfPages;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model){
        List<User> allUsers = userService.listUsers(null);
        List<User> usersToDisplay = new ArrayList<User>(MAX_USERS_PER_PAGE);

        if (allUsers.size() % MAX_USERS_PER_PAGE == 0)
            maxNumberOfPages = allUsers.size() / MAX_USERS_PER_PAGE;
        else
            maxNumberOfPages = allUsers.size() / MAX_USERS_PER_PAGE + 1;

        for (int i = (currentPage - 1) * MAX_USERS_PER_PAGE;
             i < (currentPage - 1) * MAX_USERS_PER_PAGE + MAX_USERS_PER_PAGE && i < allUsers.size();
             i++){
            usersToDisplay.add(allUsers.get(i));
        }
        model.addAttribute("user", new User());
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("userList", usersToDisplay);
        return "users";
    }

    @RequestMapping(value = "/remove/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user){
        if (user.getId() == 0) userService.addUser(user);
        else userService.updateUser(user);
        return "redirect:/users";
    }

    @RequestMapping("edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){

        List<User> allUsers = userService.listUsers(null);

        List<User> usersToDisplay = new ArrayList<User>(MAX_USERS_PER_PAGE);

        for (int i = (currentPage - 1) * MAX_USERS_PER_PAGE;
             i < (currentPage - 1) * MAX_USERS_PER_PAGE + MAX_USERS_PER_PAGE && i < allUsers.size();
             i++){
            usersToDisplay.add(allUsers.get(i));
        }

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("userList", usersToDisplay);

        return "users";
    }

    @RequestMapping(value = "/search", method=RequestMethod.GET)
    public String searchUsers(@RequestParam("searchString") String searchString, Model model){
        model.addAttribute("userList", this.userService.listUsers(searchString));
        return "searchResults";
    }

    @RequestMapping(value = "/nextPage", method=RequestMethod.GET)
    public String nextPage(Model model){
        List<User> allUsers = userService.listUsers(null);
        if (++currentPage > maxNumberOfPages) currentPage = maxNumberOfPages;
        List<User> usersToDisplay = new ArrayList<User>(MAX_USERS_PER_PAGE);

        for (int i = (currentPage - 1) * MAX_USERS_PER_PAGE;
             i < (currentPage - 1) * MAX_USERS_PER_PAGE + MAX_USERS_PER_PAGE && i < allUsers.size();
             i++){
            usersToDisplay.add(allUsers.get(i));
        }

        model.addAttribute("user", new User());
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("userList", usersToDisplay);
        return "users";
    }

    @RequestMapping(value = "/previousPage", method=RequestMethod.GET)
    public String previousPage(Model model){
        List<User> allUsers = userService.listUsers(null);
        if (--currentPage < 1) currentPage = 1;
        List<User> usersToDisplay = new ArrayList<User>(MAX_USERS_PER_PAGE);

        for (int i = (currentPage - 1) * MAX_USERS_PER_PAGE;
             i < (currentPage - 1) * MAX_USERS_PER_PAGE + MAX_USERS_PER_PAGE && i < allUsers.size();
             i++){
            usersToDisplay.add(allUsers.get(i));
        }

        model.addAttribute("user", new User());
        model.addAttribute("pageNumber", currentPage);
        model.addAttribute("userList", usersToDisplay);
        return "users";
    }
}
