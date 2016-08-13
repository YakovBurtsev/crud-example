package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import crud.dao.UserDaoImpl;

/**
 * Created by Burtsev on 08.08.2016.
 */

@Controller
public class UserController {
    private static int currentPage = 1;
    private static int lastPage = 1;

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String listUsers(@RequestParam(value ="page", required = false) Long page, Model model) {
        if (page != null) {
            currentPage = page.intValue();
        }
        int usersCount = this.userService.count();
        int k = 1;
        if (usersCount%UserDaoImpl.ROWS_PER_PAGE == 0) {k = 0;}
        int pagesCount = usersCount/UserDaoImpl.ROWS_PER_PAGE + k;
        int startPage = Math.min(pagesCount - 4, Math.max(currentPage - 2, 1));
        int endPage = Math.min(startPage + 4, pagesCount);
        lastPage = pagesCount;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", this.userService.listUsers(currentPage));

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstPage", 1);
        model.addAttribute("lastPage", pagesCount);

        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        if(user.getId() == 0) {
            this.userService.addUser(user);
        } else {
            this.userService.updateUser(user);
        }
        return "redirect:/users?page=" + lastPage;
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        this.userService.removeUser(id);
        return "redirect:/users";
    }

    @RequestMapping("edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("listUsers", this.userService.listUsers(currentPage));
        return "users";
    }

    @RequestMapping(value = "searchingResult", method = RequestMethod.GET)
    public String listUsers(Model model, String name) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsersByName", this.userService.listUsersByName(name));
        return "searchingResult";
    }

}
