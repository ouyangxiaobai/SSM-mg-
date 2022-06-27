package com.module.controller.base;

import com.module.mapper.WenjuanMapper;
import com.module.pojo.Wenjuan;
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
 * 页面请求控制  问卷管理
 */
@Controller
public class WenjuanController {
    @Autowired
    WenjuanMapper wenjuanMapper;


    /**
     * 跳转到列表页面
     *
     * @return
     */
    @RequestMapping("manage/wenjuanList")
    public String wenjuanList() {
        return "manage/wenjuan/wenjuanList";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping("manage/addWenjuan")
    public String addWenjuan(Model model) {
        return "manage/wenjuan/saveWenjuan";
    }

    /**
     * 跳转到修改页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/editWenjuan")
    public String editWenjuan(Integer id, Model model) {
        Wenjuan wenjuan = wenjuanMapper.selectWenjuanById(id);
        model.addAttribute("wenjuan", wenjuan);
        return "manage/wenjuan/saveWenjuan";
    }

    /**
     * 查看详情页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/wenjuanInfo")
    public String wenjuanInfo(Integer id, Model model) {
        Wenjuan wenjuan = wenjuanMapper.selectWenjuanById(id);
        model.addAttribute("wenjuan", wenjuan);
        return "manage/wenjuan/wenjuanInfo";
    }


    /**
     * 分页查询
     *
     * @param page  默认第一页
     * @param limit 默认每页显示10条
     * @return
     */
    @RequestMapping("manage/queryWenjuanList")
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
        List<Wenjuan> list = wenjuanMapper.selectAll(map);
        PageInfo<Wenjuan> pageInfo = new PageInfo<Wenjuan>(list);  //使用mybatis分页插件
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);  //设置返回状态0为成功
        resultUtil.setCount(pageInfo.getTotal());  //获取总记录数目 类似count(*)
        resultUtil.setData(pageInfo.getList());    //获取当前查询出来的集合
        return resultUtil;
    }

    /**
     * 插入记录
     */
    @RequestMapping("manage/saveWenjuan")
    @ResponseBody
    public ResultUtil saveWenjuan(Wenjuan wenjuan, HttpSession session) {
        Date nowTime = new Date();
        wenjuan.setCreatetime(nowTime);
        try {
            wenjuanMapper.insertWenjuan(wenjuan);
            return ResultUtil.ok("添加问卷成功");
        } catch (Exception e) {
            return ResultUtil.error("添加问卷出错,稍后再试！");
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping("manage/updateWenjuan")
    @ResponseBody
    public ResultUtil updateWenjuan(Wenjuan wenjuan, HttpSession session) {
        Date nowTime = new Date();
        wenjuan.setCreatetime(nowTime);
        try {
            wenjuanMapper.updateWenjuan(wenjuan);
            return ResultUtil.ok("修改问卷成功");
        } catch (Exception e) {
            return ResultUtil.error("修改问卷出错,稍后再试！");
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("manage/deleteWenjuan")
    @ResponseBody
    public ResultUtil deleteWenjuanById(Integer id) {
        try {
            wenjuanMapper.deleteWenjuanById(id);
            return ResultUtil.ok("删除问卷成功");
        } catch (Exception e) {
            return ResultUtil.error("删除问卷出错,稍后再试！");
        }
    }

    /**
     * 根据ID批量删除
     *
     * @param idsStr
     * @return
     */
    @RequestMapping("manage/deletesWenjuan")
    @ResponseBody
    public ResultUtil deletesWenjuan(String idsStr) {
        try {
            if (!StringUtils.isBlank(idsStr)) {
                String[] ids = idsStr.split(",");
                for (String id : ids) {
                    wenjuanMapper.deleteWenjuanById(Integer.parseInt(id));
                }
            }
            return ResultUtil.ok("批量删除问卷成功");
        } catch (Exception e) {
            return ResultUtil.error("删除问卷出错,稍后再试！");
        }
    }


}
