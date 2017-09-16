package com.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.MenuDAO;
import com.blog.vo.MenuTree;
import com.blog.model.SysMenu;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:49:37
 * @description：用户权限
 */
@Service
public class AuthService {

	@Autowired
	private MenuDAO menuDAO;

	public List<MenuTree> getMenuTree(String userCode) {
		List<SysMenu> menus = menuDAO.getMenuByUserCode(userCode);// 读取该用户下菜单

		// 存储一级菜单
		List<MenuTree> menuTrees = new ArrayList<MenuTree>();

		// 将菜单构建成树
		Map<Integer, MenuTree> temp = new HashMap<Integer, MenuTree>();// 以id和菜单为主键
		for (SysMenu app : menus) {
			MenuTree menuTree = new MenuTree(app);

			if (app.getParentID() == 0) {// 目前是二级菜单，存着子菜单。一级菜单为总的根
				menuTrees.add(menuTree);
			} else {
				MenuTree parent = temp.get(app.getParentID());// 通过parentid找到父节点
				if (parent != null) {
					parent.getChildren().add(menuTree);// 当前则为子，加上子
				}
			}

			// 放入map 中， 已备子节点索引
			temp.put(app.getId(), menuTree);
		}

		return menuTrees;
	}
}