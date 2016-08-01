package cn.ut.library.logcat;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import cn.ut.library.bean.MemoryState;
import cn.ut.library.logcat.bean.AdvancedLogInfo;
import cn.ut.library.bean.LogLevel;
import cn.ut.library.logcat.getter.MemoryGetter;
import cn.ut.library.logcat.getter.StackTraceGetter;
import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.logcat.getter.ThreadGetter;
import cn.ut.library.logcat.logger.LoggerBuilder;
import cn.ut.library.logcat.printer.Printer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by chenhang on 2016/6/14.
 */
public class UTLogTest {

    @Mock
    private Context context;
    @Mock
    private Printer mPrinter;
    @Mock
    private LogcatWriter mLogcatWriter;
    @Mock
    private MemoryGetter mMemoryGetter;
    @Mock
    private ThreadGetter mThreadGetter;
    @Mock
    private StackTraceGetter mStackTraceGetter;
    @Mock
    private StringFormatter mXmlFormatter;
    @Mock
    private StringFormatter mJsonFormatter;
    @Captor
    private ArgumentCaptor<AdvancedLogInfo> mArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks();

        LoggerBuilder builder = new LoggerBuilder(context);
        builder.setPrinter(mPrinter);
        builder.setLogWriter(mLogcatWriter);
        builder.setMemoryGetter(mMemoryGetter);
        builder.setThreadGetter(mThreadGetter);
        builder.setStackTraceGetter(mStackTraceGetter);
        builder.setXmlFormatter(mXmlFormatter);
        builder.setJsonFormatter(mJsonFormatter);
        builder.setConfiguration(new LogConfiguration());
        UTLog.setup(builder);
        UTLog.setLogLevel(LogLevel.VERBOSE);
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(mMemoryGetter.getMemory()).thenReturn(new MemoryState());
        when(mThreadGetter.getThreadInfo()).thenReturn(Thread.currentThread().getName());
        when(mStackTraceGetter.getStackTrace(isA(Class.class))).thenReturn(new StackTraceElement[0]);
        try {
            doAnswer(new Answer() {
                @Override
                public String answer(InvocationOnMock invocation) throws Throwable {
                    return "json-formatted";
                }
            }).when(mJsonFormatter).format(anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            doAnswer(new Answer() {
                @Override
                public String answer(InvocationOnMock invocation) throws Throwable {
                    return "xml-formatted";
                }
            }).when(mXmlFormatter).format(anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        UTLog.tearDown();
    }

    @Test
    public void testSetDebugEnabled() throws Exception {

        UTLog.setDebugEnabled(true);
        UTLog.d("tag", "message0");
        UTLog.d("tag", "message1");
        verify(mPrinter, times(2)).print(mArgumentCaptor.capture());
        assertEquals("message1", mArgumentCaptor.getValue().getMessage());

        UTLog.setDebugEnabled(false);
        UTLog.d("tag", "message2");
        UTLog.d("tag", "message3");
        verify(mPrinter, times(2)).print(isA(AdvancedLogInfo.class));

        verify(mLogcatWriter, times(2)).writeLog(isA(AdvancedLogInfo.class));
    }

    @Test
    public void testIsDebugEnabled() throws Exception {
        UTLog.setDebugEnabled(true);
        assertTrue(UTLog.isDebugEnabled());

        UTLog.setDebugEnabled(false);
        assertFalse(UTLog.isDebugEnabled());

        UTLog.setDebugEnabled(false);
        UTLog.setDebugEnabled(true);
        UTLog.setDebugEnabled(false);
        assertFalse(UTLog.isDebugEnabled());
    }

    @Test
    public void testSetLogLevel() throws Exception {
        UTLog.setLogLevel(LogLevel.INFO);
        UTLog.v("v", "message");
        verify(mPrinter, times(0)).print(isA(AdvancedLogInfo.class));
        UTLog.d("d", "message");
        verify(mPrinter, times(0)).print(isA(AdvancedLogInfo.class));
        UTLog.i("i", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals("i", mArgumentCaptor.getValue().getTag());
        UTLog.w("w", "message");
        verify(mPrinter, times(2)).print(mArgumentCaptor.capture());
        assertEquals("w", mArgumentCaptor.getValue().getTag());
        UTLog.e("e", "message");
        verify(mPrinter, times(3)).print(mArgumentCaptor.capture());
        assertEquals("e", mArgumentCaptor.getValue().getTag());
        UTLog.wtf("wtf", "message");
        verify(mPrinter, times(4)).print(mArgumentCaptor.capture());
        assertEquals("wtf", mArgumentCaptor.getValue().getTag());
    }

    @Test
    public void testSetTagPrefix() throws Exception {
        UTLog.setTagPrefix("Prefix");
        UTLog.d("tag", "message");
        verify(mPrinter).print(mArgumentCaptor.capture());
        assertTrue(mArgumentCaptor.getValue().getTag().startsWith("Prefix"));

        UTLog.setTagPrefix("");
        UTLog.d("tag", "message");
        verify(mPrinter, times(2)).print(mArgumentCaptor.capture());
        assertEquals("tag", mArgumentCaptor.getValue().getTag());
    }

    @Test
    public void testWatchStack() throws Exception {
        UTLog.watchStack().d("tag", "message");
        verify(mStackTraceGetter, times(1)).getStackTrace(isA(Class.class));
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertNotNull(mArgumentCaptor.getValue().getStackTrace());
    }

    @Test
    public void testTitle() throws Exception {
        UTLog.title("title").d("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals("title", mArgumentCaptor.getValue().getTitle());
    }

    @Test
    public void testWatchThread() throws Exception {
        UTLog.watchThread().d("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertNotNull(mArgumentCaptor.getValue().getThreadName());
    }

    @Test
    public void testWatchMemory() throws Exception {
        UTLog.watchMemory().d("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertNotNull(mArgumentCaptor.getValue().getMemoryInfo());
    }

    @Test
    public void testV() throws Exception {
        UTLog.v("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.VERBOSE, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testD() throws Exception {
        UTLog.d("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.DEBUG, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testI() throws Exception {
        UTLog.i("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.INFO, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testW() throws Exception {
        UTLog.w("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.WARN, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testE() throws Exception {
        UTLog.e("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.ERROR, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testE1() throws Exception {
        UTLog.e("tag", "message", new Throwable("test"));
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.ERROR, mArgumentCaptor.getValue().getLevel());
        assertNotNull(mArgumentCaptor.getValue().getThrowable());
    }

    @Test
    public void testWtf() throws Exception {
        UTLog.wtf("tag", "message");
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals(LogLevel.ASSERT, mArgumentCaptor.getValue().getLevel());
    }

    @Test
    public void testJson() throws Exception {
        UTLog.json("tag", "{\"status\":\"success\"}");
        verify(mJsonFormatter, times(1)).format(eq("{\"status\":\"success\"}"));
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals("json-formatted", mArgumentCaptor.getValue().getMessage());
    }

    @Test
    public void testXml() throws Exception {
        UTLog.xml("tag", "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><note><to>George</to><from>John</from><heading>Reminder</heading><body>Don't forget the meeting!</body></note>");
        verify(mXmlFormatter, times(1)).format(eq("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><note><to>George</to><from>John</from><heading>Reminder</heading><body>Don't forget the meeting!</body></note>"));
        verify(mPrinter, times(1)).print(mArgumentCaptor.capture());
        assertEquals("xml-formatted", mArgumentCaptor.getValue().getMessage());
    }

    @Test
    public void testAll() {
        UTLog.title("title").watchMemory().watchStack().watchThread().d("tag", "message");
        verify(mPrinter).print(mArgumentCaptor.capture());
        AdvancedLogInfo log = mArgumentCaptor.getValue();
        assertEquals("title", log.getTitle());
        assertNotNull(log.getMemoryInfo());
        assertNotNull(log.getStackTrace());
        assertNotNull(log.getThreadName());

        UTLog.title(null).d("tag", "message");
        UTLog.d(null, null);
        UTLog.d("", null);
        UTLog.d(null, "");
        UTLog.d("", "");
    }
}