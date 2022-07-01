package com.eduardoprogramador.cacador;

/*
* Copyright 2022. Eduardo Programador
* www.eduardoprogramador.com
* consultoria@eduardoprogramador.com
*
* Todos os direitos reservados
* */

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class CommonTask {
    //declare
    public static final Alert.AlertType INFORMATION = Alert.AlertType.INFORMATION;
    public static final Alert.AlertType ERROR = Alert.AlertType.ERROR;
    public static final Alert.AlertType WARNING = Alert.AlertType.WARNING;
    public static final int LINE_TYPE = 1;
    public static final int SEARCH_TYPE = 2;
    public static final int NAME_TYPE = 3;
    public static final int UASG_TYPE = 4;

    public static void showDialog(Alert.AlertType alertType, String title, String header, String msg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.show();
    }

    public static void openPage(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url.replaceAll(" ","+")));
        } catch (IOException ex) {
            showDialog(CommonTask.ERROR,"Erro","Erro de Inspeção","Não foi possível proceder à inspeção.");
        }
    }

    public static boolean exportExcel(String path,String content) {
        try {
            File file = new File(path + ".csv");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static String putFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Arquivo (.csv)","*.csv"));
        fileChooser.setTitle("Salvar Como");
        File file = fileChooser.showSaveDialog(stage);
        return file.getPath();
    }

    public static void inspectIt(int type) {
        TextInputDialog textInputDialog = new TextInputDialog();
        String query = null;
        switch (type) {
            case LINE_TYPE:
                textInputDialog.setHeaderText("Informe um nome de um tipo de objeto");
                textInputDialog.showAndWait();
                query = "www.google.com?q=" + textInputDialog.getEditor().getText();
                openPage(query);
                break;

            case SEARCH_TYPE:
                textInputDialog.setHeaderText("Informe o nome do fornecedor");
                textInputDialog.showAndWait();
                query = "www.google.com?q=" + textInputDialog.getEditor().getText();
                openPage(query);
                break;

            case NAME_TYPE:
                textInputDialog.setHeaderText("Informe o nome do fornecedor");
                textInputDialog.showAndWait();
                query = "www.google.com?q=" + textInputDialog.getEditor().getText();
                openPage(query);
                break;

            case UASG_TYPE:
                textInputDialog.setHeaderText("Informe o nome da UASG");
                textInputDialog.showAndWait();
                query = "www.google.com?q=" + textInputDialog.getEditor().getText();
                openPage(query);
                break;
        }

    }
}
