package com.module.controller.base;

import com.module.mapper.AdminMapper;
import com.module.pojo.Admin;
import com.module.util.ConstantUtil;
import com.module.util.MD5Util;
import com.module.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理类：登陆 首页跳转等
 */
@Controller
public class ManageController {
    @Autowired
    AdminMapper adminMapper;

    //默认进入系统后台首页
    @RequestMapping("/manage")
    public String manage() {
        return "manage/login";
    }

    @RequestMapping("manage/index")
    public String index() {
        return "manage/index";
    }

    @RequestMapping("manageIndexstu")
    public String manageIndexstu() {
        return "manage/indexstu";
    }

    @RequestMapping("manage/indexstu")
    public String indexstu() {
        return "manage/indexstu";
    }

    //后台主页
    @RequestMapping("manage/main")
    public String main() {
        return "manage/main";
    }


    //访问login
    @RequestMapping("manage/login")
    public String login() {
        return "manage/login";
    }


    //退出登录
    @RequestMapping("manage/loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("admin"); //清楚session中登录的对象
        return "manage/login";
    }

    //登陆提交
    @RequestMapping("manage/loginSubmit")
    @ResponseBody
    public ResultUtil loginSubmit(HttpServletRequest request, String role, String username, String password, String vcode) {
        if (StringUtils.isEmpty(vcode)) {
            return ResultUtil.error("验证码不能为空");
        }
        if (StringUtils.isEmpty(username)) {
            return ResultUtil.error("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ResultUtil.error("登陆密码不能为空");
        }

        //获取session中的验证码
        HttpSession session = request.getSession();
        String sessionVcode = session.getAttribute("vcode").toString().toLowerCase();
        if (!sessionVcode.equalsIgnoreCase(vcode)) {
            return ResultUtil.error("验证码不正确");
        }
        Map map = new HashMap();
        System.out.println("username = " + username);
        if (StringUtils.isNotEmpty(username)) {
            map.put("adminname", username);
        }
        System.out.println("map = " + map);
        List<Admin> adminList = adminMapper.selectAll(map);
        System.out.println("adminList = " + adminList);
        if (adminList.size() <= 0) {
            return new ResultUtil(1, "用户名或密码错误！");
        }
        Admin admin = adminList.get(0);
        System.out.println("admin = " + admin);
        if (admin != null) {
            //判断密码是否相等
            password = MD5Util.getMd5(password); //加密密码  然后在进行比对
            System.out.println("数据库存储密码 = " + admin.getAdminpassword());
            System.out.println("登录密码加密 = " + password);
            if (admin.getAdminpassword().equals(password)) {
                // 登陆成功
                // 将密码置空
                admin.setAdminpassword("");
                // 设置用户信息到Session作用域
                session.setAttribute(ConstantUtil.MANAGE_LOGIN_SERSSION, admin);
                session.setAttribute("loginuserName", username);
                //设置用户名
                session.setAttribute(ConstantUtil.MANAGE_LOGIN_SERSSION_ID, admin.getId());
                return new ResultUtil(0);
            } else {
                return new ResultUtil(1, "用户名或密码错误！");
            }
        } else {
            return new ResultUtil(1, "用户名或密码错误！");
        }

    }

    /**
     * 跳转到修改密码页面
     */
    @RequestMapping("manage/changePwd")
    public String changePwd() {
        return "manage/changePwd";
    }

    /**
     * 跳转到个人中心页面
     */
    @RequestMapping("manage/personalData")
    public String personalData(Model model, HttpSession session) {
        Integer adminId = (Integer) session.getAttribute(ConstantUtil.MANAGE_LOGIN_SERSSION_ID);
        Admin admin = adminMapper.selectAdminById(adminId);
        model.addAttribute("admin", admin);
        return "manage/personalData";
    }

    /**
     * 修改密码提交
     */
    @RequestMapping("manage/changePwdSubmit")
    @ResponseBody
    public ResultUtil changePwdSubmit(HttpSession session, String oldPwd, String newPwd) {
        if (StringUtils.isEmpty(newPwd)) {
            return ResultUtil.error("新密码不能为空，请重新填写！");
        }
        Integer adminId = (Integer) session.getAttribute(ConstantUtil.MANAGE_LOGIN_SERSSION_ID);
        Admin admin = adminMapper.selectAdminById(adminId);
        System.out.println("admin.getPassword() = " + admin.getAdminpassword());
        System.out.println("oldPwd = " + oldPwd);
        oldPwd = MD5Util.getMd5(oldPwd);
        if (admin.getAdminpassword().equals(oldPwd)) {
            newPwd = MD5Util.getMd5(newPwd);
            admin.setAdminpassword(newPwd);
            adminMapper.updateAdmin(admin);
            return ResultUtil.ok();
        } else {
            return ResultUtil.error("旧密码错误，请重新填写！");
        }
    }


    /**
     * 进入后台首页
     *
     * @return
     */
    @RequestMapping("/manageIndex")
    public String manageIndex() {
        return "manage/index";
    }


}
