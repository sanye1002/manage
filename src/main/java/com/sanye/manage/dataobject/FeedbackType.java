package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-11 上午 10:35
 * @Description description
 */
@Entity
@Data
@Table(name = "feedback_type")
public class FeedbackType {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer acceptPersonnelId;

    private String acceptPersonnelName;

    private String typeName;


}
