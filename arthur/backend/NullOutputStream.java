package arthur.backend;

import java.io.*;

class NullOutputStream extends OutputStream {
  public void write(int b) throws IOException {
    // write nothing
  }
}
