package com.blog.utils;

import javax.servlet.ServletContext;

/**
 * 系统用到的常量
 * 
 * @author tim
 *
 */
public class CoreConsts {
	
	/**
	 * 语言包：：：仅默认三种
	 * 
	 * @author tim
	 * 
	 */
	public static class LANGS {
		public static final String zh_CN = "zh_CN";
		public static final String zh_TW = "zh_TW";
		public static final String en_US = "en_US";
	}

	/**
	 * 运行时的一些常量
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
	}

	/**
	 * 执行环境的key
	 * 
	 * @author tim
	 * 
	 */
	public static class ExecuteContextKeys {
		/**
		 * 当前登录用户
		 */
		public static final String CURRENT_USER = "Current_User";

		/**
		 * HttpSession
		 */
		public static final String CURRENT_SESSION = "Current_Session";

		/**
		 * HttpServletRequest
		 */
		public static final String CURRENT_REQUEST = "Current_Request";
	}
}
