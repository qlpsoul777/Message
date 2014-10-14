package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qlp on 2014/10/14.
 */
@Entity
@Table(name = "t_message_reply")
public class Reply extends Model {

    public String author;  //回复人

    @Lob
    public String content;  //回复内容

    public Date replyTime = new Date();  //回复时间

    @ManyToOne
    @JoinColumn(name = "notice_id")
    public Notice notice;  //通知

}
