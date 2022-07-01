package com.eduardoprogramador.cacador;

/*
 * Copyright 2022. Eduardo Programador
 * www.eduardoprogramador.com
 * consultoria@eduardoprogramador.com
 *
 * Todos os direitos reservados
 * */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataTask {
    //declare

    public static ArrayList<String> getIdData(String key) {
        ArrayList<String> res = new ArrayList<>();
        int count = -1;
        try {
            URL url = new URL("http","compras.dados.gov.br",80,"/fornecedores/v1/linhas_fornecimento.csv?nome=" + key);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
                if(count == 0)
                    continue;

                res.add(line);
            }

            httpURLConnection.disconnect();
            return res;

        } catch (Exception ex) {
            return null;
        }
    }

    public static ArrayList<String> getSearchData(String uf, String id) {
        ArrayList<String> res = new ArrayList<>();
        int count = -1;
        try {
            URL url = new URL("http","compras.dados.gov.br",80,"/fornecedores/v1/fornecedores.csv?id_linha_fornecimento=" + id + "&uf=" + uf);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
                if(count == 0)
                    continue;

                res.add(line);
            }

            httpURLConnection.disconnect();
            return res;

        } catch (Exception ex) {
            return null;
        }
    }

    public static ArrayList<String> getNameData(String name) {
        ArrayList<String> res = new ArrayList<>();
        int count = -1;
        try {
            URL url = new URL("http","compras.dados.gov.br",80,"/fornecedores/v1/fornecedores.csv?nome=" + name.replaceAll(" ","+"));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
                if(count == 0)
                    continue;

                res.add(line);
            }

            httpURLConnection.disconnect();
            return res;

        } catch (Exception ex) {
            return null;
        }
    }

    public static ArrayList<String> getUasgData(String uasg) {
        ArrayList<String> res = new ArrayList<>();
        int count = -1;
        try {
            URL url = new URL("http","compras.dados.gov.br",80,"/fornecedores/v1/uasgs.csv?nome=" + uasg.replaceAll(" ","+"));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
                if(count == 0)
                    continue;

                res.add(line);
            }

            httpURLConnection.disconnect();
            return res;

        } catch (Exception ex) {
            return null;
        }
    }

}
