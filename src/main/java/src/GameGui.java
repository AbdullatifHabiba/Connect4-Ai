package src;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;


public class GameGui extends Application {


    TextArea textArea = new TextArea();
    boolean alphaBeta=false;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Connect4");
        GridPane pane = new GridPane();
        GridPane dashboard = new GridPane();
        GridPane Connect67 = new GridPane();
        Connect67.setPadding(new Insets(20, 20, 20, 20));
        dashboard.setPadding(new Insets(20, 20, 20, 20));
        Label Level = new Label("Level");
        TextField LevelVal = new TextField();
        GridPane pane2 = new GridPane();
        Label red4 = new Label("Number of 4 Red :");
        Label red4Val = new Label("0");
        Label yellow4 = new Label("Number of 4 Yellow : ");
        Label yellowVal = new Label("0");
        yellowVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");
        red4Val.setStyle("-fx-text-fill:rgb(17, 186, 216);");
        Button roundButton;
        ArrayList<Button> bts = new ArrayList<>();
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
                            GamePlay.setChildrens(new ArrayList<>());
                            ArrayList<ArrayList<StateNode>> list = user(Connect67, Integer.parseInt(LevelVal.getText()), finalI, red4Val, yellowVal);

                            list.forEach(state -> {
                                textArea.appendText("Level= " + (list.indexOf(state)) + "\n");
                                state.forEach(
                                        el -> {
                                            textArea.appendText("S= " + (state.indexOf(el)) + "\n");

                                            textArea.appendText(buildString(el.color, el.played));
                                        });
                            });


                        }

                );
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
        start.setStyle("-fx-background-color:crimson;-fx-font-size:20px;");
        start.setMinWidth(150);
        Button reset = new Button("Reset");
        reset.setStyle("-fx-background-color:coral;-fx-font-size:20px;");
        reset.setMinWidth(150);

        Button trace = new Button("Trace");
        trace.setStyle("-fx-background-color:orange;-fx-font-size:20px;");
        trace.setMinWidth(150);

        Button stop = new Button("Exit");
        stop.setStyle("-fx-background-color:red;-fx-font-size:20px;");
        stop.setMinWidth(150);

        reset.setDisable(true);
        trace.setDisable(true);
        stop.setDisable(false);


        start.setOnAction(event -> {
            if (!LevelVal.getText().matches("[0-8]+")) {
                new Alert(Alert.AlertType.ERROR, "Enter Valid Level integer number >= 0 ").show();
                return;
            }
            String minmax=chooseAlgorithm.getValue().toString();
            alphaBeta=minmax.equals("with alpha-beta pruning");
            bts.forEach(b -> b.setDisable(false));
            reset.setDisable(false);
            trace.setDisable(false);
            start.setDisable(true);
            chooseAlgorithm.setDisable(true);
            LevelVal.setDisable(true);

        });

        reset.setOnAction(event -> {
            stage.close();
            start(new Stage());
            textArea.clear();
        });
        stop.setOnAction(event -> stage.close());

        textArea.setMinHeight(500);
        textArea.setMinWidth(500);
        textArea.setEditable(false);


        Button finish = new Button("Return");
        finish.setStyle("-fx-background-color:green;-fx-font-size:20px;");
        finish.setMinWidth(150);
        pane2.add(textArea, 0, 0);
        pane2.add(finish, 0, 1);
        Scene scene2 = new Scene(pane2);
        trace.setOnAction(event -> {

            Stage stage2 = new Stage();
            stage2.setScene(scene2);
            stage2.show();
            finish.setOnAction(e -> stage2.close());

        });


        dashboard.add(start, 1, 6);
        dashboard.setHgap(5);
        dashboard.add(trace, 1, 7);
        dashboard.setVgap(8);
        dashboard.add(Level, 0, 2);
        dashboard.setHgap(5);
        dashboard.add(LevelVal, 1, 2);
        dashboard.setVgap(8);
        dashboard.add(chooseAlgorithm, 1, 4);
        dashboard.setHgap(5);
        dashboard.add(stop, 1, 9);
        dashboard.setVgap(8);
        dashboard.add(reset, 1, 8);


        pane.add(Connect67, 0, 0);
        pane.add(dashboard, 1, 0);
        pane.add(red4, 0, 1);
        pane.setHgap(5);
        pane.add(red4Val, 1, 1);
        pane.add(yellow4, 0, 2);
        pane.setHgap(5);
        pane.add(yellowVal, 1, 2);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


    public ArrayList<ArrayList<StateNode>> user(GridPane board, int level, int col, Label r, Label y) {

        StateNode node = GamePlay.userTurn(level, col);
        Draw(board, node, level, r, y);
        r.setText(String.valueOf(node.getRedPoints()));
        y.setText(String.valueOf(node.getYellowPoints()));

        computer(board, level, r, y);
        return GamePlay.getChildrens();
    }


    public void computer(GridPane board, int level, Label r, Label y) {
        StateNode node = GamePlay.myTurn(level,alphaBeta);
        r.setText(String.valueOf(node.getRedPoints()));
        y.setText(String.valueOf(node.getYellowPoints()));
        Draw(board, node, level, r, y);

    }

    private void Draw(GridPane board, StateNode stateNode, int level, Label r, Label y) {
        int k = 0;
        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 7; j++) {

                Button roundButton = new Button();
                String color;
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
                    ArrayList<ArrayList<StateNode>> list = user(board, level, finalI, r, y);
                    //textArea.clear();
                    list.forEach(state -> {
                        textArea.appendText("Level= " + (list.indexOf(state)) + "\n");
                        state.forEach(
                                el -> {
                                    textArea.appendText("S= " + (state.indexOf(el)) + "\n");

                                    textArea.appendText(buildString(el.color, el.played));
                                });
                    });

                });
                stack.setStyle("-fx-background-color:blue;");
                board.setHgap(.25);
                board.setVgap(.25);
            }
        }
    }

    public static String buildString(boolean[] arr, boolean[] played) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=arr.length-1;i>=0;i=i-7)
        {
            for(int j=i-6;j<=i;j++)
            {
                if(arr[j]  && played[j])
                {
                    stringBuilder.append(" 1 ");
                }
                else if(!arr[j]  && played[j])
                {
                    stringBuilder.append(" 0 ");
                }else{
                    stringBuilder.append(" - ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        launch();
    }


}