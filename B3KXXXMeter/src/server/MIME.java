package server;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *
 * @author oldRat
 */
public class MIME {
    
public static final String
            MIME_PLAINTEXT = "text/plain",
            MIME_HTML = "text/html",
            MIME_DEFAULT_BINARY = "application/octet-stream";

public static Hashtable theMimeTypes = new Hashtable();

static
{//TO_Ask
StringTokenizer st = new StringTokenizer(
        "htm		text/html "+
        "html		text/html "+
    //    "txt		text/plain "+
    //    "asc		text/plain "+
        "gif		image/gif "+
        "jpg		image/jpeg "+
        "jpeg		image/jpeg "+
        "png		image/png "+
   //     "mp3		audio/mpeg "+
   //     "m3u		audio/mpeg-url " +
   //     "pdf		application/pdf "+
   //     "doc		application/msword "+
   //     "ogg		application/x-ogg "+
   //     "zip		application/octet-stream "+
   //     "exe		application/octet-stream "+
        "jar            application/x-jar "+
        "class		application/octet-stream " );

while ( st.hasMoreTokens())
        theMimeTypes.put( st.nextToken(), st.nextToken());
}


}
