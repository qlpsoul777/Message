package controllers;

import models.Version;
import play.mvc.Controller;
import utils.FileUtils;

import java.io.File;

/**
 * Created by qlp on 2014/10/18.
 */
public class VersionUpload extends Controller {

    public static void inUpload(){
        render();
    }
    public static void upload(){
        File file = params.get("Filedata",File.class);
        String name = file.getName();
        String path = Application.saveFile(file);
        Version version = new Version();
        version.name = FileUtils.getBeforeName(name);
        version.afterName = FileUtils.getAfterName(name);
        version.path = path;
        version.size = file.length();
        version.type = FileUtils.getFileType(name);
        version.save();
    }
}
