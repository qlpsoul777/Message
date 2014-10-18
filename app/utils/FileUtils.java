package utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**上传附件辅助类
 * Created by qlp on 2014/7/27.
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getAfterName(String fileName){
        if(StringUtils.isNotBlank(fileName)){
            int dotLocation = StringUtils.lastIndexOf(fileName,'.');
            if((dotLocation > -1 ) && dotLocation < fileName.length()-1){
                return fileName.substring(dotLocation+1);
            }else{
                logger.debug("传入文件名不合法");
            }
        }else{
            logger.debug("传入文件名为blank");
        }
        return "";
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    public static String getBeforeName(String fileName){
        if(StringUtils.isNotBlank(fileName)){
            int dotLocation = StringUtils.lastIndexOf(fileName,'.');
            if((dotLocation > -1 ) && dotLocation < fileName.length()-1){
                return fileName.substring(0,dotLocation);
            }else{
                logger.debug("传入文件名不合法");
            }
        }else{
            logger.debug("传入文件名为blank");
        }
        return "";
    }

    /**
     * 获取文件的类型
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName){
        String type = getAfterName(fileName);
        if(StringUtils.equalsIgnoreCase("JPG",type) || StringUtils.equalsIgnoreCase("JPEG",type)
                || StringUtils.equalsIgnoreCase("PNG",type) || StringUtils.equalsIgnoreCase("GIF",type)){
            type = ParameterUtils.IMG;
        }else if(StringUtils.equalsIgnoreCase("DOC",type) || StringUtils.equalsIgnoreCase("XLS",type)
                || StringUtils.equalsIgnoreCase("PPT",type)){
            type = ParameterUtils.OFFICE;
        }else if(StringUtils.equalsIgnoreCase("PDF",type)){
            type = ParameterUtils.PDF;
        }else if(StringUtils.equalsIgnoreCase("ZIP",type) || StringUtils.equalsIgnoreCase("RAR",type)){
            type = ParameterUtils.ZIP;
        }else if(StringUtils.equalsIgnoreCase("MP3",type) || StringUtils.equalsIgnoreCase("WAV",type) ||
                StringUtils.equalsIgnoreCase("MID",type)){
            type = ParameterUtils.SING;
        }else if(StringUtils.equalsIgnoreCase("MP4",type) || StringUtils.equalsIgnoreCase("AVI",type) ||
                StringUtils.equalsIgnoreCase("RMVB",type) || StringUtils.equalsIgnoreCase("3GP",type)){
            type = ParameterUtils.FILM;
        }else{
            type = ParameterUtils.OTHER;
        }
        return type;
    }
}
