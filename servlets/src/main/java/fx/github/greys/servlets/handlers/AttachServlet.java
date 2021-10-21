package fx.github.greys.servlets.handlers;

import fx.github.greys.servlets.utils.MxInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import static fx.github.greys.servlets.constants.Constants.*;

/**
 * client调用attach
 */
public class AttachServlet extends HttpServlet {
    private static final long serialVersionUID = -2631388239339092259L;

    private static Logger logger = LoggerFactory.getLogger(AttachServlet.class);

    /**
     * 成功attach 返回消息
     */
    private static final String ATTACH_SUCCESS_MESSAGE = "{code:%s,message:{filePath:%s,pid:%s}}";

    /**
     * 成功attach 返回消息
     */
    private static final String ATTACH_FAIL_MESSAGE = "{code:%s,message:{cause:%s}}";

    /**
     * 不存在attach的jar的message
     */
    private static final String NO_ATTACH_JAR_MESSAGE = "no jar file found to attach";
    /**
     * 未定义attach server的message
     */
    private static final String NO_ATTACH_SERVER_DEFINED = "no attach server defined";

    private static final String vmClassName = "com.sun.tools.attach.VirtualMachine";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(DEFAULT_CHARSET_ENCODING);
        resp.setContentType(DEFAULT_CONTENT_TYPE);
        //若用户是自己上传jar包到某目录下，由用户指定
        String filePath = req.getParameter("filePath");
        PrintWriter writer = resp.getWriter();
        if (null == filePath || EMPTY_STR.equals(filePath)) {
            filePath = MxInfoHelper.getFilePath();
        }
        if (null == filePath || EMPTY_STR.equals(filePath)) {
            writer.println(String.format(ATTACH_FAIL_MESSAGE, ERROR_CODE, NO_ATTACH_JAR_MESSAGE));
            return;
        }
        String serverHost = req.getParameter("serverHost");
        String serverPort = req.getParameter("serverPort");
        if (null == serverHost || EMPTY_STR.equals(serverHost) || null == serverPort || EMPTY_STR.equals(serverPort)) {
            writer.println(String.format(ATTACH_FAIL_MESSAGE, ERROR_CODE, NO_ATTACH_SERVER_DEFINED));
            return;
        }
        String pid = MxInfoHelper.getPid();
        MxInfoHelper.setServerHost(serverHost);
        MxInfoHelper.setServerPort(serverPort);
        //vm attach
        Object obj = null;
        Method detach = null;
        try {
            Class vmClazz = getClass().getClassLoader().loadClass(vmClassName);
            Method attach = vmClazz.getMethod("attach", String.class);
            detach = vmClazz.getMethod("detach");
            Method loadAgent = vmClazz.getMethod("loadAgent", String.class);
            obj = attach.invoke(null, pid);
            loadAgent.invoke(obj, filePath);
        } catch (Exception e) {
            logger.error("attach server occurs exception.", e);
            writer.println(String.format(ATTACH_FAIL_MESSAGE, ERROR_CODE, e.getMessage()));
            return;
        } finally {
            if (obj != null && detach != null) {
                try {
                    detach.invoke(obj);
                } catch (Exception e) {
                    logger.error("AttachServlet vm detach occurs exception.", e);
                }
            }
        }
        writer.println(String.format(ATTACH_SUCCESS_MESSAGE, SUCCESS_CODE, filePath, pid));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
