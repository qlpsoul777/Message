package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by qlp on 2014/10/18.
 */
@Entity
@Table(name = "t_message_version")
public class Version extends Model {

    public String name;  //附件名称
    public String path;  //保存路径
    public String afterName;  //后缀名
    public String type;  //附件类型
    public long size;  //附件大小
    public Date createTime = new Date();  //创建时间
}
