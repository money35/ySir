package com.money.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.money.pojo.Condition;
import com.money.pojo.Course;
import com.money.pojo.Speaker;
import com.money.pojo.Video;
import com.money.service.CourseService;
import com.money.service.SpeakerService;
import com.money.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("video")
public class VideoListController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CourseService courseService;

    //显示所有video的信息
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value = "pageNum",required = false,defaultValue ="1" ) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "title",required = false,defaultValue = "") String title,
                             @RequestParam(value = "speakerId",required = false,defaultValue = "0") Integer speakerId,
                             @RequestParam(value = "courseId",required = false,defaultValue = "0") Integer courseId){
            Condition condition = new Condition(title,speakerId,courseId);
//        System.out.println(title+"==="+speakerId+"==="+courseId);
        ModelAndView modelAndView = new ModelAndView();
        //根据条件的状况的不同进行不同的查询
        if(title.equals("")&&speakerId==0&&courseId==0){
            //进行分页查询
            PageInfo pageInfo = videoService.findAllVideoByPage(pageNum, pageSize);
            modelAndView.addObject("pageInfo",pageInfo);
        }else {
            //进行条件查询
            PageInfo pageInfo = videoService.findVideoByCondition(pageNum,pageSize,condition);
            modelAndView.addObject("pageInfo",pageInfo);
            modelAndView.addObject("condition",condition);
        }
        modelAndView.setViewName("behind/videoList");

        List<Speaker> speakerList = speakerService.findAllSpeaker();
        modelAndView.addObject("speakerList",speakerList);
        List<Course> courseList = courseService.findAllCourse();
        modelAndView.addObject("courseList",courseList);

        //System.out.println(pageNum+"====="+pageSize);

//        for (int i = 0 ; i<allVideo.size();i++){
//            System.out.println(allVideo.get(i));
//        }
        return modelAndView ;
    }
    //跳转到添加video的界面
    @RequestMapping("addVideo")
    public String addVideo(HttpServletRequest request){
        //获取所有的主讲人
        List<Speaker> speakerList = speakerService.findAllSpeaker();

        //获取所有的课程
        List<Course> courseList = courseService.findAllCourse();
        request.setAttribute("speakerList",speakerList);
        request.setAttribute("courseList",courseList);
        return "behind/addVideo";
    }

    //跳转到修改video的界面

    @RequestMapping("edit")
    public ModelAndView edit (Video video){

        Integer id = video.getId();
        //System.out.println(id);
        //根据id查询相应的video信息
        Video video1 = videoService.findVideoByid(id);
        //System.out.println(video1);
        //获取所有的主讲人
        List<Speaker> speakerList = speakerService.findAllSpeaker();

        //获取所有的课程
        List<Course> courseList = courseService.findAllCourse();
        // System.out.println(speakerList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("behind/addVideo");
        modelAndView.addObject("video",video1);
        modelAndView.addObject("speakerList",speakerList);
        modelAndView.addObject("courseList",courseList);
        return modelAndView;
    }


    //添加完成后跳转到显示全部信息的页面
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Video video){
        if(null != video.getId() && !"".equals(video.getId())){
            //id存在进行修改操作
            Integer row = videoService.updateVideo(video);
            if(row==1){
                return "redirect:list";
            }else {
                return "error.jsp";
            }
        }else {
            //进行添加操作
            Integer row = videoService.addVideo(video);
            if(row==1){
                return "redirect:list";
            }else {
                return "error.jsp";
            }

        }
    }

    //删除video
    @RequestMapping("videoDel")
    @ResponseBody
    public String videoDel(Integer id){
        Integer  row = videoService.deleteVideo(id);
        String data = null;
        if (null != row && !"".equals(row)){
            data = "success";
        }
        return data;
    }

    //批量删除video
    @RequestMapping("delBatchVideos")
    public String delBatchVideos(Integer[] ids) {
        Integer row = null;
        for (int i = 0; i < ids.length; i++) {
            row = videoService.deleteVideo(ids[i]);
        }
        return  "redirect:list";
    }


}
