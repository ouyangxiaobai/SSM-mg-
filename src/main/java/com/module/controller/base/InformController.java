package com.module.controller.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.mapper.InformMapper;
import com.module.pojo.Inform;
import com.module.util.ResultUtil;
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
 * 页面请求控制  公告管理
 */
@Controller
public class InformController {
    @Autowired
    InformMapper informMapper;

    /**
     * 跳转到列表页面
     *
     * @return
     */
    @RequestMapping("manage/informList")
    public String informList() {
        return "manage/inform/informList";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping("manage/addInform")
    public String addInform(Model model) {
        return "manage/inform/saveInform";
    }

    /**
     * 跳转到修改页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/editInform")
    public String editInform(Integer id, Model model) {
        Inform inform = informMapper.selectInformById(id);
        model.addAttribute("inform", inform);
        return "manage/inform/saveInform";
    }

    /**
     * 分页查询
     *
     * @param page  默认第一页
     * @param limit 默认每页显示10条
     * @return
     */
    @RequestMapping("manage/queryInformList")
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
        List<Inform> list = informMapper.selectAll(map);
        PageInfo<Inform> pageInfo = new PageInfo<Inform>(list);  //使用mybatis分页插件
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);  //设置返回状态0为成功
        resultUtil.setCount(pageInfo.getTotal());  //获取总记录数目 类似count(*)
        resultUtil.setData(pageInfo.getList());    //获取当前查询出来的集合
        return resultUtil;
    }

    /**
     * 插入记录
     */
    @RequestMapping("manage/saveInform")
    @ResponseBody
    public ResultUtil saveInform(Inform inform, HttpSession session) {
        Date nowTime = new Date();
        inform.setCreatetime(nowTime);
        try {
            informMapper.insertInform(inform);
            return ResultUtil.ok("添加公告成功");
        } catch (Exception e) {
            return ResultUtil.error("添加公告出错,稍后再试！");
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping("manage/updateInform")
    @ResponseBody
    public ResultUtil updateInform(Inform inform, HttpSession session) {
        Date nowTime = new Date();
        inform.setCreatetime(nowTime);
        try {
            informMapper.updateInform(inform);
            return ResultUtil.ok("修改公告成功");
        } catch (Exception e) {
            return ResultUtil.error("修改公告出错,稍后再试！");
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("manage/deleteInform")
    @ResponseBody
    public ResultUtil deleteInformById(Integer id) {
        try {
            informMapper.deleteInformById(id);
            return ResultUtil.ok("删除公告成功");
        } catch (Exception e) {
            return ResultUtil.error("删除公告出错,稍后再试！");
        }
    }

    /**
     * 根据ID批量删除
     *
     * @param idsStr
     * @return
     */
    @RequestMapping("manage/deletesInform")
    @ResponseBody
    public ResultUtil deletesInform(String idsStr) {
        try {
            if (!StringUtils.isBlank(idsStr)) {
                String[] ids = idsStr.split(",");
                for (String id : ids) {
                    informMapper.deleteInformById(Integer.parseInt(id));
                }
            }
            return ResultUtil.ok("批量删除公告成功");
        } catch (Exception e) {
            return ResultUtil.error("删除公告出错,稍后再试！");
        }
    }


}
