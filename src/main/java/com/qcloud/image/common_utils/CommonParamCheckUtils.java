package com.qcloud.image.common_utils;


import com.qcloud.image.exception.ParamException;

/**
 * @author chengwu 封装一些参数检查的类,如果检查未通过抛出参数异常
 */
public class CommonParamCheckUtils {

	/**
	 * 判断参数是否为NULL，如果为NULL，抛出参数异常
	 * 
	 * @param objName
	 *            参数名
	 * @param obj
	 *            参数对象
	 * @throws ParamException 参数异常
	 */
	public static void AssertNotNull(String objName, Object obj) throws ParamException {
		if (obj == null) {
			throw new ParamException(objName + " is null, please check!");
		}
	}
        
        /**
	 * 判断整数参数是否为0，如果为0，抛出参数异常
	 * 
	 * @param objName
	 *            参数名
	 * @param value
	 *            参数值
	 * @throws ParamException 参数异常
	 */
	public static void AssertNotZero(String objName, int value) throws ParamException {
		if (value == 0) {
			throw new ParamException(objName + " is 0, please check!");
		}
	}
        
                /**
	 * 判断参数是否超出阈值，如果是，抛出参数异常
	 * 
	 * @param objName
	 *            参数名
	 * @param value
	 *            参数值
	 * @param limit
	 *            参数阈值
	 * @throws ParamException 参数异常
	 */
	public static void AssertExceed(String objName, int value, int limit) throws ParamException {
                if (limit < value) {
                    throw new ParamException(objName + " exceed limit, please check!");
                }
	}

}
