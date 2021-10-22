package fx.greys.fork.common.netty;

import fx.greys.fork.common.entity.GreysMessage;
import fx.greys.fork.common.exception.DecodeException;
import fx.greys.fork.common.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;

import static fx.greys.fork.common.constant.Constants.MAX_FRAME_LENGTH;

/**
 * 消息格式：
 * | magic_word | totalLength | headLength |   head | body |
 * |      5    |       4     |      4     | ip+port | body |
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder() {
        this(MAX_FRAME_LENGTH);
    }

    public MessageDecoder(int maxFrameLength) {
        super(MAX_FRAME_LENGTH, 5, 4, -9, 0);
    }

    private static Logger logger = LogUtil.getLogger();

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded;
        try {
            decoded = super.decode(ctx, in);
            if (decoded instanceof ByteBuf) {
                ByteBuf frame = (ByteBuf) decoded;
                try {
                    return decodeFrame(frame);
                } finally {
                    frame.release();
                }
            }
        } catch (Exception e) {
            logger.error("message decode exception.", e);
            throw new DecodeException(e);
        }
        return decoded;
    }

    public Object decodeFrame(ByteBuf frame) {
        byte[] magic = new byte[5];
        frame.readBytes(magic);
        int totalLength = frame.readInt();
        int headLength = frame.readInt();
        byte[] hostBytes = new byte[headLength - 8];
        frame.readBytes(hostBytes);
        String host = new String(hostBytes);
        int pid = frame.readInt();
        int bodyLength = totalLength - magic.length - 4 - headLength;
        byte[] bodyBytes = new byte[bodyLength];
        frame.readBytes(bodyBytes);
        String commandLine = new String(bodyBytes);
        GreysMessage message = new GreysMessage();
        message.setHost(host);
        message.setPid(pid);
        message.setCommandLine(commandLine);
        return message;
    }
}
