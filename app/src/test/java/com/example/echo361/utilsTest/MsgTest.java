package com.example.echo361.utilsTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.echo361.LayoutActivity.Msg;

public class MsgTest {

    private Msg msg;

    @Before
    public void setUp() {
        msg = new Msg("Hello!", Msg.TYPE_SEND, "SenderID", "ReceiverID");
    }

    @Test
    public void testConstructor() {
        assertNotNull(msg);
    }

    @Test
    public void testGetContent() {
        assertEquals("Hello!", msg.getContent());
    }

    @Test
    public void testGetType() {
        assertEquals(Msg.TYPE_SEND, msg.getType());
    }

    @Test
    public void testGetSenderId() {
        assertEquals("SenderID", msg.getSenderId());
    }

    @Test
    public void testGetReceiverId() {
        assertEquals("ReceiverID", msg.getReceiverId());
    }
}
