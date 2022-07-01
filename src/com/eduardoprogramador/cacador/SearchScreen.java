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

public class SearchScreen extends CommonTask implements EventHandler {
    //declare
    private Stage stage;
    private MenuBar menuBar;
    private Label foot, title, labelUF, labelID;
    private ComboBox<String> comboBoxUf;
    private TextField id;
    private Button btnGo, btnExcel, btnInspect;
    private HBox hSearch, hOptions;
    private TextArea textArea;
    private ScrollPane scrollPane;
    private VBox root;
    private Scene scene;
    private ArrayList<String> arrayListData;
    private ProgressBar progressBar;

    public SearchScreen(Stage stage, MenuBar menuBar, Label foot) {
        this.stage = stage;
        this.menuBar = menuBar;
        this.foot = foot;
        loadSearchUI();
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnGo)) {
            Runnable runnable = () -> {
                processSearchData();
            };
            new Thread(runnable).start();
        } else if(event.getTarget().equals(btnExcel)) {
            exportToExcel();
        } else if(event.getTarget().equals(btnInspect)) {
            inspectIt(CommonTask.SEARCH_TYPE);
        }
    }

    private void loadSearchUI() {
        progressBar = new ProgressBar();
        progressBar.setVisible(false);
        title = new Label("Busca por Ramo de Fornecimento");
        title.setFont(new Font("calibri",23));
        labelUF = new Label("Estado da Federação:");
        labelUF.setFont(new Font("calibri",15));
        labelID = new Label("ID de fornecimento:");
        labelID.setFont(new Font("calibri",15));
        comboBoxUf = new ComboBox<>();
        comboBoxUf.getItems().addAll("AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MT","MS","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SP","SE","TO");
        comboBoxUf.getSelectionModel().select(0);
        id = new TextField();
        btnGo = new Button("Pesquisar");
        btnExcel = new Button("Exportar para Excel");
        btnInspect = new Button("Inspecionar");
        hSearch = new HBox();
        hSearch.getChildren().addAll(labelUF,comboBoxUf,labelID,id);
        hSearch.setAlignment(Pos.CENTER);
        hSearch.setSpacing(15);
        hOptions = new HBox();
        hOptions.setAlignment(Pos.CENTER);
        hOptions.setSpacing(15);
        hOptions.getChildren().addAll(btnExcel,btnInspect);
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        root = new VBox();
        root.getChildren().addAll(menuBar,title,hSearch,btnGo,scrollPane,hOptions,progressBar,foot);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
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

    private void processSearchData() {
        progressBar.setVisible(true);
        String uf = comboBoxUf.getSelectionModel().getSelectedItem();
        String idNumber = id.getText();
        arrayListData = DataTask.getSearchData(uf,idNumber);
        int len = arrayListData.size();
        if(arrayListData != null) {
            for (int i = 0; i < len; i++) {
                textArea.appendText(arrayListData.get(i) + "\n");
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
        if(path != null && arrayListData != null) {
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
