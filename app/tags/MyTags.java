package tags;

import controllers.Application;
import groovy.lang.Closure;
import play.templates.FastTags;
import play.templates.GroovyTemplate;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by qlp on 2014/10/17.
 */
public class MyTags extends FastTags {
    public static void _page(Map<?, ?> args, Closure body, PrintWriter out,
                             GroovyTemplate.ExecutableTemplate template, int fromLine){
        String formId = (String) args.get("formId");  //表单id
        String style = (String) args.get("style");  //分页样式
        int currentPage = (Integer)args.get("current");  //当前页
        int totalSize = (Integer)args.get("total");  //总页数
        if(totalSize>0){
            StringBuilder sb = new StringBuilder();
            sb.append("<ul>").append("\n");
            if("styleOne".equals(style)) {
                sb.append(styleOne(formId,currentPage,totalSize));
            }
            sb.append("</ul>");
            out.print(sb.toString());
        }
    }

    private static StringBuilder styleOne(String formId,int currentPage,int totalSize){
        StringBuilder sb = new StringBuilder();
        if(totalSize == 1){
            sb.append("<li class=\"disabled\">");
            sb.append("<a href=\"#\">").append(1).append("</a>");
            sb.append("</li>").append("\n");
        }else{
            if(currentPage == 1){
                sb.append("<li class=\"disabled\">");
                sb.append("<a href=\"#\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(formId,currentPage,totalSize));
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                        .append(currentPage + 1).append(");\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }else if (currentPage == totalSize) {
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                        .append(currentPage-1).append(");\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(formId,currentPage,totalSize));
                sb.append("<li class=\"disabled\">");
                sb.append("<a href=\"#\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }else{
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                        .append(currentPage-1).append(");\">").append("上一页").append("</a>");
                sb.append("</li>").append("\n");
                sb.append(styleOneLess(formId,currentPage,totalSize));
                sb.append("<li>");
                sb.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                        .append(currentPage + 1).append(");\">").append("下一页").append("</a>");
                sb.append("</li>").append("\n");
            }
        }
        sb.append("<li><input type=\"hidden\" id=\"").append(formId)
                .append("_page_id\" name=\"currentPage\" value=\"").append(currentPage)
                .append("\"></li>").append("\n");
        sb.append("<script type=\"text/javascript\">").append("\n");
        sb.append("function toPage(formId, page) {").append("\n");
        sb.append("var $form = $('#' + formId);").append("\n");
        sb.append("reg = /^[0-9]*$/;").append("\n");
        sb.append("if (reg.exec(page + \"\")) {").append("\n");
        sb.append("$(\"#\" + formId + \"_page_id\").val(page);").append("\n");
        sb.append("}").append("\n");
        sb.append("$form[0].submit();").append("\n");
        sb.append("}").append("\n");
        sb.append("</script>").append("\n");
        return sb;
    }

    private static StringBuilder styleOneLess(String formId,int currentPage,int totalSize){
        StringBuilder sbLess = new StringBuilder();
       //总页数大于等于1小于10
        if (totalSize <= 10) {
            for (int i = 1; i <= totalSize; i++) {
                if (i == currentPage) {
                    sbLess.append("<li class=\"active\">");
                    sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                            .append(i).append(");\">").append(i).append("</a>");
                    sbLess.append("</li>").append("\n");
                } else {
                    sbLess.append("<li>");
                    sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                            .append(i).append(");\">").append(i).append("</a>");
                    sbLess.append("</li>").append("\n");
                }
            }
        }
//总页数大于10页
        else {
            int onePageShow = Application.PAGE_SIZE; //一页显示的页数
            int half = onePageShow >> 1;
            int halfPageShow = half + 1;
            int havePages = totalSize - currentPage; //总页数减去当前页
            int stopPage = totalSize - onePageShow; //总页数减去一页显示的页数
            if (currentPage <= halfPageShow) {
                for (int i = 1; i <= onePageShow; i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            } else if (havePages >= 4) {
                for (int i = currentPage - 5; i < currentPage + 5; i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            } else {
                for (int i = stopPage; i <= totalSize; i++) {
                    if (i == currentPage + 1) {
                        sbLess.append("<li class=\"active\">");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    } else {
                        sbLess.append("<li>");
                        sbLess.append("<a href=\"javascript:toPage('").append(formId).append("', ")
                                .append(i).append(");\">").append(i).append("</a>");
                        sbLess.append("</li>").append("\n");
                    }
                }
            }
        }
        return sbLess;
    }

}
