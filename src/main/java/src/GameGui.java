package src;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;



public class GameGui extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Connect4");
        Pane pane = new Pane();
        GridPane dashboard = new GridPane();
        GridPane Connect67 = new GridPane();
        Connect67.setPadding(new Insets(20, 20, 20, 20));
        dashboard.setPadding(new Insets(20, 20, 20, 400));
        Label Level = new Label("Level");
        TextField LevelVal = new TextField();

        Button roundButton;
        ArrayList<Button>bts=new ArrayList<>();
        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 7; j++) {

                 roundButton = new Button();

                roundButton.setStyle(
                        "-fx-background-radius: 5em; " +
                                "-fx-min-width: 50px; " +
                                "-fx-min-height: 50px; " +
                                "-fx-max-width: 50px; " +
                                "-fx-max-height: 50px;" +
                                "-fx-background-color: white; -fx-stroke: black; -fx-stroke-width: 2;"
                );
                roundButton.setId("roundedButton");
                roundButton.setDisable(true);
                bts.add(roundButton);
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                Connect67.add(stack, j, i);
                int finalI = j;
                roundButton.setOnAction(e -> {
                    GamePlay.currentState = new StateNode();
                    user(Connect67, Integer.parseInt(LevelVal.getText()), finalI);

                });
                stack.setStyle("-fx-background-color:blue;");
                Connect67.setHgap(.25);
                Connect67.setVgap(.25);
            }
        }


        ChoiceBox chooseAlgorithm = new ChoiceBox<String>(
                FXCollections.observableArrayList("without alpha-beta pruning", "with alpha-beta pruning"));
        chooseAlgorithm.setValue("without alpha-beta pruning");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseAlgorithm.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Choose MinMax");
                    }
                    return;
                }
            }
        });


        Button start = new Button("Start");
        start.setStyle("-fx-background-color:coral;-fx-font-size:20px;");

        Button reset = new Button("Reset");
        reset.setStyle("-fx-background-color:crimson;-fx-font-size:20px;");

        Button trace = new Button("Trace");
        trace.setStyle("-fx-background-color:orange;-fx-font-size:20px;");

        Button stop = new Button("Exit");
        stop.setStyle("-fx-background-color:red;-fx-font-size:20px;");

        reset.setDisable(true);
        trace.setDisable(true);
        stop.setDisable(false);


        start.setOnAction(event -> {
            if(!LevelVal.getText().matches("[0-8]+")){
                new Alert(Alert.AlertType.ERROR,"Enter Valid Level integer number >= 0 ").show();
                return;
            }
            bts.forEach(b->b.setDisable(false));
            reset.setDisable(false);
            trace.setDisable(false);
            start.setDisable(true);

        });

        reset.setOnAction(event -> start(new Stage()));
        stop.setOnAction(event -> stage.close());
        Pane pane2 = new Pane();
        TextArea textArea = new TextArea();
        textArea.setMinHeight(1000);
        textArea.setMinWidth(1000);
        textArea.setEditable(false);


        Button finish = new Button("Return");
        pane2.getChildren().addAll(textArea, finish);
        Scene scene2 = new Scene(pane2);
        trace.setOnAction(event -> {


            Stage stage2 = new Stage();
            stage2.setScene(scene2);
            stage2.show();
        });


        dashboard.add(start, 0, 8);
        dashboard.setHgap(5);
        dashboard.add(trace, 1, 8);
        dashboard.setVgap(8);
        dashboard.add(Level, 0, 4);
        dashboard.setHgap(5);
        dashboard.add(LevelVal, 1, 4);
        dashboard.setVgap(8);
        dashboard.add(chooseAlgorithm, 1, 6);
        dashboard.setHgap(5);
        dashboard.add(stop, 2, 8);
        dashboard.setVgap(8);
        dashboard.add(reset, 3, 8);
        Connect67.setMinSize(500, 400);
        pane.getChildren().addAll(Connect67, dashboard);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        finish.setOnAction(event -> stage.setScene(scene));
        stage.show();
    }



    public void user(GridPane board, int level, int col) {

        StateNode node = GamePlay.userTurn(level, col);
        Draw(board, node, level);
        computer(board, level);
    }

    public void computer(GridPane board, int level) {
        StateNode node = GamePlay.myTurn(level);
      //  for (int i = 0; i < node.color.length; i++) {
           // System.out.print("[ " + node.color[i] + " , " + node.played[i] + " ]");
        //}
        Draw(board, node, level);

    }

    private void Draw(GridPane board, StateNode stateNode, int level) {
        int k = 0;
        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 7; j++) {

                Button roundButton = new Button();
                String color ;
                if (stateNode.color[k] && stateNode.played[k]) {
                    color = "red";


                } else if (!stateNode.color[k] && stateNode.played[k]) {
                    color = "yellow";

                } else {
                    color = "white";
                }
                k++;
                roundButton.setStyle(
                        "-fx-background-radius: 5em; " +
                                "-fx-min-width: 50px; " +
                                "-fx-min-height: 50px; " +
                                "-fx-max-width: 50px; " +
                                "-fx-max-height: 50px;" +
                                " -fx-stroke: black; -fx-stroke-width: 2;" +
                                "-fx-background-color:" + color + ";");
                roundButton.setId("roundedButton");
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                board.add(stack, j, 5 - i);
                int finalI = j;
                roundButton.setOnAction(e -> {
                    System.out.println(level + "  " + finalI);
                    user(board, level, finalI);

                });
                stack.setStyle("-fx-background-color:blue;");
                board.setHgap(.25);
                board.setVgap(.25);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }


}