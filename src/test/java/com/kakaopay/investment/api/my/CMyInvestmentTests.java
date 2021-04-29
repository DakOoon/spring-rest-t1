package com.kakaopay.investment.api.my;

import java.io.IOException;

import com.kakaopay.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CMyInvestmentTests extends InvestmentApplicationTests {

    @Test
    public void callCMyInvestmentGet() {
        // given
        System.out.println("CMyInvestmentTests: callCMyInvestmentGet");

        // when
        // OkHttpClient client = new OkHttpClient().newBuilder().build();
        // MediaType mediaType = MediaType.parse("application/json");
        // RequestBody body = RequestBody.create(
        //         "{\r\n\t\"header\":{\r\n\t\t\"service\": \"WORKFLOW.ENGINE.SFlowModelDownload\"\r\n\t},\r\n\t\"dto\":{\r\n\t\t\"FLOWNAME\":\"hah2a2\"                           // required\r\n\t}\r\n}",
        //         mediaType);
        // Request request = new Request.Builder().url("http://127.0.0.1:8080?").method("GET", body)
        //         .addHeader("Content-Type", "application/json").build();
        // try {
        //     Response response = client.newCall(request).execute();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        // then
        
    }
}