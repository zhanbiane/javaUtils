package com.sy.util.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CacheUserPool {
    private static CacheUserPool cachePool;
    /**
     * 当前使用对象数组
     */
    private List<LoginUser> cacheItems;
    /**
     * 当前使用编号
     */
    private Integer itemIndex;
    private CacheUserPool() {
    	List<LoginUser> users = Collections.synchronizedList(new ArrayList<>());
    	users.add(new LoginUser("js_001","jiansheng001"));
    	users.add(new LoginUser("sx_001","sx123457"));
    	users.add(new LoginUser("sy_123456","shenyang123"));
        cacheItems = users;
        itemIndex = 0;
    }
    /**
     * 获取唯一实例
     * @return instance
     */
    public static CacheUserPool getInstance() {
        if (cachePool ==null) {
            synchronized (CacheUserPool.class) {
                if (cachePool ==null) {
                    cachePool =new CacheUserPool();
                }
            }
        }
        return cachePool;
    }
    
    /**
     * 获取所有cache信息
     * @return cacheItems
     */
    public List<LoginUser> getCacheItems() {
        return this.cacheItems;
    }
    
    /**
     * 清空cache
     */
    public void clearAllItems() {
        cacheItems.clear();
    }
    
    /**
     * 获得当前使用的cache
     * @return
     */
    public LoginUser getCacheItem() {
        if (this.cacheItems.size()>this.itemIndex) {
            return this.cacheItems.get(this.itemIndex);
        }
        return null;
    }
    
    public LoginUser getNextChacheItem() {
    	int itemSize = this.cacheItems.size();
    	if(itemSize>0) {
    		if (itemSize>(this.itemIndex+1)) {
    			this.itemIndex +=1;
    		}else {
    			this.itemIndex = 0;
    		}    		
    		return this.cacheItems.get(this.itemIndex);
    	}
    	return null;
    }
    
    /**
     * 获取指定cache信息
     * @return cacheItem
     */
    public LoginUser getCacheItem(int index) {
        if (this.cacheItems.size()>index) {
            return cacheItems.get(index);
        }
        return null;
    }
    
    /**
     * 删除一个cache
     */
    public void removeCacheItem(int index) {
        if (this.cacheItems.size()>index) {
            cacheItems.remove(index);
        }
    }
    
    /**
     * 获取cache长度
     * @return size
     */
    public int getSize() {
        return cacheItems.size();
    }
    
}
class LoginUser {
	private String username;
	private String password;
	
	public LoginUser() {
		super();
	}
	public LoginUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

