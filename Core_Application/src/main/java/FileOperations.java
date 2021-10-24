import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileOperations {
    
    FileInputStream in;
    FileOutputStream out;
    
    public void loadFile(String path,EditorMain editor) throws IOException {
        in=new FileInputStream(path);
        int i;
        String data="";
        while((i=in.read())!=-1)
            data=data+(char)i;
        editor.textArea.setText(data);
        in.close();

        
    }
    
    public void saveFile(String path,EditorMain editor) throws IOException {
        out=new FileOutputStream(path);
        out.write((editor.textArea.getText()).getBytes(StandardCharsets.UTF_8));
        out.close();
    }
    
}
