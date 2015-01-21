package pdlGui.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "fileDownloadBean")

public class FileDownloadBean {

    private static final int DEFAULT_BUFFER_SIZE = 10240;
    private String textArea;
    private String statusMessage;
    private List<String> inputTextList;
    private File file;

    private String filePath = "d:\\upload\\Scenario.xml";
    private File fileToDownload = new File("D:\\upload\\download.xml");

    public void downLoad() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        file = new File(filePath);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        inputTextList = setInputText(file);
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment;filename=\""
                + file.getName() + "\"");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file),
                    DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(),
                    DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            input.close();
            output.close();
        }
        context.responseComplete();

    }

    public void printIt() {
         file = new File(filePath);

        inputTextList = setInputText(file);
    }

    public List<String> setInputText(File f) {
        inputTextList = new ArrayList();

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

        return inputTextList;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTextArea() {
        return textArea;
    }

    public void setTextArea(String textArea) {
        this.textArea = textArea;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<String> getInputTextList() {
        return inputTextList;
    }

    public void setInputTextList(List<String> inputTextList) {
        this.inputTextList = inputTextList;
    }
}
