import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class toolbar_handler {

    public void toolbarHandler(Button btn, ToolBar toolbar, VBox vbox, Button isChecked, Stage stage){
        isChecked.setStyle("-fx-background-color: lightgray;");
        btn.setStyle("-fx-background-color: gray;");
        subToolbarHandler(btn.getText(), vbox, stage);
    }

    public void subToolbarHandler(String btn_txt, VBox vbox, Stage stage){
        switch(btn_txt){
            case "Viewer":
                Button btn1 = new Button("Exit");
                if (vbox.getChildren().size() > 1){
                    vbox.getChildren().remove(vbox.getChildren().size()-1);
                }
                btn1.setOnAction(event -> {
                    Platform.exit();
                    System.exit(0);
                });
                ToolBar OptionsToolbar_viewer = new ToolBar(btn1);

                vbox.setPadding(new Insets(5, 0, 5, 0));
                vbox.setStyle("-fx-background-color: DAE6F3;");
                vbox.getChildren().add(OptionsToolbar_viewer);
                break;
            case "File":
                Button btn_file_open = new Button("Open");
                Button btn_file_save = new Button("Save");
                Button btn_file_saveas = new Button("Save as");
                List<Button> file_btns = new ArrayList<>(Arrays.asList(btn_file_open, btn_file_save, btn_file_saveas));
                setFileListeners(file_btns, stage);
                ToolBar OptionsToolbar_file = new ToolBar(btn_file_open, btn_file_save, btn_file_saveas);

                if (vbox.getChildren().size() > 1){
                    vbox.getChildren().remove(vbox.getChildren().size()-1);
                }

                vbox.setPadding(new Insets(5, 0, 5, 0));
                vbox.setStyle("-fx-background-color: DAE6F3;");
                vbox.getChildren().add(OptionsToolbar_file);
                break;
            case "Text":
                Button btn_text_paste = new Button("Paste");
                Button btn_text_copy = new Button("Copy");
                Button btn_text_format = new Button("Format");
                Button btn_text_whitespaces = new Button("Remove White space");
                Button btn_text_clear = new Button("Clear");
                Button btn_text_load_json = new Button("Load JSON data");
                List<Button> text_btns = new ArrayList<>(Arrays.asList(btn_text_paste, btn_text_copy, btn_text_format,
                        btn_text_whitespaces, btn_text_clear, btn_text_load_json));
                setTextListeners(text_btns, stage);
                ToolBar OptionsToolbar_txt = new ToolBar(btn_text_paste, btn_text_copy, btn_text_format,
                        btn_text_whitespaces, btn_text_clear, btn_text_load_json);

                if (vbox.getChildren().size() > 1){
                    vbox.getChildren().remove(vbox.getChildren().size()-1);
                }

                vbox.setPadding(new Insets(5, 0, 5, 0));
                vbox.setStyle("-fx-background-color: DAE6F3;");
                vbox.getChildren().add(OptionsToolbar_txt);
                break;
        }
    }

    private void setFileListeners(List<Button> btns, Stage stage){
        for (Button b : btns){
            if (b.getText().equals("Open")){
                b.setOnAction(event -> {
                    File file = openFileDialog(stage, "Open", false, false);
                });
            }else{
                if (b.getText().equals("Save")){
                    b.setOnAction(event -> {
                        File file = openFileDialog(stage, "Save", true, false);
                    });
                }else{
                    b.setOnAction(event -> {
                        File file = openFileDialog(stage, "Save As", true, false);
                    });
                }
            }
        }
    }

    private File openFileDialog(Stage stage, String DialogName, boolean save, boolean json){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(DialogName);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        if (json){
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(".json", ".json"));
        }
        File file = null;
        if (save){
            file = fileChooser.showSaveDialog(stage);

        }else{
            file = fileChooser.showOpenDialog(stage);
        }
        if (file == null){
            return null;
        }
        return file;
    }

    private void setTextListeners(List<Button> btns, Stage stage){
        for (Button b : btns){
            switch(b.getText()){
                case "Paste":
                    b.setOnAction(event -> {
                        try {
                            String clipboardData = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case "Copy":
                    // get text area here and select the text
                    break;
                case "Format":
                    // ???
                    break;
                case "Remove White space":
                    // get text area and use java inbuilt function to remove white spaces
                    break;
                case "Clear":
                    // get textarea and clear it
                    break;
                case "Load JSON data":
                    b.setOnAction(event -> {
                        File file = openFileDialog(stage, "Select JSON File", false, true);
                    });
                    break;
            }
        }
    }
}
