package com.eduardoprogramador.cacador;

/*
 * Copyright 2022. Eduardo Programador
 * www.eduardoprogramador.com
 * consultoria@eduardoprogramador.com
 *
 * Todos os direitos reservados
 * */

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

public class NameScreen extends CommonTask implements EventHandler {
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
    private ArrayList<String> arrayListName;
    private ProgressBar progressBar;

    public NameScreen(Stage stage,MenuBar menuBar,Label foot) {
        this.stage = stage;
        this.menuBar = menuBar;
        this.foot = foot;
        loadNameUI();
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnGo)) {
            Runnable runnable = () -> {
                processNameData();
            };
            new Thread(runnable).start();

        } else if(event.getTarget().equals(btnExcel)) {
            exportToExcel();
        } else if(event.getTarget().equals(btnInspect)) {
            inspectIt(CommonTask.NAME_TYPE);
        }
    }

    private void loadNameUI() {
        progressBar = new ProgressBar();
        progressBar.setVisible(false);
        title = new Label("Consulta por Nome");
        title.setFont(new Font("calibri",23));
        tutorial = new Label("Informe parte do nome do fornecedor:");
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
        stage.setTitle("Caçador ComprasNET");
        stage.setOpacity(0.85f);
        stage.setResizable(false);
        stage.show();

        //listenning
        btnGo.setOnAction(this);
        btnExcel.setOnAction(this);
        btnInspect.setOnAction(this);
    }

    private void processNameData() {
        progressBar.setVisible(true);
        String name = field.getText();
        arrayListName = DataTask.getNameData(name);
        int len = arrayListName.size();
        if(arrayListName != null) {
            for (int i = 0; i < len; i++) {
                textArea.appendText(arrayListName.get(i) + "\n");
            }
            progressBar.setVisible(false);
        } else {
            showDialog(CommonTask.ERROR,"Erro","Erro de Processamento","Um erro ocorreu ao fazer o processamento dos dados");
            progressBar.setVisible(false);
        }
    }

    private void exportToExcel() {
        String path = CommonTask.putFile(stage);
        String content = textArea.getText();
        if(path != null && arrayListName != null) {
            boolean isSaved = CommonTask.exportExcel(path,content);
            if(isSaved) {
                showDialog(CommonTask.INFORMATION,"Êxito","Arquivo salvo com sucesso","O arquivo que você escolheu foi salvo com êxito");
            } else {
                showDialog(CommonTask.ERROR,"Erro","Erro de salvamento","Não foi possível salvar o arquivo especificado");
            }
        } else {
            showDialog(CommonTask.WARNING,"Aviso","Dados Vazios","Efetue uma consulta antes de tentar métodos de salvamento");
        }
    }
}
