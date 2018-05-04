package com.sanye.manage.controller;



import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.config.UploadConfig;
import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import com.sanye.manage.dataobject.ItemInfo;
import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import com.sanye.manage.dataobject.SpendingInfo;
import com.sanye.manage.service.AnchorSalaryAdvanceService;
import com.sanye.manage.service.ItemInfoService;
import com.sanye.manage.service.PersonnelSalaryAdvanceService;
import com.sanye.manage.service.SpendingService;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.UploadUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-16 上午 11:06
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@RequiresAuthentication
public class UploadController {

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private SpendingService spendingService;
    @Autowired
    private PersonnelSalaryAdvanceService personnelSalaryAdvanceService;
    @Autowired
    private AnchorSalaryAdvanceService anchorSalaryAdvanceService;
    @Autowired
    private ItemInfoService itemInfoService;
    @PostMapping("/img/{type}")
    private ResultVO<Map<String, String>> uploadFile(HttpServletRequest request,
                                                     @PathVariable String type,
                                                     MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<>();

        String path = uploadConfig.getPath()+File.separator+type;
        log.info("path={}",uploadConfig.getPath()+File.separator+type );

        String src = UploadUtil.uploadFile(file, path,type);
        if (src != null) {
            map.put("src", src);
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(100, "上传失败");
        }

    }
    @PostMapping("/imgs/{type}/{id}")
    @Synchronized
    private ResultVO<Map<String, String>> uploadIMGs(HttpServletRequest request,
                                                     @PathVariable String type,
                                                     @PathVariable Integer id,
                                                     MultipartFile file) throws Exception {
        String path = uploadConfig.getPath()+File.separator+type;
        Map<String, String> map = new HashMap<>();
        /**日常开支图片上传**/
        if (type.equals("spending")){
            log.info("id={}",id);
            SpendingInfo spendingInfo = spendingService.findOne(id);
            String src = UploadUtil.uploadFile(file, path,type);
            if (src != null) {
                if (spendingInfo.getImg()==null){
                    spendingInfo.setImg(src);
                }else {
                    spendingInfo.setImg(spendingInfo.getImg()+","+src);
                }
                map.put("src", src);
                log.info("img={}",spendingInfo.getImg());
                spendingService.save(spendingInfo);
                return ResultVOUtil.success(map);
            } else {
                return ResultVOUtil.error(100, "上传失败");
            }
        }
        if (type.equals("personnelSalaryAdvance")){
            log.info("id={}",id);
            PersonnelSalaryAdvance personnelSalaryAdvance = personnelSalaryAdvanceService.findOne(id);
            String src = UploadUtil.uploadFile(file, path,type);
            if (src != null) {
                if (personnelSalaryAdvance.getImg()==null){
                    personnelSalaryAdvance.setImg(src);
                }else {
                    personnelSalaryAdvance.setImg(personnelSalaryAdvance.getImg()+","+src);
                }
                map.put("src", src);
                log.info("img={}",personnelSalaryAdvance.getImg());
                personnelSalaryAdvanceService.save(personnelSalaryAdvance);
                return ResultVOUtil.success(map);
            } else {
                return ResultVOUtil.error(100, "上传失败");
            }
        }
        if (type.equals("anchorSalaryAdvance")){
            log.info("id={}",id);
            AnchorSalaryAdvance anchorSalaryAdvance = anchorSalaryAdvanceService.findOne(id);
            String src = UploadUtil.uploadFile(file, path,type);
            if (src != null) {
                if (anchorSalaryAdvance.getImg()==null){
                    anchorSalaryAdvance.setImg(src);
                }else {
                    anchorSalaryAdvance.setImg(anchorSalaryAdvance.getImg()+","+src);
                }
                map.put("src", src);
                log.info("img={}",anchorSalaryAdvance.getImg());
                anchorSalaryAdvanceService.save(anchorSalaryAdvance);
                return ResultVOUtil.success(map);
            } else {
                return ResultVOUtil.error(100, "上传失败");
            }
        }
        if (type.equals("item")){
            log.info("id={}",id);
            ItemInfo itemInfo = itemInfoService.findOne(id);
            String src = UploadUtil.uploadFile(file, path,type);
            if (src != null) {
                if (itemInfo.getImg()==null){
                    itemInfo.setImg(src);
                }else {
                    itemInfo.setImg(itemInfo.getImg()+","+src);
                }
                map.put("src", src);
                log.info("img={}",itemInfo.getImg());
                itemInfoService.save(itemInfo);
                return ResultVOUtil.success(map);
            } else {
                return ResultVOUtil.error(100, "上传失败");
            }
        }
        return ResultVOUtil.success();


    }
}
