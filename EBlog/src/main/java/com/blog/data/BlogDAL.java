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

	/**
	 * 报表统计
	 * 
	 * @param chartid
	 * @param styleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ResultSet getBlogStatistics(String styleType, String startDate, String endDate) {
		// TODO
/*		<option value="0">按天统计</option>
		<option value="1">按月统计</option>
		<option value="2">按年统计</option>*/
		
		//按天统计
		//select date_format(createtime,'%Y-%m-%d') postDate,count(id) postCount from bll_article 
		//where createtime>= date_format('2016-06-10 00:00:00', '%Y-%m-%d %T') and createtime<= date_format('2016-08-10 23:59:59', '%Y-%m-%d %T') 
		//group by date_format(createtime,'%Y-%m-%d')
		
		//按月统计
		//select date_format(createtime,'%Y-%m') postDate,count(id) postCount from bll_article 
		//where createtime>= date_format('2016-06-10 00:00:00', '%Y-%m-%d %T') and createtime<= date_format('2016-08-10 23:59:59', '%Y-%m-%d %T') 
		//group by date_format(createtime,'%Y-%m')
		
		//按年统计
		 //select date_format(createtime,'%Y') postDate,count(id) postCount from bll_article 
		 //where createtime>= date_format('2016-06-10 00:00:00', '%Y-%m-%d %T')
		  // and createtime<= date_format('2016-08-10 23:59:59', '%Y-%m-%d %T')
		   //group by date_format(createtime,'%Y')
		
		String 	strSql=" select date_formatstyleType postDate,count(id) postCount from bll_article where createtime>= date_format('"+startDate+" 00:00:00', '%Y-%m-%d %T') and createtime<= date_format('"+endDate+" 23:59:59', '%Y-%m-%d %T') group by date_formatstyleType";
		switch (styleType) {
		case "0":
			strSql=strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		case "1":
			strSql=strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m')");
			break;
		case "2":
			strSql=strSql.replace("date_formatstyleType", "date_format(createtime,'%Y')");
			break;
		default:
			strSql=strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		}

		ResultSet rs = DbAction.getQuery(strSql);

		return rs;
	}
}
