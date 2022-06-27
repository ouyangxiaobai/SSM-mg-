package com.module.controller.base;

import com.module.mapper.WenjuanMapper;
import com.module.mapper.WenjuanjieguoMapper;
import com.module.pojo.Wenjuanjieguo;
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
 * 页面请求控制  问卷结果管理
 */
@Controller
public class WenjuanjieguoController {
    @Autowired
    WenjuanjieguoMapper wenjuanjieguoMapper;
    @Autowired
    WenjuanMapper wenjuanMapper;


    /**
     * 跳转到列表页面
     *
     * @return
     */
    @RequestMapping("manage/wenjuanjieguoList")
    public String wenjuanjieguoList() {
        return "manage/wenjuanjieguo/wenjuanjieguoList";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping("manage/addWenjuanjieguo")
    public String addWenjuanjieguo(Model model) {
        return "manage/wenjuanjieguo/saveWenjuanjieguo";
    }

    /**
     * 跳转到修改页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/editWenjuanjieguo")
    public String editWenjuanjieguo(Integer id, Model model) {
        Wenjuanjieguo wenjuanjieguo = wenjuanjieguoMapper.selectWenjuanjieguoById(id);
        model.addAttribute("wenjuanjieguo", wenjuanjieguo);
        return "manage/wenjuanjieguo/saveWenjuanjieguo";
    }

    /**
     * 查看详情页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("manage/wenjuanjieguoInfo")
    public String wenjuanjieguoInfo(Integer id, Model model) {
        Wenjuanjieguo wenjuanjieguo = wenjuanjieguoMapper.selectWenjuanjieguoById(id);
        model.addAttribute("wenjuanjieguo", wenjuanjieguo);
        return "manage/wenjuanjieguo/wenjuanjieguoInfo";
    }


    /**
     * 分页查询
     *
     * @param page  默认第一页
     * @param limit 默认每页显示10条
     * @return
     */
    @RequestMapping("manage/queryWenjuanjieguoList")
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
        List<Wenjuanjieguo> list = wenjuanjieguoMapper.selectAll(map);
        PageInfo<Wenjuanjieguo> pageInfo = new PageInfo<Wenjuanjieguo>(list);  //使用mybatis分页插件
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(0);  //设置返回状态0为成功
        resultUtil.setCount(pageInfo.getTotal());  //获取总记录数目 类似count(*)
        resultUtil.setData(pageInfo.getList());    //获取当前查询出来的集合
        return resultUtil;
    }

    /**
     * 插入记录
     */
    @RequestMapping("manage/saveWenjuanjieguo")
    @ResponseBody
    public ResultUtil saveWenjuanjieguo(Wenjuanjieguo wenjuanjieguo, HttpSession session) {
        Date nowTime = new Date();
        wenjuanjieguo.setCreatetime(nowTime);
        try {
            wenjuanjieguoMapper.insertWenjuanjieguo(wenjuanjieguo);
            return ResultUtil.ok("添加问卷结果成功");
        } catch (Exception e) {
            return ResultUtil.error("添加问卷结果出错,稍后再试！");
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping("manage/updateWenjuanjieguo")
    @ResponseBody
    public ResultUtil updateWenjuanjieguo(Wenjuanjieguo wenjuanjieguo, HttpSession session) {
        Date nowTime = new Date();
        wenjuanjieguo.setCreatetime(nowTime);
        try {
            wenjuanjieguoMapper.updateWenjuanjieguo(wenjuanjieguo);
            return ResultUtil.ok("修改问卷结果成功");
        } catch (Exception e) {
            return ResultUtil.error("修改问卷结果出错,稍后再试！");
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("manage/deleteWenjuanjieguo")
    @ResponseBody
    public ResultUtil deleteWenjuanjieguoById(Integer id) {
        try {
            wenjuanjieguoMapper.deleteWenjuanjieguoById(id);
            return ResultUtil.ok("删除问卷结果成功");
        } catch (Exception e) {
            return ResultUtil.error("删除问卷结果出错,稍后再试！");
        }
    }

    /**
     * 根据ID批量删除
     *
     * @param idsStr
     * @return
     */
    @RequestMapping("manage/deletesWenjuanjieguo")
    @ResponseBody
    public ResultUtil deletesWenjuanjieguo(String idsStr) {
        try {
            if (!StringUtils.isBlank(idsStr)) {
                String[] ids = idsStr.split(",");
                for (String id : ids) {
                    wenjuanjieguoMapper.deleteWenjuanjieguoById(Integer.parseInt(id));
                }
            }
            return ResultUtil.ok("批量删除问卷结果成功");
        } catch (Exception e) {
            return ResultUtil.error("删除问卷结果出错,稍后再试！");
        }
    }


}
