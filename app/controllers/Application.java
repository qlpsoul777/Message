package controllers;

import org.apache.commons.lang.StringUtils;
import play.cache.Cache;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    /**
     * show all notices
     */
    public static void index() {
        Notice first = Notice.find("order by createTime desc").first();
        List<Notice> oldNotices = Notice.find("order by createTime desc").from(1).fetch(10);
        render(first,oldNotices);
    }

    /**
     * show notice and reply
     * @param id
     */
    public static void show(Long id){
        Notice notice = Notice.findById(id);
        List<Reply> replies = Reply.find("from Reply r where r.notice.id = ?1", id).fetch();
        String randomID = Codec.UUID();
        render(notice,replies,randomID);
    }

    /**
     * forward add.html
     */
    public static void add(){
        Notice notice = new Notice();
        render(notice);
    }

    /**
     * save new notice or save edit notice
     * @param noticeId
     * @param subTitle
     * @param author
     * @param content
     */
    public static void save(Long noticeId,String subTitle, String author, String content){
        Notice notice;
        if(noticeId == null){
            notice = new Notice();
        }else{
            notice = Notice.findById(noticeId);
        }
        notice.subTitle = subTitle;
        notice.author = author;
        notice.content = content;
        notice.save();
        index();
    }

    /**
     * delete notice
     * @param id
     */
    public static void delete(Long id){
        Notice notice = Notice.findById(id);
        notice.delete();
        index();
    }

    /**
     * forward add.html
     * @param id
     */
    public static void edit(Long id){
        Notice notice = Notice.findById(id);
        renderTemplate("/Application/add.html", notice);
    }

    /**
     * produce captcha image
     * @param id
     */
    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }

    /**
     * save reply
     * @param id
     */
    public static void reply(Long id){

    }

}