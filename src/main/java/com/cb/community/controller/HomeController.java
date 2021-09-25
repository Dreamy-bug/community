package com.cb.community.controller;

import com.cb.community.entity.DiscussPost;
import com.cb.community.entity.Page;
import com.cb.community.entity.User;
import com.cb.community.service.DiscussPostService;
import com.cb.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list != null){
            for(DiscussPost post : list){
                HashMap<String, Object> hashMap = new HashMap<>();
                User userById = userService.findUserById(post.getUserId());
                hashMap.put("user",userById);
                hashMap.put("post",post);
                discussPosts.add(hashMap);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }
}
