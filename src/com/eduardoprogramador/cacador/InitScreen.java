package com.eduardoprogramador.cacador;

/*
 * Copyright 2022. Eduardo Programador
 * www.eduardoprogramador.com
 * consultoria@eduardoprogramador.com
 *
 * Todos os direitos reservados
 * */

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InitScreen extends Application implements EventHandler {
    private Stage stage;
    private Scene scene;
    private VBox root;
    private MenuBar menuBar;
    private Menu menuOption, menuAbout;
    private MenuItem itemId, itemRamo,itemNome,itemUasg,itemAbout,itemMore;
    private ImageView imageView;
    private Image image;
    private Label labelCacador, labelFoot;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        loadInitUI();
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(itemId)) {
            new LineScreen(stage,menuBar,labelFoot);
        } else if(event.getTarget().equals(itemRamo)) {
            new SearchScreen(stage,menuBar,labelFoot);
        } else if(event.getTarget().equals(itemNome)) {
            new NameScreen(stage,menuBar,labelFoot);
        } else if(event.getTarget().equals(itemUasg)) {
            new UasgScreen(stage,menuBar,labelFoot);
        } else if(event.getTarget().equals(itemAbout)) {
            CommonTask.showDialog(CommonTask.INFORMATION,"Sobre o Caçador ComprasNET","Detalhes",
                    "Caçador ComprasNET é um software direcionado aos compradores públicos que retorna " +
                            "dados de fornecedores por ramo de fornecimento, unidade federativa e parte da " +
                            "razão social, ajudando, assim, os processos de aquisições. O programa é inteiramente " +
                            "escrito na linguagem java e absorve informações mediante a utilização de algoritmos " +
                            "próprios, com a utilização de dados abertos do Governo Federal. Atualmente, existem " +
                            "duas versões do programa: Desktop e Android, esta última já disponível na PlayStore. " +
                            "A versão para dispositivos IOS também está em desenvolvimento e será disponibilizada " +
                            "oportunamente.\n\nAutor: Eduardo Programador\nSite: www.eduardoprogramador.com\n" +
                            "E-mail: consultoria@eduardoprogramador.com\nFone: (81) 98860-0704\nDesenvolvedor " +
                            "de Software e Analista de Dados");
        } else if(event.getTarget().equals(itemMore)) {
            CommonTask.openPage("www.eduardoprogramador.com/softwares.html");
        }
    }

    private void loadInitUI() {
        root = new VBox();
        menuBar = new MenuBar();
        menuOption = new Menu("Opções");
        menuAbout = new Menu("Sobre");
        itemId = new MenuItem("Consultar ID de Fornecimento");
        itemRamo = new MenuItem("Consultar por Ramo");
        itemNome = new MenuItem("Consultar por Nome");
        itemUasg = new MenuItem("Consultar UASG");
        itemAbout = new MenuItem("Sobre o Caçador");
        itemMore = new MenuItem("Mais Softwares");
        menuOption.getItems().addAll(itemId,itemRamo,itemNome,itemUasg);
        menuAbout.getItems().addAll(itemAbout,itemMore);
        menuBar.getMenus().addAll(menuOption,menuAbout);
        image = new Image("https://eduardoprogramador.com/img/icos/cacador.png");
        imageView = new ImageView(image);
        labelCacador = new Label("Caçador ComprasNET");
        labelCacador.setFont(new Font("calibri",17));
        labelFoot = new Label("Copyright 2022. Eduardo Programador. \n      www.eduardoprogramador.com");
        labelFoot.setFont(new Font("calibri",17));
        root.getChildren().addAll(menuBar,imageView,labelCacador,labelFoot);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root,600,700);
        stage.setScene(scene);
        stage.setTitle("Caçador ComprasNET");
        stage.getIcons().add(new Image("https://eduardoprogramador.com/img/icos/cacador.png"));
        stage.setOpacity(1);
        stage.setResizable(false);
        stage.show();

        //listenning
        itemId.setOnAction(this);
        itemRamo.setOnAction(this);
        itemNome.setOnAction(this);
        itemUasg.setOnAction(this);
        itemAbout.setOnAction(this);
        itemMore.setOnAction(this);
    }
}
