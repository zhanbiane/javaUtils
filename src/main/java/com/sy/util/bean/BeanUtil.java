package com.sy.util.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @deccription 实例转换
 *
 * @author zhanbiane
 * 2018年1月11日
 */
public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	/**
	 * map转实体
	 * @return
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		if (map != null) {
			try {
				T obj = clazz.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					Method setter = property.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, map.get(property.getName()));
					}
				}
				return obj;
			} catch (Exception e) {
				logger.error(e.toString());
			} 
		}
		return null;
	}
	
	/**
	 * 实体转map
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		if (obj != null) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();
					if (!key.equalsIgnoreCase("class")) {
						Method getter = property.getReadMethod();
						Object value = getter == null ? null : getter.invoke(obj);
						map.put(key, value);
					}
				}
				return map;
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return null;
	}
	
}
