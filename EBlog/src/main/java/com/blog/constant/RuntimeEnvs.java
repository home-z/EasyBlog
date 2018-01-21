package com.blog.constant;

import javax.servlet.ServletContext;

/**
 * @author：Tim
 * @date：2018年1月20日 上午9:58:21
 * @description：系统运行过程变量
 */
public class RuntimeEnvs {

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
	 * 当前登录用户ID
	 */
	public static String CURRENT_USERID;

	/**
	 * 当前语言
	 */
	public static String CURRENT_LANGUAGE;

}
