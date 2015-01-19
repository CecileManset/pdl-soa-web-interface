package pdlGui.beans;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;


import pdlGui.entities.Fichier;

@ManagedBean
@RequestScoped
public class UploadBean implements Serializable {
    private static final long serialVersionUID = 1L;

     
    private Fichier           fichier;
    private UploadedFile uploadedFile;

    // Initialisation du bean fichier
    public UploadBean() {
        fichier = new Fichier();
    }

    public void envoyer() throws IOException {
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        byte[] contenuFichier = fichier.getContenu().getBytes();

        /*
         * Effectuer ici l'enregistrement du contenu du fichier sur le disque,
         * ou dans la BDD (accompagné du type du contenu, éventuellement), ou
         * tout autre traitement souhaité...
         */
     // Prepare filename prefix and suffix for an unique filename in upload folder.
        String prefix = FilenameUtils.getBaseName(fichier.getContenu().getName());
        String suffix = FilenameUtils.getExtension(fichier.getContenu().getName());

        // Prepare file and outputstream.
        File file = null;
        OutputStream output = null;
        
        try {
            // Create file with unique name in upload folder and write to it.
            file = File.createTempFile(prefix + "_", "." + suffix, new File("d:/upload"));
            output = new FileOutputStream(file);
            IOUtils.copy(fichier.getContenu().getInputStream(), output);
            nomFichier = file.getName();

            // Show succes message.
            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
                FacesMessage.SEVERITY_INFO, "File upload succeed!", null));
        } catch (IOException e) {
            // Cleanup.
            if (file != null) file.delete();

            // Show error message.
            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "File upload failed with I/O error.", null));

            // Always log stacktraces (with a real logger).
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(output);
        }
        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Fichier '%s', de taille '%s' et de type '%s' envoyé avec succès !",
                        nomFichier, tailleFichier, typeFichier ) ) );
    }
    
    /**
     * Download file.
     */
    public void download() throws IOException
    {
       File file = new File("D:\\upload\\sdf.pdf");
       InputStream fis = new FileInputStream(file);
       byte[] buf = new byte[1024];
       int offset = 0;
       int numRead = 0;
       while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
       {
         offset += numRead;
       }
       fis.close();
       HttpServletResponse response =
          (HttpServletResponse) FacesContext.getCurrentInstance()
              .getExternalContext().getResponse();
      
       response.setHeader("Content-Type", "text/plain");
      response.setHeader("Content-Disposition", "attachment;filename=sdf.pdf");
      response.getOutputStream().write(buf);
      response.getOutputStream().flush();
      response.getOutputStream().close();
      FacesContext.getCurrentInstance().responseComplete();
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier( Fichier fichier ) {
        this.fichier = fichier;
    }
    
   
}
