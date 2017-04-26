package com.blog.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.blog.model.BllArticle;
import com.blog.utils.DbAction;
import com.blog.utils.HibernateUtils;

/*操作Blog类
 */
public class BlogDAL {
	
	/**
	 * 查询文章
	 * 
	 * @throws ParseException
	 */
	public List<BllArticle> searchBlog(String vblogType, String vTitle, String vstartDate, String vendDate,
			String vContent, String currentUserCode) throws SQLException, ParseException {
		List<BllArticle> lstBlogs = new ArrayList<BllArticle>();

		// 读取数据库
		StringBuilder strBulder = new StringBuilder();
		strBulder.append("SELECT * FROM bll_article b where ");

		if (vblogType != "") {
			strBulder.append(" b.TypeID = '");
			strBulder.append(vblogType);
			strBulder.append("' and ");
		}

		if (vTitle != "") {
			strBulder.append(" b.Title like '%");
			strBulder.append(vTitle);
			strBulder.append("%' and ");
		}
		if (vstartDate != "") {
			strBulder.append(" b.CreateTime >= date_format('");
			strBulder.append(vstartDate);
			strBulder.append(" 00:00:00"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (vendDate != "") {
			strBulder.append(" b.CreateTime <= date_format('");
			strBulder.append(vendDate);
			strBulder.append(" 23:59:59"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (vContent != "") {
			strBulder.append(" b.Content like '%");
			strBulder.append(vContent);
			strBulder.append("%' and ");
		}

		// 按照用户查询
		strBulder.append(" CreateBy ='");
		strBulder.append(currentUserCode);
		strBulder.append("'");
		lstBlogs = HibernateUtils.queryListParam(BllArticle.class, strBulder.toString(), null);

		return lstBlogs;
	}
}
