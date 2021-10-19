package fx.github.greys.servlets.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 适用于servlet 3.0 ,spi 加载动态servlet配置
 */
public interface GreysServletInitializer {

    void onStartup(ServletContext sc) throws ServletException;
}
