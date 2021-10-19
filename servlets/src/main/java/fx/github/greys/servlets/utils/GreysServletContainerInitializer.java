package fx.github.greys.servlets.utils;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 */
@HandlesTypes(GreysServletInitializer.class)
public class GreysServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        List<GreysServletInitializer> initializers = new LinkedList<>();

        if (set != null) {
            for (Class<?> clazz : set) {
                // Be defensive: Some servlet containers provide us with invalid classes,
                // no matter what @HandlesTypes says...
                if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) &&
                        GreysServletInitializer.class.isAssignableFrom(clazz)) {
                    try {
                        initializers.add((GreysServletInitializer) clazz.newInstance());
                    } catch (Throwable ex) {
                        throw new ServletException("Failed to instantiate GreysServletInitializer class", ex);
                    }
                }
            }
        }

        if (initializers.isEmpty()) {
            servletContext.log("No GreysServletInitializer types detected on classpath");
            return;
        }

        servletContext.log(initializers.size() + "GreysServletInitializer detected on classpath");
        AnnotationAwareOrderComparator.sort(initializers);
        for (GreysServletInitializer initializer : initializers) {
            initializer.onStartup(servletContext);
        }
    }
}
