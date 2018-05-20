package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.FeedbackInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:46
 * @Description description
 */
@Data
public class FeedbackDTO {

    private Integer id;
    //反馈id
    private FeedbackInfo feedbackinfo;

    private List<FeedbackDetailDTO> detailDTOList;
}
