package com.eduardoprogramador.cacador;

/*
 * Copyright 2022. Eduardo Programador
 * www.eduardoprogramador.com
 * consultoria@eduardoprogramador.com
 *
 * Todos os direitos reservados
 * */

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;

public class UasgScreen extends CommonTask implements EventHandler {
    //declare
    private Stage stage;
    private MenuBar menuBar;
    private Label foot, title, tutorial;
    private TextField field;
    private Button btnGo, btnExcel, btnInspect;
    private TextArea textArea;
    private ScrollPane scrollPane;
    private HBox hName, hOptions;
    private VBox root;
    private Scene scene;
    private ArrayList<String> arrayListUasg;
    private ProgressBar progressBar;

    public UasgScreen (Stage stage,MenuBar menuBar,Label foot) {
        this.stage = stage;
        this.menuBar = menuBar;
        this.foot = foot;
        loadNameUI();
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnGo)) {
            Runnable runnable = () -> {
                processUasgData();
            };
            new Thread(runnable).start();
        } else if(event.getTarget().equals(btnExcel)) {
            exportToExcel();
        } else if(event.getTarget().equals(btnInspect)) {
            inspectIt(CommonTask.UASG_TYPE);
        }
    }

    private void loadNameUI() {
        progressBar = new ProgressBar();
        progressBar.setVisible(false);
        title = new Label("Consultar UASG");
        title.setFont(new Font("calibri",23));
        tutorial = new Label("Informe parte do nome da UASG:");
        tutorial.setFont(new Font("calibri",15));
        field = new TextField();
        btnGo = new Button("Consultar");
        btnExcel = new Button("Exportar para Excel");
        btnInspect = new Button("Inspecionar");
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        hName = new HBox();
        hName.setSpacing(15);
        hName.setAlignment(Pos.CENTER);
        hName.getChildren().addAll(tutorial,field);
        hOptions = new HBox();
        hOptions.setSpacing(15);
        hOptions.setAlignment(Pos.CENTER);
        hOptions.getChildren().addAll(btnExcel,btnInspect);
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(menuBar,title,hName,btnGo,scrollPane,hOptions,progressBar,foot);
        scene = new Scene(root,600,700);
        stage.setScene(scene);
        stage.setTitle("Ca??ador ComprasNET");
        stage.setOpacity(0.85f);
        stage.setResizable(false);
        stage.show();

        //listenning
        btnGo.setOnAction(this);
        btnExcel.setOnAction(this);
        btnInspect.setOnAction(this);
    }

    private void processUasgData() {
        progressBar.setVisible(true);
        String uasg = field.getText();
        arrayListUasg = DataTask.getUasgData(uasg);
        if(arrayListUasg != null) {
            int len = arrayListUasg.size();
            for (int i = 0; i < len; i++) {
                textArea.appendText(arrayListUasg.get(i) + "\n");
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisible(false);
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showDialog(CommonTask.ERROR,"Erro","Erro de Processamento","Um erro ocorreu ao fazer o processamento dos dados");
                    progressBar.setVisible(false);
                }
            });

        }
    }

    private void exportToExcel() {
        String path = CommonTask.putFile(stage);
        String content = textArea.getText();
        if(path != null && arrayListUasg != null) {
            boolean isSaved = CommonTask.exportExcel(path,content);
            if(isSaved) {
                showDialog(CommonTask.INFORMATION,"??xito","Arquivo salvo com sucesso","O arquivo que voc?? escolheu foi salvo com ??xito");
            } else {
                showDialog(CommonTask.ERROR,"Erro","Erro de salvamento","N??o foi poss??vel salvar o arquivo especificado");
            }
        } else {
            showDialog(CommonTask.WARNING,"Aviso","Dados Vazios","Efetue uma consulta antes de tentar m??todos de salvamento");
        }
    }
}
