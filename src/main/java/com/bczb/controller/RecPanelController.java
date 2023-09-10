package com.bczb.controller;

import com.bczb.pojo.Rat;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.IRecPanelService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/rec-panel")
public class RecPanelController {
    
    @Resource
    private IRecPanelService recPanelService;



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReturnGroupInfoParams {
        private String exName;
        private String name;
        private ArrayList<Rat> rats;
    }

    @GetMapping("/{gId}")
    public Result GetGroupInfoByGroupId(@PathVariable("gId") String gId) throws BusinessException  {
        // 判断exId和status是否为数字
        if(gId == null) {
            return Result.error("参数错误");
        }
        if(!recPanelService.isGroupExist(gId)) {
            return Result.error("组别不存在");
        }
        ReturnGroupInfoParams returnGroupInfoParams = new ReturnGroupInfoParams();
        returnGroupInfoParams.setExName(recPanelService.getExNameByGId(gId));
        returnGroupInfoParams.setName(recPanelService.getGroupNameByGId(gId));
        returnGroupInfoParams.setRats(recPanelService.getRatsByGId(gId));
        return Result.data(returnGroupInfoParams);
    }
}
