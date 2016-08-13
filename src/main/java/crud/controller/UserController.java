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

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String listUsers(@RequestParam(value ="page", required = false) Integer page, Model model) {
        int currentPage = fillModel(page, model, false, null);
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", this.userService.listUsers(currentPage));
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@RequestParam(value ="page", required = false) Integer page,
                          @RequestParam(value ="lastPage", required = false) Integer lastPage,
                          @ModelAttribute("user") User user) {
        if(user.getId() == 0) {
            this.userService.addUser(user);
            return "redirect:/users?page=" + lastPage;
        } else {
            this.userService.updateUser(user);
            return "redirect:/users?page=" + page;
        }
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id,  @RequestParam(value ="page", required = false) Integer page) {
        this.userService.removeUser(id);
        return "redirect:/users?page=" + page;
    }

    @RequestMapping("edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model, @RequestParam(value ="page", required = false) Integer page) {
        int currentPage = fillModel(page, model, false, null);
        model.addAttribute("listUsers", this.userService.listUsers(currentPage));
        model.addAttribute("user", this.userService.getUserById(id));
        return "users";
    }

    @RequestMapping(value = "searchingResult", method = RequestMethod.GET)
    public String listUsers(@RequestParam(value ="page", required = false) Integer page, Model model, String name) {
        int currentPage = fillModel(page, model, true, name);
        model.addAttribute("user", new User());
        model.addAttribute("listUsersByName", this.userService.listUsersByName(name, currentPage));
        model.addAttribute("searchName", name);
        return "searchingResult";
    }

    private int fillModel(Integer page, Model model, boolean isSearching, String name) {
        int currentPage = 1;
        if (page != null) {
            currentPage = page;
        }
        int usersCount = 0;
        if (isSearching) {
            usersCount = this.userService.searchByNameResultCount(name);
        } else {
            usersCount = this.userService.count();
        }
        int k = 1;
        if (usersCount%UserDaoImpl.ROWS_PER_PAGE == 0) {k = 0;}
        int pagesCount = usersCount/UserDaoImpl.ROWS_PER_PAGE + k;
        int startPage = Math.min(pagesCount - 4, Math.max(currentPage - 2, 1));
        if (startPage<1) {
            startPage = 1;
        }
        int endPage = Math.min(startPage + 4, pagesCount);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstPage", 1);
        model.addAttribute("lastPage", pagesCount);

        return currentPage;
    }
}
