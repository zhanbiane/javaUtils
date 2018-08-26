package com.sy.util.db.backup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

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
	private String[] uniqColumns;
	private int pageSize = 1000;
	private int finishCountInit = 0;
	
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
	public DbUniq(String dbName, String finishTable,String ip,int port,String username,String password,String...uniqColumns) {
		String jdbcUrl = "jdbc:mysql://"+ip+":"+port+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=true&rewriteBatchedStatements=true";
		this.finishTable = finishTable;
		this.finishTableRecycle = finishTable+"_recycle";
		this.uniqColumns = uniqColumns;
		String driver = "com.mysql.jdbc.Driver";
		jdbc = new JDBCUtil(jdbcUrl, username, password, driver);
	}

	
	public static void main(String[] args) {
		DbUniq uniq = new DbUniq("db_china_credits", "credit_all", "10.50.5.10",3306, "user_ccredits", "rye34edfcvfrt67yhnjju8","com_name","social_credit_code");
		
		uniq.execute();
		
	}
	public void execute() {
		List<tableMeta> tables = showTableNames("org_credit_", "new");
		readNow();
		createRecycle();
		int i=1;
		for (tableMeta tableMeta : tables) {
			System.out.println((i++)+"开始保存表："+tableMeta.getTableName());
			if(i>94) {
				batchProcess(tableMeta.getTableName(),tableMeta.getAutoLine());
			}
		}
		jdbc.closeConnect();
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
				if(finishTable.equals(tableName)) {
					finishCountInit = tableres.getInt(11);
				}
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (tableMeta tableMeta : list) {
			System.out.println(tableMeta.getTableName()+"\t\t"+tableMeta.getLine());
		}
		return list;
	}
	
	/**
	 * 读取最终表现有数据并加入缓存
	 */
	private void readNow() {
		System.out.println("初始化数据。。");
		try {
//			ResultSet res = jdbc.queryResultset("select count(*) from "+finishTable);
//			int count = 0;
//			if(res.next()) {
//				count = res.getInt(1);
//			}
			int count = finishCountInit;
			if(count != 0) {
				if(null == uniqColumns) {
					return;
				}
				int page = count%pageSize==0?count/pageSize:(count/pageSize+1);
				for(int i=0;i<page;i++) {
					String sql = "SELECT "+StringUtils.join(uniqColumns, ",")+" FROM `"+finishTable+"` WHERE id between "+(pageSize*i+1)+" and "+(pageSize*i+pageSize);
//					System.out.println("初始化："+sql);
					ResultSet columnRes = jdbc.queryResultset(sql);
					int columnCount = columnRes.getMetaData().getColumnCount();
					while(columnRes.next()) {
						String value =  "";
						for(int j=1;j<=columnCount;j++) {
							value+=columnRes.getString(j);
						}
						bloom.add(value);
					}
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
	private void batchProcess(String tableName,int tableCount) {
		try {
			int count = tableCount;
			if(count != 0) {
				if(null == uniqColumns) {
					return;
				}
				String tableColumns = StringUtils.join(tableColumns(finishTable),",");
				
				int page = count%pageSize==0?count/pageSize:(count/pageSize+1);
				for(int i=0;i<page;i++) {
					DateTime dt = new DateTime();
					List<Object[]> objList = new ArrayList<>();
					String sql = "SELECT * FROM `"+tableName+"` WHERE id between "+(pageSize*i+1)+" and "+(pageSize*i+pageSize);
					List<Map<String, Object>> queryList = jdbc.queryList(sql);
					String value =  "";
					for (Map<String, Object> map : queryList) {
						/////////
						String com_name = MapValue(map.get("com_name"));
						String social_credit_code = MapValue(map.get("social_credit_code"));
						if("".equals(com_name)) {
							continue;
						}
						value = com_name+social_credit_code;
						if(!bloom.check(value)) {
							bloom.add(value);
							String business_register_code = MapValue(map.get("business_register_code"));
							if("".equals(business_register_code)) {
								business_register_code = MapValue(map.get("business_register"));
							}
							String organination_code = MapValue(map.get("organination_code"));
							if("".equals(organination_code)) {
								organination_code = MapValue(map.get("org_code"));
							}
							String tax_code = MapValue(map.get("tax_code"));
							String source_url = MapValue(map.get("source_url"));
							String area = tableName.substring(11);
							
							objList.add(new Object[]{com_name,social_credit_code,business_register_code,
									organination_code,tax_code,source_url,dt.toDate(),area});
						}
						/////////
					}
					if(!objList.isEmpty()) {
						try {
							jdbc.updateBatch(insertsql(finishTable, tableColumns), objList);
							System.out.println("执行保存："+tableName+i);
						} catch (Exception e) {
							System.out.println("保存未知情况未入库的数据："+i);
							jdbc.updateBatch(insertsql(finishTableRecycle, tableColumns), objList);
						}
					}
					objList=null;
					queryList = null;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 自动创建回收表
	 * 注：要把复制过来的索引去除
	 */
	private  void createRecycle() {
		try {
			jdbc.update("create table "+finishTableRecycle+" like "+finishTable);
		} catch (Exception e) {
			System.out.println(finishTableRecycle+"《已存在。");
		}
	}
	
	
	/**
	 * 导入回收表中的数据
	 */
	public void saveRecycle() {
		
	}
	
	/**
	 * 读取表结构
	 * @param tableName
	 * @return
	 */
	private List<String> tableColumns(String tableName) {
        try {
        	List<String> cList = new ArrayList<>();
        	// 根据表名提前表里面信息：  
			ResultSet colRet = jdbc.getConnection().getMetaData().getColumns(null, "%", tableName, "%");
			while(colRet.next()){
            	String columnName = colRet.getString("COLUMN_NAME");
            	if(!columnName.equals("id")) {
            		cList.add(columnName);
            	}
            }
			return cList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	private static String MapValue(Object value) {
		if(null != value && !"null".equals(value.toString())) {
			return value.toString();
		}else {
			return "";
		}
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
//    	System.out.println(insertSql);
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
