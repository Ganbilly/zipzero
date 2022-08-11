package com.ktds.zipzero.payment.service;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 이은성(2022-08-11)
 * 기능 : 페이지 맵퍼
 */

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Setter(onMethod_ = @Autowired)
    private PaymentMapper paymentMapper;
    
    @Value("${api_key}")
    private String secretKey;
    
    @Override
    public List<PaymentDTO> getPaymentList(long mid, int skip, int size) {
        List<PaymentDTO> paymentList = paymentMapper.getPage(mid, skip, size);
        return paymentList;
    }

    @Override
    public PaymentDTO getPaymentDetail(long pid) {
        PaymentDTO ppayment = paymentMapper.getPaymentById(pid);
        return ppayment;
    }

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 등록
    */
    @Override
    public void registPayment(PaymentDTO paymentDTO) {
        paymentMapper.registPayment(paymentDTO);
    }

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 이미지 url을 받아 인식하여 paymentDTO로 변환후 반환
    */
    @Override
    public PaymentDTO recImage(String imageFile) {
		String apiURL = "https://9bpsb8rl83.apigw.ntruss.com/custom/v1/17635/3f8a9f00642ae1ed5a37e05854e1ed8f7b295c6a7cf2b987b31dfa2a5740aec3/document/receipt";

		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setReadTimeout(30000);
			con.setRequestMethod("POST");
			String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("X-OCR-SECRET", secretKey);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			JSONObject image = new JSONObject();
			image.put("format", "jpg");
			image.put("name", "demo");
			JSONArray images = new JSONArray();
			images.put(image);
			json.put("images", images);
			String postParams = json.toString();

			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			long start = System.currentTimeMillis();
			File file = new File(imageFile);
			writeMultiPart(wr, postParams, file, boundary);
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
		IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(boundary).append("\r\n");
		sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
		sb.append(jsonMessage);
		sb.append("\r\n");

		out.write(sb.toString().getBytes("UTF-8"));
		out.flush();

		if (file != null && file.isFile()) {
			out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
			StringBuilder fileString = new StringBuilder();
			fileString
				.append("Content-Disposition:form-data; name=\"file\"; filename=");
			fileString.append("\"" + file.getName() + "\"\r\n");
			fileString.append("Content-Type: application/octet-stream\r\n\r\n");
			out.write(fileString.toString().getBytes("UTF-8"));
			out.flush();

			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[8192];
				int count;
				while ((count = fis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.write("\r\n".getBytes());
			}

			out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
		}
		out.flush();
	}
}
