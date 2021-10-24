import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.*;


import org.python.util.PythonInterpreter;
import org.python.core.*;

import javax.swing.*;
import java.lang.reflect.*;

class ScriptRunner{
    PythonInterpreter intr;
    public void runner(EditorMain editor, String path){
        File file=new File(path);
        intr=new PythonInterpreter();
        intr.set("editor", editor);
        intr.execfile(String.valueOf(file));
    }
}


public class EditorMain extends Application{
    TextAreaObserver handler;

    EditorMain editor=this;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    public TextArea textArea = new TextArea();

    @Override
    public void start(Stage stage)
    {

        stage.setTitle("Editor");
        stage.setMinWidth(800);
        stage.setHeight(800);

        //Populating default scripts
        ObservableList<String> list_plugin = FXCollections.observableArrayList();
        ListView<String> listView_plugin = new ListView<>(list_plugin);
        list_plugin.add("Date");
        list_plugin.add("Find");

        ObservableList<String> list_script = FXCollections.observableArrayList();
        ListView<String> listView_script = new ListView<>(list_script);
        

        // Create toolbar
        Button btn1 = new Button("Viewer");
        Button btn2 = new Button("File");
        Button btn3 = new Button("Text");
        Button btn4 = new Button("Add Script");
        Button btn5 = new Button("Add Plugin");
        Button btn6 = new Button("Something");

        List<Button> btns = new ArrayList<>(Arrays.asList(btn1, btn2, btn3, btn4, btn5));
        setBtnStyling("-fx-background-color: lightgray;", btns);

        ToolBar toolBar = new ToolBar(btn1, btn2, btn3, btn4, btn5);

        toolBar.setFocusTraversable(false);
        toolBar.getItems().forEach(btn -> btn.setFocusTraversable(false));
        textArea.setStyle("-fx-font-family: 'monospace'"); // Set the font

        ToolBar OptionsToolbar = new ToolBar(btn6);

        VBox toolbarPane = new VBox();
        toolbarPane.setPadding(new Insets(5, 0, 5, 0));
        toolbarPane.setStyle("-fx-background-color: DAE6F3;");

        toolbarPane.getChildren().add(toolBar);
        toolbarPane.getChildren().add(OptionsToolbar);


        // Add the main parts of the UI to the window.
        BorderPane mainBox = new BorderPane();
        mainBox.setTop(toolbarPane);
        mainBox.setCenter(textArea);
        Scene scene = new Scene(mainBox);

        // Button event handlers.
        btn1.setOnAction(event -> showDialog1());
        btn2.setOnAction(event -> toolBar.getItems().add(new Button("ButtonN")));
        //btn3.setOnAction(event -> showDialog1(listView_api, toolBar));
        btn4.setOnAction(event -> showDialog2(stage,listView_script, toolBar));

        // TextArea event handlers & caret positioning.
        textArea.caretPositionProperty().addListener((ChangeListener<Number>) (object, oldValue, newValue) ->
        {
            try {
                if (newValue.intValue() > 2 && handler != null) {
                    String arr = handler.onType(textArea.getText(newValue.intValue() - 3, newValue.intValue()));


                    if (arr != null) {
                        editor.textArea.replaceText(newValue.intValue() - 3, newValue.intValue(), "😊");
                    }

                }
            }
            catch(Exception e){}
        });

        textArea.setText("This is sample text for development testing");
        textArea.positionCaret(textArea.getText().toString().length());
        //textArea.selectRange(8, 16); // Select a range of text (and move the caret to the end)

        // Example global keypress handler.
        scene.setOnKeyPressed(keyEvent ->
        {
            // See the documentation for the KeyCode class to see all the available keys.

            KeyCode key = keyEvent.getCode();
            boolean ctrl = keyEvent.isControlDown();
            boolean shift = keyEvent.isShiftDown();
            boolean alt = keyEvent.isAltDown();

            if(key == KeyCode.F1)
            {
                new Alert(Alert.AlertType.INFORMATION, "F1", ButtonType.OK).showAndWait();
            }
            else if(ctrl && shift && key == KeyCode.B)
            {
                new Alert(Alert.AlertType.INFORMATION, "ctrl+shift+b", ButtonType.OK).showAndWait();
            }
            else if(ctrl && key == KeyCode.B)
            {
                new Alert(Alert.AlertType.INFORMATION, "ctrl+b", ButtonType.OK).showAndWait();
            }
            else if(alt && key == KeyCode.B)
            {
                new Alert(Alert.AlertType.INFORMATION, "alt+b", ButtonType.OK).showAndWait();
            }
        });

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void showDialog1()
    {
        // TextInputDialog is a subclass of Dialog that just presents a single text field.

        var dialog = new TextInputDialog();
        dialog.setTitle("Text entry dialog box");
        dialog.setHeaderText("Enter text");

        // 'showAndWait()' opens the dialog and waits for the user to press the 'OK' or 'Cancel' button. It returns an Optional, which is a whole other discussion, but we can call 'orElse(null)' on that to get the actual string entered if the user pressed 'OK', or null if the user pressed 'Cancel'.

        var inputStr = dialog.showAndWait().orElse(null);
        if(inputStr != null)
        {
            // Alert is another specialised dialog just for displaying a quick message.
            new Alert(
                    Alert.AlertType.INFORMATION,
                    "You entered '" + inputStr + "'",
                    ButtonType.OK).showAndWait();
        }
    }

    private void showDialog2(Stage stage,ListView<String> listView, ToolBar parentToolbar)
    {
        Button addBtn = new Button("Add...");
        Button removeBtn = new Button("Remove...");
        ToolBar toolBar = new ToolBar(addBtn, removeBtn);

        addBtn.setOnAction(e->
        {

            ScriptRunner runner=new ScriptRunner();
            String path=fileChooser(stage);
            runner.runner(editor,path);
            listView.add("Emoji Script");
        });

        // FYI: 'ObservableList' inherits from the ordinary List interface, but also works as a subject for any 'observer-pattern' purposes; e.g., to allow an on-screen ListView to display any changes made to the list as they are made.

        BorderPane box = new BorderPane();
        box.setTop(toolBar);
        box.setCenter(listView);

        Dialog dialog = new Dialog();
        dialog.setTitle("List of things");
        dialog.setHeaderText("Here's a list of things");
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    // Buttons styling

    private void setOnHover(Button btn){
        btn.setOnMouseEntered(event -> {
//            if (btn.getBackground().toString() == "gray")
//            btn.setStyle("-fx-background-color: gray;");
        });
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: lightgray;"));
    }

    private void setBtnStyling(String background_color, List<Button> btns){

        for  (Button b : btns){
            b.setStyle(background_color);
            setOnHover(b);
        }
    }


    public String fileChooser(Stage stage){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file=chooser.showOpenDialog(stage);
        String path=file.toString();
        return path;

    }

    public void registerHandler(TextAreaObserver handler){
        this.handler=handler;
    }


}
