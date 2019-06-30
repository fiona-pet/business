package com.fionapet.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tom on 16/6/29.
 */


@MappedSuperclass
public class CMSPEntity extends StatusEntity {
    /**
     * 创建人
     */
    private Persons createUser;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 修改人
     */
    private String updateUserId;
    /**
     * 修改日期
     */
    private Date updateDate;

//    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name="create_user_id")
    public Persons getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Persons createUser) {
        this.createUser = createUser;
    }

//    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

//    @JsonIgnore
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

//    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {

        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
