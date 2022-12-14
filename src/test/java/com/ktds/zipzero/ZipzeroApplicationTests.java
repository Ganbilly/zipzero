package com.ktds.zipzero;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

@SpringBootTest
@Log4j2
class ZipzeroApplicationTests {

	@Test
	void jsontest(){
		
	}




	@Value("${com.ktds.api_key}")
	private String key;

	@Test
	void contextLoads() {
	}

	@Test
	public void apiTest() {
		String apiURL = "https://9bpsb8rl83.apigw.ntruss.com/custom/v1/17635/3f8a9f00642ae1ed5a37e05854e1ed8f7b295c6a7cf2b987b31dfa2a5740aec3/document/receipt";
		String secretKey = key;
		String imageFile = "C:\\zzz\\3.jpg";

		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
			DataInputStream din = null;
			if (responseCode == 200) {
				//br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				din = new DataInputStream(con.getInputStream());

			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024*8];

			try{
			while(true){

				int count = din.read(buffer);
				
				if(count == -1) { break;}

				bos.write(buffer,0,count);

			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		log.info(new String(bos.toByteArray()));

			// String inputLine;
			// StringBuffer response = new StringBuffer();
			// while ((inputLine = br.readUTF()) != null) {
			// 	response.append(inputLine);
			// }
			// br.close();

			//System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary)
			throws IOException {
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

	@Test
	public void testest(){
		String a = "123456";

		a = a.replaceFirst("1", "{\"image_url\":abc.jpg, ");
		log.info(a);
		
	}
}