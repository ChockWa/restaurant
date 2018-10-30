package com.sss.restaurant.table.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@TableName("sys_table_use")
public class TableUse implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 桌子uid
     */
    private String tableUid;

    /**
     * token
     */
    private String accessToken;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 状态1-点餐中2-点餐完成3-上餐完毕
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 点餐完成时间
     */
    private Date submitTime;

    /**
     * 上餐完毕时间
     */
    private Date overTime;

    private static final long serialVersionUID = 1L;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableUid() {
        return tableUid;
    }

    public void setTableUid(String tableUid) {
        this.tableUid = tableUid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }
}