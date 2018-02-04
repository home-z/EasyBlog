package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.CommentDAO;
import com.blog.po.BllCommont;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:32:55
 * @description：TODO
 */
@Repository
public class CommentDAOImpl implements CommentDAO {

	@Override
	public List<BllCommont> getCommentListByUser(String userId) {
		List<BllCommont> typeList = HibernateUtils.queryListParam(BllCommont.class,
				"select * from bll_commont where creator='" + userId + "'");

		return typeList;
	}

	@Override
	public boolean deleteComment(String toDeleteIds) {
		String[] deleteidArray = toDeleteIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_commont where id in (");

		for (int i = 0; i < deleteidArray.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteidArray[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public List<BllCommont> getCommentById(String articleID) {
		// 读取该文章的评论
		List<BllCommont> comList = HibernateUtils.queryListParam(BllCommont.class,
				"select * from bll_commont where ArticleID='" + articleID + "' order by createtime asc");

		return comList;
	}
}
