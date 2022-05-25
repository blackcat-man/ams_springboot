package com.four.controller;


import com.four.controller.common.LoginEntity;
import com.four.controller.common.R;
import com.four.service.IUserService;
import com.four.utils.RedisUtils;
import com.four.utils.VerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录
 */

@Slf4j
@RestController()
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获得验证码
     * @param request
     * @param response
     */
    @GetMapping("/createImg")
    @ApiOperation("获取验证码")
    public void createImg(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg"); //设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache"); //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyUtil randomValidateCode = new VerifyUtil();
            //输出验证码图片 并 将验证码存入到 Session当中
            String randcode = randomValidateCode.getRandcode(response);

            //将生成的随机验证码存放到redis中
            redisUtils.saveCode(request.getParameter("pCode"),randcode);
        } catch (Exception e) {
            log.error("获取验证码异常：", e);
        }
    }


//    /**
//     * 获取登录日志
//     * @return
//     */
//    @PostMapping("/selectLoginLogList")
//    public R selectLoginLogList() {
//        return null;
//    }
}
