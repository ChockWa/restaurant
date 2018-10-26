package com.sss.restaurant.goods.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
@TableName("sys_goods")
public class Goods implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 类目id
     */
    private Long categoryId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品子名称
     */
    private String subName;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品原价
     */
    private BigDecimal amountOrigin;

    /**
     * 商品销售价
     */
    private BigDecimal amount;

    /**
     * 商品图片(多张图片用逗号隔开)
     */
    private String pics;

    /**
     * 总数量
     */
    private Integer total;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 已购数量
     */
    private Integer qtySale;

    /**
     * 商品权重
     */
    private Integer weight;

    /**
     * 商品销售状态1-上架2-下架
     */
    private Integer saleStatus;

    /**
     * 商品状态1-正常2-删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updator;

    private static final long serialVersionUID = 1L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountOrigin() {
        return amountOrigin;
    }

    public void setAmountOrigin(BigDecimal amountOrigin) {
        this.amountOrigin = amountOrigin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getQtySale() {
        return qtySale;
    }

    public void setQtySale(Integer qtySale) {
        this.qtySale = qtySale;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }
}