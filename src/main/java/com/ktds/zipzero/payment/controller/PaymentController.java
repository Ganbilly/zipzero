package com.ktds.zipzero.payment.controller;

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.all.dto.TimeDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {



    private final PaymentService paymentService;

    @Value("${com.ktds.upload.path}")
    private String uploadPath;

    @Value("${com.ktds.api_key}")
    private String key;

    @GetMapping("/userlist")
    public String paymentList(Model model, @RequestParam(value = "mid") long mid,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("paymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<PaymentDTO> paymentList = paymentService.getPaymentList(mid, pageDTO.getSkip(), size);

        model.addAttribute("paymentList", paymentList);

        return "payment/userlist";
    }

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : /payment/regist로 접속하면 regist.html페이지 연결
     */
    @GetMapping("/regist")
    public String getPaymentRegist() {
        log.info("paymentRegist");
        return "payment/regist";
    }

    /*
     * 만든사람 : 이은성(2022-08-11)
     * 최종수정 : 이은성(2022-08-11)
     * 기능 : API에서 JSON 데이터를 받아서 regist페이지 입력칸에 채우는 기능
     */
    @PostMapping("/regist")
    public String PostPaymentRegist(Model model, PaymentDTO paymentDTO, TimeDTO timeDTO) {
        log.info("PostPaymentRegist");
        paymentDTO = paymentService.getPaymentDetail(1);
        timeDTO.setYear("2022");
        timeDTO.setMonth("08");
        timeDTO.setDate("22");
        timeDTO.setHour("12");
        timeDTO.setMin("22");
        timeDTO.setSec("12");
        log.info(timeDTO.toString());
        model.addAttribute("paymentDTO", paymentDTO);
        model.addAttribute("timeDTO", timeDTO);
        return "payment/regist";
    }

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : /payment/regist 페이지에서 영수증 등록
     */
    @PostMapping("/registadd")
    public String postPaymentRegistAdd(PaymentDTO paymentDTO, TimeDTO timeDTO) {

        LocalDateTime t = LocalDateTime.parse(timeDTO.getTime());
        paymentDTO.setPtime(t);
        paymentDTO.setPregdate(LocalDateTime.now());
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setPcheck(1);
        paymentDTO.setPcurstate(1L);
        paymentDTO.setPfinstate(1L);
        paymentDTO.setSid(1L);
        paymentDTO.setMid(1L);
        
        log.info(paymentDTO);
        paymentService.registPayment(paymentDTO);
        
        return "redirect:/payment/regist";

    }

    /*
     * 만든사람 : 이은성(2022-08-11)
     * 최종수정 : 이은성(2022-08-11)
     * 기능 : /payment/regist 페이지에서 이미지 등록시 api를 통해 객체로 반환 ( image -> json -> PaymentDTO )
     */
    @CrossOrigin
    @PostMapping( value = "/registrec", produces = {"application/json"})
    @ResponseBody
    public String postPamyentRegistRec(@RequestParam(value="receipt") MultipartFile file){
        
        // 파일 저장
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM/dd"));
        String folderPath = str.replace("/", File.separator);
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        File uploadPathFolder = new File(uploadPath, folderPath);

        
        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }


        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);

        log.info("-------------------------------------" + saveName);

        try {
            file.transferTo(savePath);
        } catch (IOException e){
            e.printStackTrace();
        }

        // API 호출
        String apiURL = "https://9bpsb8rl83.apigw.ntruss.com/custom/v1/17635/3f8a9f00642ae1ed5a37e05854e1ed8f7b295c6a7cf2b987b31dfa2a5740aec3/document/receipt";
		String secretKey = key;
		String imageFile = saveName;

        String result = null;

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
			image.put("name", saveName);
			JSONArray images = new JSONArray();
			images.put(image);
			json.put("images", images);
			String postParams = json.toString();

			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			long start = System.currentTimeMillis();
			File apifile = new File(imageFile);
			writeMultiPart(wr, postParams, apifile, boundary);
			wr.close();

            int responseCode = con.getResponseCode();
			BufferedReader br;
			DataInputStream din = null;
			if (responseCode == 200) {
				din = new DataInputStream(con.getInputStream());

			} else {
				din = new DataInputStream(con.getErrorStream());
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
            }finally {
                if(bos != null) try{ bos.close();}catch(Exception e){}
            }

		
            result = new String(bos.toByteArray(), "UTF-8"); 

            //log.info(result);

		} catch (Exception e) {
			System.out.println(e);
		}

        return result;
    }

    /*
     * 만든사람 : 이은성(2022-08-11)
     * 최종수정 : 이은성(2022-08-11)
     * 기능 : OCR api에서 사용하는 함수
     */
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

    /*
     * @GetMapping("/adminlist")
     * public String paymentAdminList(Model model, @RequestParam(value = "mid") long
     * mid, @RequestParam(value = "page", defaultValue = "1") int
     * page, @RequestParam(value = "size", defaultValue = "10") int size){
     * log.info("paymentList");
     * PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
     * List<PaymentDTO> paymentList = paymentService.getPaymentList(mid,
     * pageDTO.getSkip(), size);
     * 
     * model.addAttribute("paymentList", paymentList);
     * 
     * return "payment/adminlist";
     * }
     * /*
     * 
     * @GetMapping("/adminmanage")
     * public void paymentAdminManage(){
     * log.info("adminManage");
     * }
     */
    @GetMapping("/userdetail")
    public String paymentUserDetail(Model model, @RequestParam(value = "pid") long pid) {
        log.info("UserDetail");

        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/userdetail";
    }

    @GetMapping("/admindetail")
    public String paymentAdminDetail(Model model, @RequestParam(value = "pid") long pid) {
        log.info("AdminDetail");

        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/admindetail";
    }
    /*
     * @PostMapping("/modify")
     * public void paymentModify(){
     * log.info("Modify");
     * }
     * 
     * @PostMapping("/delete")
     * public void paymentDelete(){
     * log.info("Delete");
     * }
     * 
     * @GetMapping("/chart")
     * public void paymentChart(){
     * log.info("chart");
     * }
     */

}
