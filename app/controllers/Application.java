package controllers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PatternLayout;
import play.Play;
import play.cache.Cache;
import play.libs.Codec;
import play.libs.Files;
import play.libs.Images;
import play.mvc.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;

public class Application extends Controller {
    public static final int PAGE_SIZE = 5;  //每页显示条数

    /**
     * 总页数
     * @return
     */
    public static int totalPage(){
        int total;
        int totalSize = Notice.findAll().size();
        if((totalSize % PAGE_SIZE) == 0){
            total = totalSize/PAGE_SIZE;
        }else{
            total = (int)(totalSize/PAGE_SIZE) +1;
        }
        return total;
    }

    /**
     * show all notices
     */
    public static void index(int currentPage) {
        if(currentPage == 0){
            currentPage = 1;
        }
        Notice first = Notice.find("order by createTime desc").first();
        int start = (currentPage-1)*PAGE_SIZE;
        List<Notice> oldNotices = Notice.find("order by createTime desc").from(start).fetch(PAGE_SIZE);
        int totalSize = totalPage();
        render(first,oldNotices,currentPage,totalSize);
    }

    /**
     * show notice and reply
     * @param id
     */
    public static void show(Long id){
        Notice notice = Notice.findById(id);
        List<Reply> replies = Reply.find("from Reply r where r.notice.id = ?1", id).fetch();
        String randomID = Codec.UUID();
        String path = Play.applicationPath.getAbsolutePath();
        if(StringUtils.contains(path,'\\')){
            path = StringUtils.replaceChars(path,'\\','/').substring(0,path.length()-1);
        }
        render(notice, replies, randomID, path);
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
    public static void save(Long noticeId,String subTitle, String author, String content,File image){
        Notice notice;
        if(noticeId == null){
            notice = new Notice();
        }else{
            notice = Notice.findById(noticeId);
        }
        String imgPath = saveFile(image);
        notice.imgPath = imgPath;
        notice.subTitle = subTitle;
        notice.author = author;
        notice.content = content;
        notice.save();
        index(0);
    }

    /**
     * save file
     * @param image
     * @return
     */
    private static String saveFile(File image) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        double random = (Math.random()*100000);
        int rand = (int) Math.floor(random);
        String name = image.getName();
        String suffix = name.substring(name.indexOf(".")+1);
        String names = sdf.format(new Date())+rand+"."+suffix;
        String picPath = "data/images/" + names;
        File file = new File("data/images");
        if(!file.exists()){
            file.mkdirs();
        }
        String realPath = Play.ctxPath + picPath;
        File newfile = new File(realPath);
        Files.copy(image, newfile);
        return picPath;
    }

    /**
     * download file
     * @param id
     */
    public static void download(Long id){
        Notice notice = Notice.findById(id);
        String imgPath = notice.imgPath;
        File file = new File(imgPath);
        if(file.exists()){
            renderBinary(file,file.getName());
        }
    }

    /**
     * delete notice
     * @param id
     */
    public static void delete(Long id){
        Notice notice = Notice.findById(id);
        notice.delete();
        index(0);
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
    public static void reply(Long id,String author,String content,String code,String randomID){
        Notice notice = Notice.findById(id);
        validation.equals(
                code, Cache.get(randomID)
        ).message("Invalid code. Please type it again");
        if(!validation.hasErrors()){
            Reply reply = new Reply();
            reply.author = author;
            reply.content = content;
            reply.notice = notice;
            reply.save();
            flash.success("Thanks for posting %s", author);
        }
        Cache.delete(randomID);
        show(id);
    }

    public static void previousPage(int currentPage){
        if(currentPage >1){
            currentPage -= 1;
        }
        index(currentPage);
    }

    public static void nextPage(int currentPage){
        if(currentPage != totalPage()){
            currentPage += 1;
        }
        index(currentPage);
    }

}