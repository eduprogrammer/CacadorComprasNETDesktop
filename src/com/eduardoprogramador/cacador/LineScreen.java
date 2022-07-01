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

public class LineScreen extends CommonTask implements EventHandler {
    //declare
    private Stage stage;
    private MenuBar menuBar;
    private Label foot, title, tutorial;
    private TextField field;
    private Button btnGo, btnExcel, btnCheck;
    private TextArea textArea;
    private ScrollPane scrollPane;
    private HBox hId, hOptions;
    private VBox root;
    private Scene scene;
    private ArrayList<String> arrayListRes;
    private ProgressBar progressBar;

    public LineScreen(Stage stage, MenuBar menuBar, Label foot) {
        this.stage = stage;
        this.menuBar = menuBar;
        this.foot = foot;
        lineUI();
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnGo)) {
            progressBar.setVisible(true);
            Runnable runnable = () -> {
                arrayListRes = DataTask.getIdData(field.getText());
                processIdResult();
            };
            new Thread(runnable).start();

        } else if(event.getTarget().equals(btnExcel)) {
            exportToExcel();
        } else if(event.getTarget().equals(btnCheck)) {
            inspectIt(CommonTask.LINE_TYPE);
        }
    }

    private void lineUI() {
        progressBar = new ProgressBar();
        progressBar.setVisible(false);
        title = new Label("Buscar ID de fornecimento");
        title.setFont(new Font("calibri",23));
        tutorial = new Label("Informe uma palavra-chave para iniciar a busca. \nPor exemplo: para materias (canetas, máscara,etc.), \nutilize a palavra MATERIAL. \nPara serviços, \nutilize uma palavra específica (ENGENHARIA,MANUTENCAO, etc.)");
        tutorial.setFont(new Font("calibri",15));
        field = new TextField();
        btnGo = new Button("Consultar");
        btnExcel = new Button("Exportar para Excel");
        btnCheck = new Button("Inspecionar");
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        hId = new HBox();
        hId.setSpacing(15);
        hId.setAlignment(Pos.CENTER);
        hId.getChildren().addAll(tutorial,field);
        hOptions = new HBox();
        hOptions.setSpacing(15);
        hOptions.setAlignment(Pos.CENTER);
        hOptions.getChildren().addAll(btnExcel,btnCheck);
        root = new VBox();
        root.getChildren().addAll(menuBar,title,hId,btnGo,scrollPane,hOptions,progressBar,foot);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root,600,700);
        stage.setScene(scene);
        stage.setOpacity(0.85f);
        stage.setResizable(false);
        stage.show();

        //listenning
        btnGo.setOnAction(this);
        btnExcel.setOnAction(this);
        btnCheck.setOnAction(this);
    }

    private void processIdResult() {
        if(arrayListRes != null) {
            int len = arrayListRes.size();
            for (int i = 0; i < len; i++) {
                textArea.appendText(arrayListRes.get(i) + "\n");
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
        if(path != null && arrayListRes != null) {
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
