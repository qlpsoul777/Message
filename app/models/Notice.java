package models;

import org.hibernate.annotations.GenericGenerator;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qlp on 2014/10/14.
 */
@Entity
@Table(name = "t_message_notice")
public class Notice extends Model{

    public String subTitle; //标题

    public String author;  //创建人

    @Lob
    public String content;  //内容

    public Date createTime = new Date();  //创建时间

    public String imgPath;  //附件地址

}
