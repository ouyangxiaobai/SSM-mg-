package com.module.controller.base;

import com.module.mapper.UserinfoMapper;
import com.module.pojo.Userinfo;
import com.module.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面请求控制  用户信息管理
 */
@Controller
public class UserinfoController {
    @Autowired
    UserinfoMapper userinfoMapper;


    /**
     * 跳转到列表页面
     *
     * @return
     */
    @RequestMapping("manage/userinfoList")
    public String userinfoList() {
        return "manage/userinfo/userinfoList";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping("manage/addUserinfo")
    public String addUserinfo(Model model) {
        return "manage/userinfo/saveUserinfo";
    }

    /**
     * 跳转到修改页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/editUserinfo")
    public String editUserinfo(Integer id, Model model) {
        Userinfo userinfo = userinfoMapper.selectUserinfoById(id);
        model.addAttribute("userinfo", userinfo);
        return "manage/userinfo/saveUserinfo";
    }

    /**
     * 查看详情页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/userinfoInfo")
    public String userinfoInfo(Integer id, Model model) {
        Userinfo userinfo = userinfoMapper.selectUserinfoById(id);
        model.addAttribute("userinfo", userinfo);
        return "manage/userinfo/userinfoInfo";
    }


    /**
     * 分页查询
     *
     * @param page  默认第一页
     * @param limit 默认每页显示10条
     * @return
     */
    @RequestMapping("manage/queryUserinfoList")
    @ResponseBody
    public ResultUtil getCarouseList(Integer page, Integer limit, String keyword) {
        if (null == page) { //默认第一页
            page = 1;
        }
        if (null == limit) { //默认每页10条
            limit = 10;
        }
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(keyword)) {
            map.put("keyword", keyword);
        }
        PageHelper.startPage(page, limit, true);
        List<Userinfo> list = userinfoMapper.selectAll(map);
        PageInfo<Userinfo> pageInfo = new PageInfo<Userinfo>(list);  //使用mybatis分页插件
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);  //设置返回状态0为成功
        resultUtil.setCount(pageInfo.getTotal());  //获取总记录数目 类似count(*)
        resultUtil.setData(pageInfo.getList());    //获取当前查询出来的集合
        return resultUtil;
    }

    /**
     * 插入记录
     */
    @RequestMapping("manage/saveUserinfo")
    @ResponseBody
    public ResultUtil saveUserinfo(Userinfo userinfo, HttpSession session) {
        Date nowTime = new Date();
        userinfo.setCreatetime(nowTime);
        try {
            userinfoMapper.insertUserinfo(userinfo);
            return ResultUtil.ok("添加用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("添加用户信息出错,稍后再试！");
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping("manage/updateUserinfo")
    @ResponseBody
    public ResultUtil updateUserinfo(Userinfo userinfo, HttpSession session) {
        Date nowTime = new Date();
        userinfo.setCreatetime(nowTime);
        try {
            userinfoMapper.updateUserinfo(userinfo);
            return ResultUtil.ok("修改用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("修改用户信息出错,稍后再试！");
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("manage/deleteUserinfo")
    @ResponseBody
    public ResultUtil deleteUserinfoById(Integer id) {
        try {
            userinfoMapper.deleteUserinfoById(id);
            return ResultUtil.ok("删除用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("删除用户信息出错,稍后再试！");
        }
    }

    /**
     * 根据ID批量删除
     *
     * @param idsStr
     * @return
     */
    @RequestMapping("manage/deletesUserinfo")
    @ResponseBody
    public ResultUtil deletesUserinfo(String idsStr) {
        try {
            if (!StringUtils.isBlank(idsStr)) {
                String[] ids = idsStr.split(",");
                for (String id : ids) {
                    userinfoMapper.deleteUserinfoById(Integer.parseInt(id));
                }
            }
            return ResultUtil.ok("批量删除用户信息成功");
        } catch (Exception e) {
            return ResultUtil.error("删除用户信息出错,稍后再试！");
        }
    }


}
