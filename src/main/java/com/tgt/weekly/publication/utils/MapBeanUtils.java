package com.tgt.weekly.publication.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.Map;

/**
 * @Description:
 * @Author: Allen Li
 * @CreateDate: 2018/6/19
 * @Version: 0.0.1
 */
public class MapBeanUtils {

    public static <T>T mapToBean(Map map, T bean) throws Exception {
        if (map == null){
            return null;
        }
        BeanUtils.populate(bean, map);
        return bean;
    }

    public static Map beanToMap(Object obj) {
        if(obj == null){
            return null;
        }
        return BeanMap.create(obj);
    }
}
