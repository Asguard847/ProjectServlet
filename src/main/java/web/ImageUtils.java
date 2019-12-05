package web;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtils {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    public static void saveImage(HttpServletRequest request, int driverId) {
        OutputStream out = null;
        InputStream filecontent = null;
        try {
            Part filePart = request.getPart("driverImage");
            if(filePart.getSize() == 0){
                return;
            }
            String rootDirectory = request.getSession().getServletContext().getRealPath("/");
            String path = "\\resources\\images\\" + driverId + ".png";
            File file = new File(rootDirectory + path);
            out = new FileOutputStream(file);
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            LOG.info("Picture for id "+ driverId + " persisted.");

        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (ServletException e) {
            LOG.error(e.getMessage());
        }finally{
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }if(filecontent!=null){
                try {
                    filecontent.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
    }

    public static void deleteImage(HttpServletRequest request, int id) {

        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDirectory + "\\resources\\images\\" + id + ".png");

        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
