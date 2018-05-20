package com.sanye.manage.controller;

import com.sanye.manage.DTO.FeedbackDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.FeedbackDetail;
import com.sanye.manage.dataobject.FeedbackInfo;
import com.sanye.manage.dataobject.FeedbackType;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.form.FeedbackTypeForm;
import com.sanye.manage.service.FeedbackService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 7:56
 * @Description description
 */
@Controller
@RequestMapping("/oa/feedback")
@RequiresAuthentication
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private PersonnelInfoService personnelInfoService;

    @GetMapping("/info/index")
    public ModelAndView index(Map<String, Object> map, @RequestParam(value = "id", defaultValue = "") Integer id) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        Integer editStatus = 0;
        if (id != null) {
            feedbackDTO = feedbackService.finOneFeedbackDTOByFeedbackInfoId(id);
            editStatus = 1;
        }
        List<FeedbackType> feedbackTypeList = feedbackService.finAllFeedbackType();
        map.put("editStatus", editStatus);
        map.put("pageTitle", "反馈信息");

        map.put("feedbackDTO", feedbackDTO);
        map.put("feedbackTypeList", feedbackTypeList);
        if (id != null) {
            if (feedbackDTO.getFeedbackinfo().getSendUserId() != ShiroGetSession.getUserInfo().getId()) {
                map.put("pageId", 40);

                return new ModelAndView("view/feedbackReply", map);
            }
        }
        map.put("pageId", 42);
        return new ModelAndView("view/feedback", map);
    }

    @PostMapping("/info/save")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<Map<String, Object>> saveInfo(@RequestParam(value = "title") String title,
                                                  @RequestParam(value = "feedbackTypeId") Integer feedbackTypeId,
                                                  @RequestParam(value = "content") String content) {
        Map<String, Object> map = new HashMap<>();
        //info
        FeedbackInfo feedbackInfo = new FeedbackInfo();
        feedbackInfo.setBackStatus(0);
        feedbackInfo.setFeedbackTypeId(feedbackTypeId);
        feedbackInfo.setSendTime(GetTimeUtil.getDate());
        feedbackInfo.setSendUserId(ShiroGetSession.getUserInfo().getId());
        feedbackInfo.setTitle(title);
        //accentUserId
        FeedbackType feedbackType = feedbackService.findOneFeedbackType(feedbackTypeId);
        feedbackInfo.setAccentUserId(personnelInfoService.findOne(feedbackType.getAcceptPersonnelId()).getUserInfo().getId());
        FeedbackInfo result = feedbackService.saveFeedbackInfo(feedbackInfo);
        //detail
        FeedbackDetail feedbackDetail = new FeedbackDetail();
        feedbackDetail.setSendUserId(result.getSendUserId());
        feedbackDetail.setContent(content);
        feedbackDetail.setFeedbackInfoId(result.getId());
        feedbackDetail.setBackUserId(result.getAccentUserId());
        feedbackDetail.setSendTime(result.getSendTime());
        feedbackService.saveFeedbackDetail(feedbackDetail);
        map.put("url", "/oa/feedback/info/index.html?id=" + result.getId());
        return ResultVOUtil.success(map);
    }

    @PostMapping("/info/finish")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<Map<String, Object>> infoFinish(@RequestParam Integer id,
                                                    @RequestParam Integer backStatus) {
        FeedbackInfo feedbackInfo = feedbackService.findOneFeedbackInfo(id);
        if (feedbackInfo == null) {
            return ResultVOUtil.error(100, "无记录");
        }
        feedbackInfo.setBackStatus(1);
        feedbackService.saveFeedbackInfo(feedbackInfo);
        return ResultVOUtil.success();
    }

    @RequiresPermissions("feedback:list")
    @GetMapping("/info/list")
    public ModelAndView InfoList(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "backStatus", defaultValue = "1") Integer backStatus) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<FeedbackInfo> feedbackInfoPage = feedbackService.findAllFeedbackInfoByUserId(ShiroGetSession.getUserInfo().getId(), backStatus, pageRequest);
        Integer addStatus = 1;
        if (ShiroGetSession.getUserInfo().getUserType().equals("personnel")) {
            addStatus = 0;

        }
        map.put("pageTitle", "反馈消息列表");
        map.put("pageId", 41);
        map.put("pageContent", feedbackInfoPage);
        map.put("size", size);
        map.put("addStatus", addStatus);
        map.put("backStatus", backStatus);
        map.put("currentPage", page);
        map.put("url", "/oa/feedback/info/list.html");
        return new ModelAndView("view/feedbackList", map);
    }

    @RequiresPermissions("feedback:check")
    @GetMapping("/info/check")

    public ModelAndView InfoCheck(Map<String, Object> map,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "backStatus", defaultValue = "1") Integer backStatus) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<FeedbackInfo> feedbackInfoPage = feedbackService.findAllFeedbackInfoByUserId(ShiroGetSession.getUserInfo().getId(), backStatus, pageRequest);
        Integer addStatus = 1;
        if (ShiroGetSession.getUserInfo().getUserType().equals("personnel")) {
            addStatus = 0;
        }
        map.put("pageId", 40);
        map.put("pageTitle", "反馈消息处理");
        map.put("pageContent", feedbackInfoPage);
        map.put("size", size);
        map.put("addStatus", addStatus);
        map.put("backStatus", backStatus);
        map.put("currentPage", page);
        map.put("url", "/oa/feedback/info/check.html");
        return new ModelAndView("view/feedbackList", map);
    }

    @PostMapping("/detail/send")
    @ResponseBody
    public ResultVO<Map<String, Object>> detailSend(@RequestParam(value = "id") Integer id,
                                                    @RequestParam(value = "sendContent") String sendContent) {
        Map<String, Object> map = new HashMap<>();
        map = feedbackService.detailSend(id, sendContent);
        return ResultVOUtil.success(map);
    }

    @PostMapping("/detail/reply")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<Map<String, Object>> detailReply(@RequestParam(value = "id") Integer id,
                                                     @RequestParam(value = "backContent") String backContent) {
        feedbackService.detailReply(id, backContent);
        return ResultVOUtil.success();
    }

    @RequiresPermissions("feedbackType:add")
    @GetMapping("/type/index")
    public ModelAndView typeIndex(Map<String, Object> map,
                                  @RequestParam(value = "id", defaultValue = "") Integer id) {
        List<PersonnelInfo> personnelInfoList = personnelInfoService.findAll();
        FeedbackType feedbackType = new FeedbackType();
        if (id != null) {
            feedbackType = feedbackService.findOneFeedbackType(id);
        }
        map.put("pageTitle", "反馈类型添加");
        map.put("pageId", 38);
        map.put("personnelInfoList", personnelInfoList);
        map.put("feedbackType", feedbackType);
        return new ModelAndView("view/feedbackTypeAdd", map);
    }

    @RequiresPermissions("feedbackType:list")
    @GetMapping("/type/list")
    public ModelAndView typeList(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<FeedbackType> feedbackTypePage = feedbackService.finAllFeedbackType(pageRequest);
        map.put("pageId", 39);
        map.put("pageTitle", "反馈类型列表");
        map.put("pageContent", feedbackTypePage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/feedback/type/list.html");
        return new ModelAndView("view/feedbackTypeList", map);
    }

    @PostMapping("/type/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveType(@Valid FeedbackTypeForm feedbackTypeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        FeedbackType feedbackType = new FeedbackType();
        if (feedbackTypeForm.getId() != null) {
            feedbackType = feedbackService.findOneFeedbackType(feedbackTypeForm.getId());
        }
        BeanUtils.copyProperties(feedbackTypeForm, feedbackType);
        feedbackService.saveFeedbackType(feedbackType);
        return ResultVOUtil.success();
    }

    /**
     * @param id
     * @return
     */
    @PostMapping("/type/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteType(@RequestParam(value = "id") Integer id) {
        if (feedbackService.findOneFeedbackType(id) == null) {
            return ResultVOUtil.error(100, "无该记录无需删除！");
        }
        feedbackService.deleteFeedbackType(id);
        return ResultVOUtil.success();
    }


}
