package com.ppstream.mt.utils;


public class StringUtil {
    
    /**
     * 判断是否为空(null或 长度为0)
     * @param str
     * @return
     * @date 2010-3-12
     */
    public  static boolean isEmpty(String str){
        
        if(str == null){
            return true;
        }
        
        str = str.trim();
        
        if("".equals(str)){
            return true;
        }
        
        return false;
    }
}
