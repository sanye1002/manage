package com.sanye.manage.controller;

import com.sanye.manage.DTO.AccountDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 超级战机
 * 2018-04-14 15:18
 */
@Controller
@RequestMapping("/")
public class AccountController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public ModelAndView login(Map<String,Object> map){
      return new ModelAndView("view/login",map);
    }

    @GetMapping("/")
    public ModelAndView loginPage(Map<String,Object> map){
      return new ModelAndView("redirect:/login.html",map);
    }

    @PostMapping("/account/sign-in")
    @ResponseBody
    public ResultVO<Map<String,Object>> signIn(@Valid AccountDTO accountDTO,
                                               BindingResult bindingResult,
                                               HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        map = userService.login(request,accountDTO.getPhone(),accountDTO.getPassword());

        return ResultVOUtil.success(map);
    }
    @GetMapping("/login-out")
    public ModelAndView loginOut(Map<String,Object> map){
        ShiroGetSession.removeUser();
        return new ModelAndView("redirect:/login.html",map);
    }


}
