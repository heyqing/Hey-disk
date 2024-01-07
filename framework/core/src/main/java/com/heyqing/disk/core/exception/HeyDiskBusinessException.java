package com.heyqing.disk.core.exception;

import com.heyqing.disk.core.response.ResponseCode;
import lombok.Data;

/**
 * ClassName:HeyDiskBusinessException
 * Package:com.heyqing.disk.core.exception
 * Description:
 *      自定义全局业务异常类
 * @Date:2024/1/6
 * @Author:Heyqing
 */
@Data
public class HeyDiskBusinessException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    public HeyDiskBusinessException(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
    }

    public HeyDiskBusinessException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public HeyDiskBusinessException(String msg){
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.msg = msg;
    }

    public HeyDiskBusinessException(){
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.msg = ResponseCode.ERROR_PARAM.getDesc();
    }
}
