package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.CheckInfo;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-20 上午 11:57
 * @Description description
 */
@Data
public class CheckNoticeDTO<T> {

    private CheckInfo checkInfo;

    private T data;
}
