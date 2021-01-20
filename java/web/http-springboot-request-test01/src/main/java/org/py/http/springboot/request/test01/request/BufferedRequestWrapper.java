package org.py.http.springboot.request.test01.request;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer;

    /**
     * Default constructor.
     *
     * @param request {@link HttpServletRequest}
     * @throws IOException error
     */
    public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        InputStream is = request.getInputStream();
        if (null != is) {
            buffer = IOUtils.toByteArray(is);
        } else {
            buffer = null;
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new BufferedServletInputStream(new ByteArrayInputStream(buffer));
    }

    public ByteBuffer getBuffer() {
        if (null != buffer) {
            return ByteBuffer.wrap(Arrays.copyOf(buffer, buffer.length));
        }
        return null;
    }
}
