package src;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameGui extends Application {
    int index = 0;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Connect4");
        Pane pane = new Pane();
        GridPane dashboard = new GridPane();
        GridPane Connect67 = new GridPane();
        Connect67.setPadding(new Insets(20, 20, 20, 20));
        dashboard.setPadding(new Insets(20, 20, 20, 400));
          Label Level=new Label("Level");
          TextField LevelVal=new TextField();

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 6; j++) {

                Button roundButton = new Button();

                roundButton.setStyle(
                        "-fx-background-radius: 5em; " +
                                "-fx-min-width: 50px; " +
                                "-fx-min-height: 50px; " +
                                "-fx-max-width: 50px; " +
                                "-fx-max-height: 50px;"+
                                "-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 2;"
                );
                roundButton.setId("roundedButton");
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                Connect67.add(stack, i, j);
                int finalI = i;
                roundButton.setOnAction(e->{
                    System.out.println(LevelVal.getText()+" "+finalI);
                    GamePlay.currentState =new StateNode();
                    user(Connect67, Integer.parseInt(LevelVal.getText()),finalI);

                });
                stack.setStyle("-fx-background-color:blue;");
                Connect67.setHgap(.25);
                Connect67.setVgap(.25);
            }
        }


        ChoiceBox chooseAlgorithm = new ChoiceBox<String>(
                FXCollections.observableArrayList("without alpha-beta pruning", "with alpha-beta pruning"));
        chooseAlgorithm.setValue("");
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
        Button reset = new Button("Reset");
        Button trace = new Button("Trace");
        Button stop = new Button("Stop");

        reset.setDisable(true);
        trace.setDisable(true);
        stop.setDisable(true);


        start.setOnAction(event -> {
            reset.setDisable(false);
            trace.setDisable(false);
            stop.setDisable(false);
            start.setDisable(true);

        });

        reset.setOnAction(event -> {


        });
        Pane pane2=new Pane();
        TextArea textArea=new TextArea();
        Button finish=new Button("Return");
        pane2.getChildren().addAll(textArea,finish);
        Scene scene2=new Scene(pane2);
trace.setOnAction(event -> {
    stage.setScene(scene2);

});



        dashboard.add(start, 0, 8);
        dashboard.setHgap(5);
        dashboard.add(stop, 1, 8);
        dashboard.setVgap(8);
        dashboard.add(Level, 0, 4);
        dashboard.setHgap(5);
        dashboard.add(LevelVal, 1, 4);
        dashboard.setVgap(8);
        dashboard.add(chooseAlgorithm, 1, 6);
        dashboard.setHgap(5);
        dashboard.add(trace, 2, 8);
        dashboard.setVgap(8);
        dashboard.add(reset, 3, 8);


        Connect67.setMinSize(500, 400);
        pane.getChildren().addAll(Connect67, dashboard);
        Scene scene=new Scene(pane);
        stage.setScene(scene);
        finish.setOnAction(event -> {
            stage.setScene(scene);

        });
        stage.show();
    }

    private void makeActionOn(int col) {
        System.out.println(col);
    }
    public void user(GridPane board, int level,int col)
    {

        StateNode node =GamePlay.userTurn(level,col);
        Draw(board,node,level);
        System.out.println("board = " + node.getRedPoints() + ", level = " + level + ", col = " + col);
        computer(board,level);
    }
    public void computer(GridPane board,int level)
    {
        StateNode node =GamePlay.myTurn(level);
        for (int i = 0; i < node.color.length; i++) {
            System.out.print("[ "+node.color[i]+ " , "+node.played[i]+" ]");
        }
        Draw(board,node,level);

    }
    private void Draw(GridPane board,StateNode stateNode,int level){
        int k=0;
        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 7; j++) {

                Button roundButton = new Button();
                String color="";
                if(stateNode.color[k]  &&stateNode.played[k])
                {
                   color="red";


                }
                else if(!stateNode.color[k]  &&stateNode.played [k])
                {
                    color="yellow";

                }
                else {
                    color="white";
                }
                 k++;
                roundButton.setStyle(
                        "-fx-background-radius: 5em; " +
                                "-fx-min-width: 50px; " +
                                "-fx-min-height: 50px; " +
                                "-fx-max-width: 50px; " +
                                "-fx-max-height: 50px;"+
                                " -fx-stroke: black; -fx-stroke-width: 2;"+
                                "-fx-background-color:"+color+";");
                roundButton.setId("roundedButton");
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                board.add(stack, j, 5-i);
                int finalI = j;
                roundButton.setOnAction(e->{
                    System.out.println(level+"  "+finalI);
                    user(board, level,finalI);

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