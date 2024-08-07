package com.example.jeecg_tools;

import cn.hutool.http.HttpResponse;
import com.example.jeecg_tools.common.BasePayload;
import com.example.jeecg_tools.entity.Result;
import com.example.jeecg_tools.util.ExppList;
import com.example.jeecg_tools.util.Tools;
import javafx.fxml.FXML;
import cn.hutool.http.HttpRequest;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import cn.hutool.http.GlobalHeaders;

import java.time.LocalDate;
import java.util.List;


public class HelloController {

    //Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private CheckBox CheckHEAD_;

    @FXML
    private TextArea FileContent_;

    @FXML
    private TextArea FileName_;

    @FXML
    private Button FileUpload_;

    @FXML
    private TextArea OutPath_;

    @FXML
    private TextArea URL_;

    @FXML
    private Button Vulcheck_;

    @FXML
    private TextArea INFO_;

    @FXML
    private TextArea XsContent_;

    @FXML
    private TextArea XsFilename_;

    @FXML
    private TextArea XsXsOut_;

    @FXML
    private Button Inject_;

    @FXML
    private TextArea HEAD_;

    @FXML
    private ComboBox<String> comboBox;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initialize() {
        XsFilename_.setText(LocalDate.now()+".zip");
        this.comboBox.setValue("ALL");
        this.comboBox.getItems().add("ALL");
        this.comboBox.getItems().addAll(ExppList.get_exp());
        this.CheckHEAD_.setSelected(false);
    }

    @FXML
    void Cookie_(MouseEvent event) {
        if(this.CheckHEAD_.isSelected()){
            String header = HEAD_.getText();
            if(!header.isEmpty()){
                String hea = header.trim();
                int index = hea.indexOf(":");
                if (index == -1) {
                    this.alert.setTitle("提示:");
                    this.alert.setHeaderText(null);
                    this.alert.setContentText("请求头格式错误！");
                    this.alert.showAndWait();
                    return;
                }
                String mapk = hea.substring(0,index);
                String mapv = hea.substring(index+1);
                GlobalHeaders.INSTANCE.clearHeaders();
                GlobalHeaders.INSTANCE.header(mapk,mapv);
            }
        }else{
            GlobalHeaders.INSTANCE.clearHeaders();
        }



    }

    @FXML
    void Inject(MouseEvent event) throws Exception {
        this.Cookie_(event);
        String url = URL_.getText();
        String filename = XsFilename_.getText();
        String payload = XsContent_.getText();
        BasePayload bp = Tools.getPayload("JEECG Xstream反序列化");
        Result vul = bp.Inject(url, filename, payload);
        if (vul.isRes()) {
            XsXsOut_.setText("[+] 已尝试注入，请访问！");
        } else {
            XsXsOut_.setText("[-] 注入失败！"+vul.getPayload()+vul.getVuln());
        }
    }

    @FXML
    void FileUpload(MouseEvent event) throws Exception {
        this.Cookie_(event);
        String url = URL_.getText();
        String version = comboBox.getSelectionModel().getSelectedItem();

        if (!version.contains("文件上传")){
            this.alert.setTitle("提示:");
            this.alert.setHeaderText(null);
            this.alert.setContentText("请选择上传类漏洞！");
            this.alert.showAndWait();
            return;
        }

        String filename = FileName_.getText();
        String filecontent = FileContent_.getText();
            BasePayload bp = Tools.getPayload(version);
            Result vul = bp.fileUpload(url, filename, filecontent);
            if (vul.isRes()) {
                OutPath_.setText("[+] 文件上传成功：" + url + "/" + vul.getVuln());
            } else {
                OutPath_.setText("[-] 文件上传失败！");
            }
    }

    @FXML
    void VulCheck(MouseEvent event) throws Exception {
        this.Cookie_(event);
        String url = URL_.getText();
        try {
            HttpRequest res = HttpRequest.get(url);
            HttpResponse execute = res.execute();
            System.out.println(execute.body());
        }catch (Exception e){
            INFO_.setText("[-] 访问：" + new String(URL_.getText())+"失败！\n\n" + e);
            return;
        }

        String version = comboBox.getSelectionModel().getSelectedItem();

        if(version == "ALL"){
            INFO_.setText("");
            List<String> explist = ExppList.get_exp();
            for (int i = 0; i < explist.size(); i++) {
                BasePayload bp = Tools.getPayload(explist.get(i));
                Result vul = bp.checkVUL(url);

                if(vul.isRes()){
                    INFO_.appendText("[+] 存在接口："+explist.get(i)+"，请尝试漏洞利用！\n\n访问URL："+vul.getPayload()+"\n\n"+"-------------------------------\n\n\n");
                }else{
                    INFO_.appendText("[-] 不存在漏洞："+explist.get(i)+"\n\n访问URL："+vul.getPayload()+"\n\n请尝试登录后利用！"+"\n\n"+"-------------------------------\n\n\n");
                }
            }
            return;
        }

        BasePayload bp = Tools.getPayload(version);
        Result vul = bp.checkVUL(url);
        if(vul.isRes()){
            INFO_.setText("[+] 存在接口："+version+"，请尝试漏洞利用！\n\n访问URL："+vul.getPayload()+"\n\n"+"返回包：" + vul.getVuln());
        }else{
            INFO_.setText("[-] 不存在漏洞："+version+"\n\n访问URL："+vul.getPayload()+"\n\n"+"返回包：" + vul.getVuln());
        }
    }


    @FXML
    void onCleanlog(MouseEvent event) {
        INFO_.setText("");
    }



}
