import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;


public class EditorMain extends Application{

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    private TextArea textArea = new TextArea();

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

        ObservableList<String> list_api = FXCollections.observableArrayList();
        ListView<String> listView_api = new ListView<>(list_api);
        list_api.add("Emoji");

        // Create toolbar
        Button btn1 = new Button("File");
        Button btn2 = new Button("Edit");
        Button btn3 = new Button("Add Script");
        Button btn4 = new Button("Add Plugin");

        btn1.setStyle("-fx-background-color: lightgray;");
        btn2.setStyle("-fx-background-color: lightgray;");
        btn3.setStyle("-fx-background-color: lightgray;");
        btn4.setStyle("-fx-background-color: lightgray;");

        setOnHover(btn1);
        setOnHover(btn2);
        setOnHover(btn3);
        setOnHover(btn4);


        ToolBar toolBar = new ToolBar(btn1, btn2, btn3, btn4);

        // Subtle user experience tweaks
        toolBar.setFocusTraversable(false);
        toolBar.getItems().forEach(btn -> btn.setFocusTraversable(false));
        textArea.setStyle("-fx-font-family: 'monospace'"); // Set the font

        // Add the main parts of the UI to the window.
        BorderPane mainBox = new BorderPane();
        mainBox.setTop(toolBar);
        mainBox.setCenter(textArea);
        Scene scene = new Scene(mainBox);

        // Button event handlers.
        btn1.setOnAction(event -> showDialog1());
        btn2.setOnAction(event -> toolBar.getItems().add(new Button("ButtonN")));
        btn3.setOnAction(event -> showDialog2(listView_api, toolBar));
        btn4.setOnAction(event -> showDialog2(listView_plugin, toolBar));

        // TextArea event handlers & caret positioning.
        textArea.textProperty().addListener((object, oldValue, newValue) ->
        {
            System.out.println("caret position is " + textArea.getCaretPosition() +
                    "; text is\n---\n" + newValue + "\n---\n");
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

    private void setOnHover(Button btn){
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: gray;"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: lightgray;"));
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

    private void showDialog2(ListView<String> listView, ToolBar parentToolbar)
    {
        Button addBtn = new Button("Add...");
        Button removeBtn = new Button("Remove...");
        ToolBar toolBar = new ToolBar(addBtn, removeBtn);

        addBtn.setOnAction(event -> {
            parentToolbar.getItems().add(new Button("New Btn"));
        });
        removeBtn.setOnAction(event -> new Alert(Alert.AlertType.INFORMATION, "Remove...", ButtonType.OK).showAndWait());

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
}
