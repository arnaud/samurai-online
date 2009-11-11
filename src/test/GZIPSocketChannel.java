package test;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class GZIPSocketChannel {

	private SocketChannel channel;
	private Deflater deflater;
	private Inflater inflater;
	private byte[] outBuffer;
	private byte[] inBuffer;
	ByteBuffer inByteBuffer;

	public GZIPSocketChannel(SocketChannel channel) {
		this.channel = channel;
		deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, false);
		inflater = new Inflater(false);
		outBuffer = new byte[1024];
		inBuffer = new byte[1024];
		inByteBuffer = ByteBuffer.allocate(1024);
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public int write(ByteBuffer buf) throws IOException {
		int length = buf.limit();
		if (length > 0) {
			byte[] array = new byte[length];
			buf.get(array, 0, length);
			write(array, 0, length);
		}
		return length;
	}

	public int write(byte[] array, int offset, int length) throws IOException {
		while (!deflater.needsInput()) {
			deflate();
		}
		deflater.setInput(array, offset, length);
		while (!deflater.needsInput()) {
			deflate();
		}
		return length;
	}

	public int read(byte[] buffer) throws IOException, DataFormatException {
		while (inflater.needsInput() && ! inflater.finished()) {
			inByteBuffer.clear();
			channel.read(inByteBuffer);
			inByteBuffer.flip();
			int length = inByteBuffer.limit();
			inByteBuffer.get(inBuffer, 0, length);
			inflater.setInput(inBuffer, 0, length);
		}
		if (inflater.finished() || inflater.needsDictionary())
			return -1;
		int rc = inflater.inflate(buffer);
		/*
		 System.err.println("read: "+inflater.getBytesRead()
		 +"written: "+inflater.getBytesWritten());
		 */
		return rc;
	}

	public void finish() throws IOException {
		if (!deflater.finished()) {
			deflater.finish();
			while (!deflater.finished()) {
				deflate();
			}
			/* System.err.println("read: "+deflater.getBytesRead()
			 +" written: "+deflater.getBytesWritten());
			 */
		}
	}

	protected void deflate() throws IOException {
		int len = deflater.deflate(outBuffer, 0, outBuffer.length);
		if (len > 0) {
			int offset = 0;
			ByteBuffer buffer = ByteBuffer.wrap(outBuffer, 0, len);
			while (offset != len) {
				int wc = channel.write(buffer);
				offset += wc;
			}
		}
	}
}