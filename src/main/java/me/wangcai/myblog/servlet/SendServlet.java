package me.wangcai.myblog.servlet;

import me.wangcai.myblog.manager.WordManager;
import me.wangcai.myblog.config.FileConfig;
import me.wangcai.myblog.model.WordBean;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@WebServlet(name = "send word",urlPatterns = "/sendword")
public class SendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, UnsupportedEncodingException {
        if(req.getSession().getAttribute("user") == null){
            return;
        }
        Boolean isMultipart= ServletFileUpload.isMultipartContent(req);
        if(!isMultipart) {
            return;
        }
        String imgUrl = "default.jpg";
        
        //创建FileItemFactory对象
        FileItemFactory factory=new DiskFileItemFactory();
        //创建文件上传的处理器
        ServletFileUpload upload=new ServletFileUpload(factory);
        //解析请求
        Map<String, List<FileItem>> map = upload.parseParameterMap(req);
        String title = map.get("title").get(0).getString("UTF-8");
        String content = map.get("content").get(0).getString("UTF-8");
        List<FileItem> items=map.get("image");
        if(items != null && items.size() == 1){
            FileItem item = items.get(0);
            String suffix = item.getName().split("\\.")[1];
            if(!suffix.equalsIgnoreCase("png")  && !suffix.equalsIgnoreCase("jpeg") && !suffix.equalsIgnoreCase("jpg") && !suffix.equalsIgnoreCase("gif")){
                return;
            }
            if(item.getSize() > 1024 * 1024 * 5){
                return;
            }
            String filename = System.currentTimeMillis() + "." + suffix;
            imgUrl = filename;
            try {
                item.write(new File(FileConfig.getPath() + "/images/",filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            WordManager.getWordManager(). add(new WordBean(title,content,imgUrl,System.currentTimeMillis()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
