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
	 * 语言包
	 * 
	 * @author tim
	 * 
	 */
	public static class Langs {
		public static final String zh_CN = "zh_CN";
		public static final String zh_TW = "zh_TW";
		public static final String en_US = "en";
		public static final String Jap = "jp";
	}

	/**
	 * 运行时的一些常量，在用户登录时赋值
	 * 
	 * @author tim
	 * 
	 */
	public static class Runtime {

		/**
		 * 应用程序的绝对路径
		 */
		public static String APP_ABSOLUTE_PATH;

		/**
		 * 执行环境
		 */
		public static ServletContext SERVLETCONTEXT;

		/**
		 * 当前登录用户用户名
		 */
		public static String CURRENT_USERCODE;

		/**
		 * 当前语言
		 */
		public static String CURRENT_LANGUAGE;
	}

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
