package io.luna.net.codec.game;

import io.luna.net.codec.ByteMessage;
import io.luna.net.codec.IsaacCipher;
import io.luna.net.codec.MessageType;
import io.luna.net.msg.GameMessage;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A test that ensures the {@link GameMessageEncoder} is functioning correctly.
 *
 * @author lare96 <http://github.org/lare96>
 * @author Graham
 */
public final class GameMessageEncoderTest {

    /**
     * Test encoding game packets.
     */
    @Test
    public void testEncode() throws Exception {
        var isaac = new IsaacCipher(new int[]{0, 0, 0, 0});
        var encoder = new GameMessageEncoder(isaac);

        byte[] payload = "test".getBytes();
        var buffer = Unpooled.buffer();

        // fixed length messages
        var msg = ByteMessage.message(54, MessageType.FIXED);
        msg.putBytes(payload);
        encoder.encode(null, new GameMessage(msg.getOpcode(), msg.getType(), msg), buffer);

        assertEquals(41, buffer.readUnsignedByte());
        assertEquals('t', buffer.readByte());
        assertEquals('e', buffer.readByte());
        assertEquals('s', buffer.readByte());
        assertEquals('t', buffer.readByte());

        buffer.clear();

        // variable length messages
        msg = ByteMessage.message(54, MessageType.VAR);
        msg.putBytes(payload);
        encoder.encode(null, new GameMessage(msg.getOpcode(), msg.getType(), msg), buffer);

        assertEquals(195, buffer.readUnsignedByte());
        assertEquals(4, buffer.readByte());
        assertEquals('t', buffer.readByte());
        assertEquals('e', buffer.readByte());
        assertEquals('s', buffer.readByte());
        assertEquals('t', buffer.readByte());

        buffer.clear();

        // variable short length messages
        msg = ByteMessage.message(54, MessageType.VAR_SHORT);
        msg.putBytes(payload);
        encoder.encode(null, new GameMessage(msg.getOpcode(), msg.getType(), msg), buffer);

        assertEquals(88, buffer.readUnsignedByte());
        assertEquals(4, buffer.readUnsignedShort());
        assertEquals('t', buffer.readByte());
        assertEquals('e', buffer.readByte());
        assertEquals('s', buffer.readByte());
        assertEquals('t', buffer.readByte());
    }
}
