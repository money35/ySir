package com.money.controller;

import com.github.pagehelper.PageInfo;
import com.money.pojo.Speaker;
import com.money.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "speaker",produces = { "text/html;charset=UTF-8;", "application/json;charset=UTF-8;" })
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;
    @RequestMapping("showSpeakerList")
    public ModelAndView showSpeakerList( @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "3") Integer pageSize
    ){
        PageInfo pageInfo = speakerService.findAllSpeakerByPage(pageNum, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("behind/speakerList");
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView ;
    }

    @RequestMapping("addSpeaker")
    public ModelAndView addSpeaker(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("behind/addSpeaker");

        return modelAndView;
    }

    @RequestMapping("edit")
    public ModelAndView edit(Speaker speaker){
        Integer id = speaker.getId();
        System.out.println(id);
        Speaker speaker1 = speakerService.findSpeakerById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("behind/addSpeaker");
        modelAndView.addObject("speaker",speaker1);
        return modelAndView ;
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Speaker speaker){
        //System.out.println(speaker);
        if(null != speaker.getId() && !"".equals(speaker.getId())){
            //有id时进行修改操作
            Integer row = speakerService.updateSpeaker(speaker);
            if(row==1){
                return "forward:showSpeakerList";
            }else {
                return "error.jsp";
            }
        }else {
            //无id时进行添加操作
            Integer row = speakerService.addSpeaker(speaker);
            if(row==1){
                return "forward:showSpeakerList";
            }else {
                return "error.jsp";
            }
        }

    }

    //根据id删除相应的主讲人
    @RequestMapping("speakerDel")
    @ResponseBody
    public String speakerDel(Speaker speaker,HttpServletRequest request){
        System.out.println(speaker.getId());
        Integer row = speakerService.delSpeakerById(speaker.getId());
        String data = null;
        if (row==1){
            data = "success";
        }
        return data;
    }
}
