package com.sanye.manage.controller.tools;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-25 下午 12:37
 * @Description description
 */
@RestController
@RequestMapping("/")
public class LoginApi {
    @Autowired
    private UserService userService;
    @PostMapping("login_rysj_api")
    public ResultVO<Map<String,Object>> login(@RequestParam(value = "phone") String phone,
                                              @RequestParam(value = "password")String password,
                                              @RequestParam(value = "apikey")String apikey){
        if (!apikey.equals("RlZa0BzaaA9fxrUMJF642frBdiwi8kiK")){
            return ResultVOUtil.error(100,"非法请求！！！");
        }
        return userService.loginAPI(phone, password);
    }
}
