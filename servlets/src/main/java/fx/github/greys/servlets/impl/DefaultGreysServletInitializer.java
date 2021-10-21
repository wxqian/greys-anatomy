package fx.github.greys.servlets.impl;

import fx.github.greys.servlets.handlers.AttachServlet;
import fx.github.greys.servlets.handlers.PingServlet;
import fx.github.greys.servlets.handlers.UploadServlet;
import fx.github.greys.servlets.utils.GreysServletInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import java.io.InputStream;
import java.util.Properties;

import static fx.github.greys.servlets.constants.Constants.EMPTY_STR;

/**
 * 默认GreysServletInitializer实现类
 */
public class DefaultGreysServletInitializer implements GreysServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(DefaultGreysServletInitializer.class);

    /**
     * 配置文件中servlet mapping属性名
     */
    private static final String UPLOAD_SERVLET_MAPPING = "upload.servlet.mapping";
    private static final String PING_SERVLET_MAPPING = "ping.servlet.mapping";
    private static final String ATTACH_SERVLET_MAPPING = "attach.servlet.mapping";
    /**
     * 外部配置servlet mapping配置文件地址
     */
    private static final String PROPERTY_FILE_NAME = "/greys/servlet.properties";
    /**
     * 默认servlet mapping
     */
    private static final String DEFAULT_UPLOAD_MAPPING = "/greys/upload";
    private static final String DEFAULT_PING_MAPPING = "/greys/ping";
    private static final String DEFAULT_ATTACH_MAPPING = "/greys/attach";
    /**
     * servlet name
     */
    private static final String UPLOAD_SERVLET_NAME = "greysUpload";
    private static final String PING_SERVLET_NAME = "greysPing";
    private static final String ATTACH_SERVLET_NAME = "greysATTACH";

    private static final int LOAD_ON_START_UP = 3;

    private static final Properties properties = new Properties();

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        try (InputStream is = getClass().getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties.load(is);
        } catch (Exception e) {
            logger.error("get resource occurs exception.", e);
        }
        //upload servlet
        Dynamic upload = sc.addServlet(UPLOAD_SERVLET_NAME, UploadServlet.class);
        upload.setLoadOnStartup(LOAD_ON_START_UP);
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(EMPTY_STR);
        upload.setMultipartConfig(multipartConfigElement);
        upload.addMapping((String) properties.getOrDefault(UPLOAD_SERVLET_MAPPING, DEFAULT_UPLOAD_MAPPING));
        //ping servlet
        Dynamic ping = sc.addServlet(PING_SERVLET_NAME, PingServlet.class);
        ping.setLoadOnStartup(LOAD_ON_START_UP);
        ping.addMapping((String) properties.getOrDefault(PING_SERVLET_MAPPING, DEFAULT_PING_MAPPING));
        //attach servlet
        Dynamic attach = sc.addServlet(ATTACH_SERVLET_NAME, AttachServlet.class);
        attach.setLoadOnStartup(LOAD_ON_START_UP);
        attach.addMapping((String) properties.getOrDefault(ATTACH_SERVLET_MAPPING, DEFAULT_ATTACH_MAPPING));
    }
}
