package com.heyqing.disk.core.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * ClassName:Result
 * Package:com.heyqing.disk.core.response
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */

/**
 * 公共的返回对象
 */
//保证json序列化得时候，如果属性位null，key也就一起消失
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     *状态说明文案
     */
    private String msg;

    /**
     * 返回承载
     */
    private T data;

    private Result(Integer code){
        this.code = code;
    }

    private Result(Integer code ,String msg){
        this.code = code;
        this.msg = msg;
    }

    private Result(Integer code ,String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess(){
        return Objects.equals(this.code,ResponseCode.SUCCESS.getCode());
    }

    public static <T> Result<T> success(){
        return new Result<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Result<T> success(String msg){
        return new Result<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> Result<T> data(T data){
        return new Result<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(),data);
    }

    public static <T> Result<T> fail(){
        return new Result<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> Result<T> fail(String msg){
        return new Result<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> Result<T> fail(Integer code ,String msg){
        return new Result<T>(code,msg);
    }

    public static <T> Result<T> fail(ResponseCode responseCode){
        return new Result<T>(responseCode.getCode(),responseCode.getDesc());
    }
}
