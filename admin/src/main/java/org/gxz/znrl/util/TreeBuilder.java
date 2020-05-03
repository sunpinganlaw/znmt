package org.gxz.znrl.util;

import org.gxz.znrl.entity.newsystem.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujy on 18-5-4.
 */
public class TreeBuilder {

    List<TreeNode> nodes = new ArrayList<TreeNode>();

    public TreeBuilder() {
    }

    public TreeBuilder(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
    static {

    }
    // 构建树形结构
    public List<TreeNode> buildTree() {
        List<TreeNode> treeNodes = new ArrayList<>();
        List<TreeNode> rootNodes = getRootNodes();
        for (TreeNode rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    // 递归子节点
    public void buildChildNodes(TreeNode node) {
        List<TreeNode> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (TreeNode child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }

    // 获取父节点下所有的子节点
    public List<TreeNode> getChildNodes(TreeNode pnode) {
        List<TreeNode> childNodes = new ArrayList<>();
        for (TreeNode n : nodes) {
            if (pnode.getId().equals(n.getParentId())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    // 判断是否为根节点
    public boolean rootNode(TreeNode node) {
        boolean isRootNode = true;
        for (TreeNode n : nodes) {
            if (n.getId().equals(node.getParentId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    // 获取集合中所有的根节点
    public List<TreeNode> getRootNodes() {
        List<TreeNode> rootNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (rootNode(node)) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

}
