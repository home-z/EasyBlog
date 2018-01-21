package com.blog.utils;

import javax.servlet.ServletContext;

/**
 * 系统用到的常量标识名称，用于缓存等
 * 
 * @author tim
 *
 */
public class CoreConsts {

	/**
	 * 执行环境的key
	 * 
	 * @author tim
	 * 
	 */
	public static class ExecuteContextKeys {

		/**
		 * 当前登录用户（对象）
		 */
		public static final String CURRENT_USER = "Current_User";

		/**
		 * 当前登录用户的菜单（集合）
		 */
		public static final String CURRENT_MENU = "Current_MenuList";

		/**
		 * HttpSession
		 */
		public static final String CURRENT_SESSION = "Current_Session";

		/**
		 * HttpServletRequest
		 */
		public static final String CURRENT_REQUEST = "Current_Request";

		/**
		 * 每页数量
		 */
		public static final int PAGESIZE = 10;

		/**
		 * 相似度阈值
		 */
		public static final double SIMILARTY = 60d;
	}
}
