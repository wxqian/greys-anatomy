package fx.github.greys.servlets.utils;

import java.lang.management.ManagementFactory;

/**
 * 应用管理信息类
 */
public class MxInfoHelper {

    /**
     * 是否初始化
     */
    private static boolean initialized = false;

    /**
     * 当前进程pid
     */
    private static String pid;

    /**
     * attach jar 路径
     */
    private static String filePath;

    /**
     * attach server host
     */
    private static String serverHost;

    /**
     * attach server port
     */
    private static int serverPort;

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getPid() {
        if (!initialized) {
            synchronized (MxInfoHelper.class) {
                if (!initialized) {
                    String name = ManagementFactory.getRuntimeMXBean().getName();
                    pid = name.substring(0, name.indexOf("@"));
                    initialized = true;
                }
            }
        }
        return pid;
    }

    public static void setFilePath(String filePath) {
        MxInfoHelper.filePath = filePath;
    }

    public static String getFilePath() {
        return MxInfoHelper.filePath;
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String serverHost) {
        MxInfoHelper.serverHost = serverHost;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(String serverPort) {
        MxInfoHelper.serverPort = Integer.parseInt(serverPort);
    }
}
