package com.yunda.gzjx.entity;

import java.io.Serializable;
import java.util.Map;

/**根据实际义务需求更改
 * @param <T>
 */
public class BaseResponse<T> implements Serializable {
    private Boolean success;
    private T data;
    private T entity;
    private Integer totalProperty;
    private String id;
    private String message;
    private Map<String,String> iamges;

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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Integer getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(Integer totalProperty) {
        this.totalProperty = totalProperty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getIamges() {
        return iamges;
    }

    public void setIamges(Map<String, String> iamges) {
        this.iamges = iamges;
    }
}
