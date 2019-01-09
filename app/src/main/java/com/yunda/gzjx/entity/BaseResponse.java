package com.yunda.gzjx.entity;

import java.io.Serializable;

/**
 * 根据实际义务需求更改
 *
 * @param <T>
 */
public class BaseResponse<T> implements Serializable {
    private Boolean success;
    private T data;
    private String content;
    //    private T entity;
    private String message;
    //    private String id;
    //    private Integer totalProperty;
    //    private Map<String,String> iamges;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
