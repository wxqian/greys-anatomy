package fx.github.greys.servlets.handlers;

import fx.github.greys.servlets.utils.MxInfoHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static fx.github.greys.servlets.constants.Constants.*;

/**
 * 测试servlet注册成功，连接有效
 */
public class PingServlet extends HttpServlet {

    private static final long serialVersionUID = 5333884309801135929L;

    private static final String RESP_MESSAGE = "{code:%s,message:{pid:%s}}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(DEFAULT_CHARSET_ENCODING);
        resp.setContentType(DEFAULT_CONTENT_TYPE);
        PrintWriter writer = resp.getWriter();
        writer.println(String.format(RESP_MESSAGE, SUCCESS_CODE, MxInfoHelper.getPid()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
