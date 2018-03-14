package com.sy.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 入门参考：
 * http://www.cnblogs.com/hongten/archive/2011/03/29/1998311.html
 * 
 * @deccription jdbc工具类
 * @author zhanbiane
 * 2018年1月15日
 */
public class JDBCUtil {

	private String DRIVER = null;//驱动
	private String URL = null;//数据库链接
	private String USER = null;//用户名
	private String PASSWORD = null;//密码
	
	private Connection connection= null;//数据库连接对象、
	private PreparedStatement prepareStatement = null;
	private Statement statement = null;
	
	private ResultSet resultset = null;//结果集
	
	public JDBCUtil(String url, String username, String password, String driver) {
		if (driver == null || url == null || username == null || password == null) {
			throw new IllegalArgumentException("The parameters must not be null");
		}
		DRIVER = driver;
		URL = url;
		USER = username;
		PASSWORD = password;
	}
	
	/**
	 * 创建数据库的连接
	 * @return
	 */
	public Connection getConnection(){
		try {
			if(connection==null || connection.isClosed()){
				Class.forName(DRIVER);	//加载驱动类
				connection = DriverManager.getConnection(URL, USER, PASSWORD);	//建立数据库连接
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关闭连接
	 */
	public void closeConnect(){
		
		if(resultset!=null){
			try {
				resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(prepareStatement!=null){
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询
	 * @param hql 如select * from A where id = ?
	 * @param params
	 * @return
	 */
	public ResultSet queryResultset(String hql,Object ... params){
		connection =  this.getConnection();
		try {
			prepareStatement = connection.prepareStatement(hql);
			
			//参数判断
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					prepareStatement.setObject(i + 1, params[i]);
				}
			}
			resultset = prepareStatement.executeQuery(hql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	/**
	 * 查询
	 * @param hql 如select * from A where id = ?
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryList(String hql,Object ... params){
		connection =  this.getConnection();
		try {
			prepareStatement = connection.prepareStatement(hql);
			
			//参数判断
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					prepareStatement.setObject(i + 1, params[i]);
				}
			}
			resultset = prepareStatement.executeQuery(hql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.resultSet2List(resultset);
		
	}
	
	/**
	 * 大数据的查询,能快速显示第一行
	 * @return
	 */
	public ResultSet queryResultsetMore(String sql){
		connection =  this.getConnection();
		try {
			if(DRIVER.indexOf("mysql")!=-1){
				prepareStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				prepareStatement.setFetchSize(Integer.MIN_VALUE);
				prepareStatement.setFetchDirection(ResultSet.FETCH_REVERSE);
			}else{
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setFetchSize(1000);
			}
			resultset = prepareStatement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	/**
	 * 用于执行INSERT、UPDATE或 DELETE语句以及SQL DDL语句
	 * @return
	 */
	public int update(String sql){
		int count = 0;
		connection = this.getConnection();
		try {
			statement = connection.createStatement();
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 用于执行INSERT、UPDATE或 DELETE语句以及SQL DDL语句
	 * 此方法有重复操作时使用，更安全
	 */
	public int update(String hql,Object ...params){
		int count = 0;
		connection = this.getConnection();
		try {
			prepareStatement = connection.prepareStatement(hql);
			//参数判断
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					prepareStatement.setObject(i + 1, params[i]);
				}
			}
			count = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 大量执行DDL操作
	 * @param hql
	 * @param objs
	 */
	public void updateBatch(String hql,List<Object[]> objs) {
		connection = this.getConnection();
		try {
			prepareStatement = connection.prepareStatement(hql);
			//参数判断
			if(objs!=null&&!objs.isEmpty()) {
				for(Object[] params:objs) {
					if(params!=null){
						for (int i = 0; i < params.length; i++) {
							prepareStatement.setObject(i + 1, params[i]);
						}
						prepareStatement.addBatch();
					}
				}
				prepareStatement.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得数据库中所有的表
	 */
	public List<String> getTables() throws SQLException{  
		List<String> namelist = new ArrayList<String>();
		connection = this.getConnection();
		DatabaseMetaData dbMetData = connection.getMetaData(); 
		ResultSet res = dbMetData.getTables(null, "%", "%", new String[]{"TABLE"});
		while(res.next()){
			if(res.getString(4)!=null&&res.getString(4).equalsIgnoreCase("TABLE")){
				String tableName = res.getString(3);
				namelist.add(tableName);
			}
		}
		res.close();
		return namelist;
    }
	
	/**
	 * 结果集转List
	 * @param ResultSet
	 * @return
	 */
	public List<Map<String,Object>> resultSet2List(ResultSet result){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			ResultSetMetaData metaData = result.getMetaData();
			int columNum = metaData.getColumnCount();//列数
			
			while(resultset.next()){
				Map<String,Object> map = new HashMap<String, Object>();
				for(int i = 1;i<=columNum;i++){
					map.put(metaData.getColumnLabel(i), resultset.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 存储过程带有一个输出参数的方法
	 * 未经过验证，仅供参考
	 * 
	 * @param sql
	 *            存储过程语句
	 * @param params
	 *            参数数组
	 * @param outParamPos
	 *            输出参数位置
	 * @param SqlType
	 *            输出参数类型
	 * @return 输出参数的值
	 */
	public Object executeCall(String sql, Object[] params, int outParamPos, int SqlType){
		Object object = null;
		connection = this.getConnection();
		CallableStatement callableStatement =null;
		try {
			// 调用存储过程
			callableStatement = connection.prepareCall(sql);

			// 给参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					callableStatement.setObject(i + 1, params[i]);
				}
			}
			
			// 注册输出参数
			callableStatement.registerOutParameter(outParamPos, SqlType);

			// 执行
			callableStatement.execute();

			// 得到输出参数
			object = callableStatement.getObject(outParamPos);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			if(callableStatement!=null){
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}
	
	
	public static void main(String[] args) {
		//mysql5.x和mysql6.x会有些许不同
		String url = "jdbc:mysql://192.168.137.228:3306/dbtest?useUnicode=true&characterEncoding=utf8";
		String driver = "com.mysql.jdbc.Driver";
		JDBCUtil jdbc= new JDBCUtil(url, "root", "shenyang123", driver);
		Connection jdbcCon = jdbc.getConnection();
		try {
			jdbcCon.setAutoCommit(false);//开启事物
			//xxxxxxxxxxxxx执行insert,update,delete
			jdbcCon.commit();//执行命令
			jdbc.closeConnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
