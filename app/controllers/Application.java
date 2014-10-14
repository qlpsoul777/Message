package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        Notice first = Notice.find("order by createTime desc").first();
        List<Notice> oldNotices = Notice.find("order by createTime desc").from(1).fetch(10);
        render(first,oldNotices);
    }

    public static void show(Long id){
        Notice notice = Notice.findById(id);
        List<Reply> replies = Reply.find("from Reply r where r.notice.id = ?1",id).fetch();
        render(notice,replies);
    }

    public static void add(){
        render();
    }

    public static void save(Notice notice){
        notice.save();
        index();
    }

}