/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package notepad;

import static java.awt.SystemColor.text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.ZoomEvent;

/**
 *
 * @author clinic
 */
public class Notepad extends Application {

    BorderPane root = new BorderPane();

    boolean isSaved = true;
    String myClipBoard;
    FileChooser fileChooser = new FileChooser();
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    boolean islight = true;

    String fileName = "Untitled";
    String path = "";
    String oldTxt = "";
    String newTxt = "";
    MenuItem fitem = new MenuItem("New");
    MenuItem fitem2 = new MenuItem("Open");
    MenuItem fitem3 = new MenuItem("Save");
    MenuItem fitem4 = new MenuItem("Exit");
    TextArea area = new TextArea();
    MenuItem eitem = new MenuItem("Undo");
    MenuItem eitem2 = new MenuItem("Cut");
    MenuItem eitem3 = new MenuItem("Copy");
    MenuItem eitem4 = new MenuItem("Paste");
    MenuItem eitem5 = new MenuItem("Delete");

    MenuItem eitem6 = new MenuItem("Select All");
    MenuItem Hitem = new MenuItem("AboutNotePad");


    @Override
    public void init() throws Exception {
        super.init();
        fitem.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        fitem2.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        fitem3.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        fitem4.setAccelerator(KeyCombination.keyCombination("Ctrl+e"));

        eitem.setAccelerator(KeyCombination.keyCombination("Ctrl+u"));
        eitem2.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
        eitem3.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));

        eitem4.setAccelerator(KeyCombination.keyCombination("Ctrl+p"));
        eitem5.setAccelerator(KeyCombination.keyCombination("Ctrl+d"));
        eitem6.setAccelerator(KeyCombination.keyCombination("Ctrl+a"));
        area.setId("#root");
        MenuBar mbar = new MenuBar();

        Menu file = new Menu("File");
        Menu Edit = new Menu("Edit");
        Menu Help = new Menu("Help");

        file.getItems().addAll(fitem, fitem2, fitem3, fitem4);
        SeparatorMenuItem sep = new SeparatorMenuItem();
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        SeparatorMenuItem sep3 = new SeparatorMenuItem();

        file.getItems().add(3, sep);

        Edit.getItems().addAll(eitem, eitem2, eitem3, eitem4, eitem5, eitem6);
        Edit.getItems().add(1, sep2);
        Edit.getItems().add(5, sep3);
        Help.getItems().addAll(Hitem);

        mbar.getMenus().addAll(file, Edit, Help);

        root.setTop(mbar);
        root.setCenter(area);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        fitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                area.clear();

                oldTxt = "";
                newTxt = "";
                fileName = "Untitled";
                path = "";
            }
        });

        fitem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
                try {
                    File f = fileChooser.showOpenDialog(primaryStage);
                    fileName = f.getName();
                    area.clear();
                    path = f.getParent();
                    fr = new FileReader(f);
                    br = new BufferedReader(fr);
                    String line;
                    do {
                        line = br.readLine();
                        if (line != null) {
                            area.appendText(line + "\n");
                        }
                    } while (line != null);
                    br.close();
                    fr.close();
                    oldTxt = area.getText();
                    newTxt = area.getText();
                    primaryStage.setTitle(fileName + " NotePad ");
                } catch (Exception e) {
                }
            }

        });

        
        fitem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
                    File f = fileChooser.showSaveDialog(primaryStage);
                    fileName = f.getName();
                    path = f.getParent();
                    fw = new FileWriter(f);
                    bw = new BufferedWriter(fw);
                    bw.write(area.getText());
                    bw.close();
                    fw.close();
                    isSaved = true;
                    oldTxt = area.getText();
                    newTxt = area.getText();
                    primaryStage.setTitle(fileName + "  NotePad ");
                } catch (Exception e) {
                }

            }
        });

        fitem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fitem3.fire();

                primaryStage.close();

            }
        });
        eitem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                area.undo();
                isSaved = false;
                primaryStage.setTitle("*" + fileName + "  NotePad");
            }

        });

        eitem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                area.cut();
                isSaved = false;
                primaryStage.setTitle("*" + fileName + "  NotePad");
            }
        });

        eitem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                area.copy();
            }
        });

        eitem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                area.paste();

            }
        });

        eitem5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IndexRange r = area.getSelection();
                area.deleteText(r);
            }
        });

        eitem6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                area.selectAll();
            }
        });

        Hitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About NotePad");
                alert.setHeaderText("MY NotePad");
                alert.setContentText("Developed by Merehan Yehia.");
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(root, 300, 250);

        Button b1 = new Button("light mode");

        Button b2 = new Button("dark mode");
        FlowPane pane = new FlowPane();
        pane.getChildren().add(b1);
        pane.getChildren().add(b2);

        root.setBottom(pane);

        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getStylesheets().clear();
//                         
                area.setStyle("-fx-text-fill:black;");

            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getStylesheets().clear();
                area.setStyle("-fx-text-fill:white; ");

                scene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());

            }
        });

        primaryStage.setTitle("NotePad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
