package arthur.backend;

import java.io.*;

public class IoUtils {

    private static final int BUFFER_SIZE = 8192;

    public static long copy(InputStream is, OutputStream os) {
        byte[] buf = new byte[BUFFER_SIZE];
        long total = 0;
        int len = 0;
        try {
            while (-1 != (len = is.read(buf))) {
                os.write(buf, 0, len);
                total += len;
            }
        } catch (IOException ioe) {
            throw new RuntimeException("error reading stream", ioe);
        }
        return total;
    }

    public static String string(InputStream is) {
      BufferedReader br = null;
		  StringBuilder sb = new StringBuilder();

		  String line;
		  try {
        br = new BufferedReader(new InputStreamReader(is));
			  while ((line = br.readLine()) != null) {
    		  sb.append(line + "\n");
			  }

		  } catch (IOException e) {
			  e.printStackTrace();
		  } finally {
			  if (br != null) {
				  try {
					  br.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
		  }

		  return sb.toString();
    }

    public static void move(String start, String end) {
      try {
        String exec = "mv " + start + " " + end;
        Process p = Runtime.getRuntime().exec(exec);
        p.waitFor();
      } catch (Exception e) {
        System.out.println("failed to move file " + start + " to " + end);
        e.printStackTrace();
      }
    }

}
