package com.module.controller.base;

import com.module.mapper.ArticleMapper;
import com.module.mapper.SorttypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 数据统计查询请求处理
 */
@Controller
public class DataCountController {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    SorttypeMapper sorttypeMapper;

    /**
     * 数据统计显示
     *
     * @param model
     * @return
     */
    @RequestMapping("manage/countDateShow")
    public String countDateShow(Model model) {
        List<Map> mapList = articleMapper.countData(null);
        model.addAttribute("mapList", mapList);
        return "manage/article/countDateShow";
    }

}
