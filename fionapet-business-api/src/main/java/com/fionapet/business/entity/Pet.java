package com.fionapet.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 宠物
 * <p>
 * Created by tom on 2016-07-19 10:31:06.
 */
@Entity
@Table(name = "t_pet")
@ApiModel("宠物")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pet extends PetBase {

}

