package fx.greys.fork.common.netty;

import fx.greys.fork.common.entity.GreysMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

import static fx.greys.fork.common.constant.Constants.MAGIC_WORD;

public class MessageEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof GreysMessage) {

            GreysMessage message = (GreysMessage) o;
            byteBuf.writeBytes(MAGIC_WORD.getBytes());
            int totalLength = 9;
            int headLength = 8;
            byte[] hostBytes = message.getHost().getBytes();
            byte[] bodyBytes = message.getCommandLine().getBytes(StandardCharsets.UTF_8);
            headLength += hostBytes.length;
            totalLength += headLength;
            totalLength += bodyBytes.length;
            byteBuf.writeInt(totalLength);
            byteBuf.writeInt(headLength);
            byteBuf.writeBytes(hostBytes);
            byteBuf.writeInt(message.getPid());
            byteBuf.writeBytes(bodyBytes);
        }
    }
}
