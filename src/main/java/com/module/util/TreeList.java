package com.module.util;

import com.module.pojo.Liuyan;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据实体类生成属性表格菜单 实体类有两个必须属性（set get方法） 会自动按照（id/pid）顺序递归对list集合内容排序 String
 * depart_id;//唯一id String parentid;//父id
 * 此处如果父ID和id不同 可以修改代码中相关代码就可以
 *
 * @author Administrator
 */
public class TreeList {
    private List<Liuyan> resultNodes = new ArrayList<Liuyan>();//树形结构排序之后list内容
    private List<Liuyan> nodes; //传入list参数

    public TreeList(List<Liuyan> nodes) {//通过构造函数初始化
        this.nodes = nodes;
    }

    /**
     * 构建树形结构list
     *
     * @return 返回树形结构List列表
     */
    public List<Liuyan> buildTree() {
        for (Liuyan node : nodes) {
            String id = node.getId().toString();
            if (node.getFid() == null || node.getFid() == 0) {//通过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    /**
     * 递归循环子节点
     *
     * @param node 当前节点
     */
    private void build(Liuyan node) {
        List<Liuyan> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            for (Liuyan child : children) {//将子节点遍历加入返回值中
                resultNodes.add(child);
                build(child);
            }
        }
    }

    /**
     * @param node
     * @return 返回
     */
    private List<Liuyan> getChildren(Liuyan node) {
        List<Liuyan> children = new ArrayList<Liuyan>();
        String id = node.getId().toString();
        for (Liuyan child : nodes) {
            if (child.getFid() != null && id.equals(child.getFid().toString())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

}
