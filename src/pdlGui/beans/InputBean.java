package pdlGui.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
import com.insa.swim.orchestrator.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "inputBean")
@ViewScoped
public class InputBean implements Serializable {

    private static final long serialVersionUID = 9040359120893077422L;

    private Part part;
    private String statusMessage;
    private String statusUploadMessage;
    private String fileName;
    private String inputData;
    private List<String> inputTextList;
    private String textArea;
    private File fileToUpload = new File("D:\\upload\\scenarioIHM.xml");

    public List<String> uploadFile() throws IOException {

        // Extract file name from content-disposition header of file part
        //String fileName = getFileName(part);
        fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);

        String basePath = "D:" + File.separator + "upload" + File.separator;
        File outputFilePath = new File(basePath + fileName);

        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = part.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            statusMessage = "File upload successfull !!";
           // Application app = new Application();
            //app.start();
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage = "File upload failed !!";
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();

            }
        }
        return setInputText(outputFilePath);    // return to same page
    }

    public List<String> setInputText(File f) {
        inputTextList = new ArrayList();
        if (statusMessage.equals("File upload successfull !!")) {

            try {
                //File f = new File("D:\\upload\\"+fileName);
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                try {
                    String line = br.readLine();

                    while (line != null) {
                        inputTextList.add(line);
                        System.out.println(line);
                        line = br.readLine();
                    }

                    br.close();
                    fr.close();
                } catch (IOException exception) {
                    System.out.println("Erreur lors de la lecture : " + exception.getMessage());
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Le fichier n'a pas été trouvé");
            }
        }
        return inputTextList;
    }

 

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    public void writeFile() {

        PrintWriter pw = null;
        try {
            File f = new File("D:\\upload\\scenario.xml");
            pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            int i = 0;
            pw.println(textArea);
            pw.close();
            statusUploadMessage = "File upload successfull !!";
        } catch (IOException ex) {
            Logger.getLogger(InputBean.class.getName()).log(Level.SEVERE, null, ex);
            statusUploadMessage = "File upload failed !!";
        } finally {
            pw.close();
        }
    }

    public void upload(File f) throws IOException {
        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = part.getInputStream();
            outputStream = new FileOutputStream(f);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            //statusMessage = "File upload successfull !!";
        } catch (IOException e) {
            e.printStackTrace();
            // statusMessage = "File upload failed !!";
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();

            }
        }

    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
       public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusUploadMessage() {
        return statusUploadMessage;
    }

    public void setStatusUploadMessage(String statusUploadMessage) {
        this.statusUploadMessage = statusUploadMessage;
    }

    public List<String> getInputTextList() {
        return inputTextList;
    }

    public void setInputTextList(List<String> inputTextList) {
        this.inputTextList = inputTextList;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getTextArea() {
        return textArea;
    }

    public void setTextArea(String textArea) {
        this.textArea = textArea;
    }


    /* public void printIt(ActionEvent event) throws IOException {
     //uploadFile();
     //setInputData("hello");
     // setInputText();

     }*/
}
