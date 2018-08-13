package com.sy.util.db.backup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sy.util.db.JDBCUtil;
import com.sy.util.filter.BloomFilter;

/**
 * @deccription MYSQL数据库去重，合并成新表
 * 新表需手动创建，且拥有唯一属性字段
 * 本方法使用布隆过滤器
 * @author zhanbiane
 * 2018年8月13日
 */
public class DbUniq {
	private static BloomFilter bloom = new BloomFilter(30000000);
	
	private String finishTable;
	private String finishTableRecycle;
	private JDBCUtil jdbc;
	private String[] columns;
	private int pageSize = 100000;
	
	/**
	 * 
	 * @param 数据库名
	 * @param 最终数据表
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param columns唯一索引字段[col1,col2]
	 */
	public DbUniq(String dbName, String finishTable,String ip,int port,String username,String password,String...columns) {
		String jdbcUrl = "jdbc:mysql://"+ip+":"+port+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		this.finishTable = finishTable;
		this.finishTableRecycle = finishTable+"_recycle";
		this.columns = columns;
		String driver = "com.mysql.jdbc.Driver";
		jdbc = new JDBCUtil(jdbcUrl, username, password, driver);
	}

	
	public static void main(String[] args) {
		DbUniq uniq = new DbUniq("db_china_credits", "credit_all", "10.50.5.10",3306, "user_ccredits", "rye34edfcvfrt67yhnjju8");
		uniq.showTableNames("org_credit", "_new");
		System.out.println(StringUtils.join(new String[] {"a","b"}, ","));
	}
	
	/**
	 * 返回所有指定表及数据量
	 * @param 表前缀
	 * @param 不包含的后缀
	 * @return 数据根据数量升序排列
	 */
	private List<tableMeta> showTableNames(String pro,String not_end) {
		List<tableMeta> list = new ArrayList<>();
		try {
			ResultSet tableres = jdbc.queryResultset("show table status");
			while(tableres.next()){
				String tableName = tableres.getString(1);
				if(null != pro && !tableName.startsWith(pro)) {
					continue;
				}
				if(null != not_end && tableName.endsWith(not_end)) {
					continue;
				}
				tableMeta meta = new tableMeta(tableres.getString(1), tableres.getInt(5), tableres.getInt(11), tableres.getString(12));
				list.add(meta);
			}
			Collections.sort(list, new Comparator<tableMeta>() {
				public int compare(tableMeta o1, tableMeta o2) {
					return o1.getLine().compareTo(o2.getLine());
				}
			});
			System.out.println(list);
			for (tableMeta tableMeta : list) {
				System.out.print(tableMeta.getTableName());
				System.out.println(tableMeta.getLine());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 读取最终表现有数据并加入缓存
	 */
	private void readNow() {
		try {
			ResultSet res = jdbc.queryResultset("select count(*) from "+finishTable);
			int count = 0;
			if(res.next()) {
				count = res.getInt(1);
			}
			if(count != 0) {
				if(null == columns) {
					return;
				}
				int page = count%pageSize==0?count/pageSize:(count/pageSize+1);
				for(int i=0;i<page;i++) {
					String sql = "SELECT "+StringUtils.join(columns, ",")+" FROM `"+finishTable+"` WHERE id between "+(pageSize*i+1)+" and "+(pageSize*i+pageSize);
					ResultSet columnRes = jdbc.queryResultset(sql);
					int columnCount = columnRes.getMetaData().getColumnCount();
					String value =  "";
					for(int j=1;j<=columnCount;j++) {
						value+=columnRes.getString(j);
					}
					bloom.add(value);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 批量读取数据与缓存对比并存入数据库
	 */
	private static void batchProcess() {
		
	}
	
	/**
	 * 自动创建回收表
	 */
	private  void createRecycle() {
		jdbc.update("create table "+finishTableRecycle+" like "+finishTable);
	}
	
	/**
	 * 保存未知情况未入库的数据
	 */
	private static void recycle() {
//		jdbc.
	}
	
	/**
	 * 导入回收表中的数据
	 */
	public void saveRecycle() {
		
	}
	
	/**
	 * 生成hql
	 * @param tablename
	 * @param colums
	 * @return
	 */
    private static String insertsql(String tablename, String colums){
    	int length = colums.split(",").length;
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<length;i++){
    		if(i==0){
    			sb.append("?");
    		}else{
    			sb.append(",?");
    		}
    	}
    	String insertSql = "insert into "+tablename+" ("+colums+") values("+sb.toString()+")";
    	return insertSql;
    }
}

class tableMeta {
	private String tableName;
	//行数
	private Integer line;
	//自增主键
	private Integer autoLine;
	//创建时间
	private String createTime;
	/**
	 * @return the line
	 */
	public Integer getLine() {
		return line;
	}
	/**
	 * @return the autoLine
	 */
	public int getAutoLine() {
		return autoLine;
	}
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName
	 * @param line
	 * @param autoLine
	 * @param createTime
	 */
	public tableMeta(String tableName, int line, int autoLine, String createTime) {
		super();
		this.tableName = tableName;
		this.line = line;
		this.autoLine = autoLine;
		this.createTime = createTime;
	}
	
}
