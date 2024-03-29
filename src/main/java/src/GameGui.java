package src;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class GameGui extends Application {
    boolean alphaBeta = false;
    ArrayList<Button> bts = new ArrayList<>();// board buttons
    HashSet<Integer> h = new HashSet<>();//to completed columns
    FileWriter fileWriter = new FileWriter("trace.txt");//file of tracing
    PrintWriter printWriter = new PrintWriter(fileWriter);
    int levels = 0;//to print levels in file
    HostServices hostServices = getHostServices();//to show file

    public GameGui() throws IOException {
    }

    public static String buildString(boolean[] arr, boolean[] played) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i = i - 7) {
            for (int j = i - 6; j <= i; j++) {
                if (arr[j] && played[j]) {
                    stringBuilder.append(" 1 ");
                } else if (!arr[j] && played[j]) {
                    stringBuilder.append(" 0 ");
                } else {
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
        Label red4 = new Label("Number of 4 Red :");
        Label red4Val = new Label("0");
        Label yellow4 = new Label("Number of 4 Yellow : ");
        Label yellowVal = new Label("0");
        yellowVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");
        red4Val.setStyle("-fx-text-fill:rgb(17, 186, 216);");
        Button roundButton;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                roundButton = new Button();
                roundButton.setStyle("-fx-background-radius: 5em; " + "-fx-min-width: 50px; " + "-fx-min-height: 50px; " + "-fx-max-width: 50px; " + "-fx-max-height: 50px;" + "-fx-background-color: white; -fx-stroke: black; -fx-stroke-width: 2;");
                roundButton.setId("roundedButton");
                roundButton.setDisable(true);
                bts.add(roundButton);
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                Connect67.add(stack, j, i);
                int finalI = j;
                roundButton.setOnAction(e -> {
                            GamePlay.currentState = new StateNode();
                            GamePlay.setChildren(new ArrayList<>(), new ArrayList<>());
                            user(Connect67, Integer.parseInt(LevelVal.getText()), finalI, red4Val, yellowVal);
                        }
                );
                stack.setStyle("-fx-background-color:blue;");
                Connect67.setHgap(.25);
                Connect67.setVgap(.25);
            }
        }
        ChoiceBox chooseAlgorithm = new ChoiceBox<String>(FXCollections.observableArrayList("without alpha-beta pruning", "with alpha-beta pruning"));
        chooseAlgorithm.setValue("without alpha-beta pruning");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseAlgorithm.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label label) {
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
            String minmax = chooseAlgorithm.getValue().toString();
            alphaBeta = minmax.equals("with alpha-beta pruning");
            bts.forEach(b -> b.setDisable(false));
            reset.setDisable(false);
            trace.setDisable(false);
            start.setDisable(true);
            chooseAlgorithm.setDisable(true);
            LevelVal.setDisable(true);
        });
        reset.setOnAction(event -> {
            GamePlay.currentState = new StateNode();
            GamePlay.moves = 0;
            GamePlay.setChildren(new ArrayList<>(), new ArrayList<>());
            bts = new ArrayList<>();
            start(stage);
            h = new HashSet<>();
        });
        stop.setOnAction(event -> {
            stage.close();
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        trace.setOnAction(event -> {
            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            hostServices.showDocument("trace.txt");
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

    private void makeTrace(ArrayList<ArrayList<boolean[]>> ChildrenPlayed, ArrayList<ArrayList<boolean[]>> ChildrenColor) {
        printWriter.println("After user move number " + (levels++) + "\n");
        for (int i = 0; i < ChildrenPlayed.size(); i++) {
            printWriter.println("State = " + i + "\n");
            for (int i1 = 0; i1 < ChildrenPlayed.get(i).size(); i1++) {
                printWriter.println("Action = " + (i1) + "\n");
                printWriter.println(buildString(ChildrenColor.get(i).get(i1), ChildrenPlayed.get(i).get(i1)));
            }
        }
    }

    public void user(GridPane board, int level, int col, Label r, Label y) {
        StateNode node = GamePlay.userTurn(col);
        Draw(board, node, level, r, y);
        r.setText(String.valueOf(node.getRedPoints()));
        y.setText(String.valueOf(node.getYellowPoints()));
        computer(board, level, r, y);
        makeTrace(GamePlay.getChildrenPlayedArr(), GamePlay.getChildrenColorArr());
    }

    public void computer(GridPane board, int level, Label r, Label y) {
        StateNode node = GamePlay.myTurn(level, alphaBeta);
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
                roundButton.setStyle("-fx-background-radius: 5em; " + "-fx-min-width: 50px; " + "-fx-min-height: 50px; " + "-fx-max-width: 50px; " + "-fx-max-height: 50px;" + " -fx-stroke: black; -fx-stroke-width: 2;" + "-fx-background-color:" + color + ";");
                roundButton.setId("roundedButton");
                StackPane stack = new StackPane();
                stack.getChildren().add(roundButton);
                board.add(stack, j, 5 - i);
                int finalI = j;
                roundButton.setOnAction(e -> {
                    if (stateNode.played[35 + finalI]) {
                        h.add(finalI);
                    }
                    System.out.println("Completed Columns: " + h.stream().toList());
                    if (h.contains(finalI)) {
                        new Alert(Alert.AlertType.WARNING, "THIS COLUMN IS COMPLETED ").show();
                        return;
                    }
                    user(board, level, finalI, r, y);
                });
                k++;
                stack.setStyle("-fx-background-color:blue;");
                board.setHgap(.25);
                board.setVgap(.25);
            }
        }
    }
}