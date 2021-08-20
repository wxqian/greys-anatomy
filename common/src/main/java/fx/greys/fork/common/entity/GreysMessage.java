package fx.greys.fork.common.entity;

import java.io.Serializable;

public class GreysMessage implements Serializable {
    private static final long serialVersionUID = -2227151483758581694L;

    private String host;

    private int port;

    private String commandLine;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
}
