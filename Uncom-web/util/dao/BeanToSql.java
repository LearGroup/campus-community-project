package dao;

import bean.articleData;
import bean.commentData;
import bean.userData;

public class BeanToSql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql=TableUtils.getCreateTableSQl(commentData.class);
		System.out.println(sql);
	}

}
