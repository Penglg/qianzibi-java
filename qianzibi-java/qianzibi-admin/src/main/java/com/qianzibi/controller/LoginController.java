package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.CreateImageCode;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginController {

    @Resource
    private SysAccountService sysAccountService;

    /**
     * 获取验证码
     */
    @GetMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCode createImageCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = createImageCode.getCode();
        System.out.println("--///////" + code + "\\\\\\\\\\--");
        // 将验证码存入session中
        session.setAttribute(Constants.CHECK_CODE_KEY, code);
        createImageCode.write(response.getOutputStream());
    }

    @Check(checkLogin = false)
    @PostMapping("/login")
    public R login(HttpSession session,
                   @VerifyParam(required = true, regex = VerifyRegexEnum.PHONE) String phone,
                   @VerifyParam(required = true) String password,
                   @VerifyParam(required = true) String checkCode) {
        if (!session.getAttribute(Constants.CHECK_CODE_KEY).equals(checkCode.toLowerCase())) {
            throw new BusinessException("图片验证码错误");
        }
        SessionUserAdminDto userAdminDto = sysAccountService.login(phone, password);
        session.setAttribute(Constants.SESSION_KEY, userAdminDto);

        return R.ok().data(userAdminDto);
    }
}
