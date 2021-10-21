package fx.github.greys.servlets.handlers;

import fx.github.greys.servlets.utils.MxInfoHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static fx.github.greys.servlets.constants.Constants.*;

/**
 * 上传agent的jar
 */
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 3465348534147352547L;

    /**
     * 上传成功响应消息
     */
    private static final String UPLOAD_SUCCESS_MESSAGE = "{code:%s,message:{filePath:%s}}";
    /**
     * 上传失败响应消息
     */
    private static final String UPLOAD_ERROR_MESSAGE = "{code:%s,message:{cause:%s}}";

    private static final String DEFAULT_UPLOAD_PATH = "/WEB-INF/greys/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(DEFAULT_CHARSET_ENCODING);
        resp.setContentType(DEFAULT_CONTENT_TYPE);
        String filePath = req.getServletContext().getRealPath(DEFAULT_UPLOAD_PATH);
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        PrintWriter writer = resp.getWriter();
        try {
            Part part = req.getPart("file");
            String header = part.getHeader("content-disposition");
            String fileName = getFileName(header);
            filePath = filePath + File.separator + fileName;
            part.write(filePath);
            MxInfoHelper.setFilePath(filePath);
        } catch (Exception e) {
            writer.println(String.format(UPLOAD_ERROR_MESSAGE, ERROR_CODE, e.getMessage()));
            return;
        }
        writer.println(String.format(UPLOAD_SUCCESS_MESSAGE, SUCCESS_CODE, filePath));
    }

    private String getFileName(String header) {
        /**
         * String[] tempArr1 = header.split(";");代码执行完之后，在不同的浏览器下，tempArr1数组里面的内容稍有区别
         * 火狐或者google浏览器下：tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
         * IE浏览器下：tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
         */
        String[] tempArr1 = header.split(";");
        /**
         *火狐或者google浏览器下：tempArr2={filename,"snmp4j--api.zip"}
         *IE浏览器下：tempArr2={filename,"E:\snmp4j--api.zip"}
         */
        String[] tempArr2 = tempArr1[2].split("=");
        //获取文件名，兼容各种浏览器的写法
        return tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
    }
}
