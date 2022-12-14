package com.ktds.zipzero.payment.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.all.dto.TimeDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.service.CommentService;
import com.ktds.zipzero.payment.dto.FilterDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;
import com.ktds.zipzero.security.domain.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

    private final PaymentService paymentService;

    private final CommentService commentService;

    @Value("${com.ktds.upload.path}")
    private String uploadPath;

    @Value("${com.ktds.api_key}")
    private String key;

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : mid??? ????????? ????????? ????????? ????????? ?????????
     */
    @GetMapping("/userlist")
    public String userPaymentList(Model model, @RequestParam(value = "mid") long mid,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentList");
        
        if(customUser.getMember().getMid() == mid) {
            List<PaymentDTO> filterList = paymentService.getAllPaymentList(mid);
            int listsize = filterList.size();
            PageDTO pageDTO = PageDTO.builder().page(page).size(10).total(listsize == 0 ? 1 : listsize).build();
            pageDTO.setPaging();
    
            model.addAttribute("user", customUser);
            model.addAttribute("mid", mid);
            model.addAttribute("paymentList", paymentService.getPaymentList(mid, pageDTO.getSkip(), size));
            model.addAttribute("page", pageDTO);

            return "payment/userlist";
        } else {
            return "deniedMid";
        }
    }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ????????? ?????? ??????????????? ?????? ???????????? ??????
     */
    @PostMapping("/modify")
    public String paymentModify(Model model, @ModelAttribute("pid") long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentModify");

        model.addAttribute("user", customUser);
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/modify";
    }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ????????? ?????? ??????????????? ????????? ????????? ??????
     */
    @PostMapping("/modifyresult")
    public String paymentModifyResult(Model model, @ModelAttribute("paymentDTO") PaymentDTO paymentDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentModifyResult");
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setPcurstate(2L);
        paymentDTO.setSid(3L);
        paymentDTO.setPcheck(1);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("user", customUser);
        model.addAttribute("mid", paymentService.getPaymentDetail(paymentDTO.getPid()).getMid());

        return "redirect:userlist";
    }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ????????? ?????? ??????????????? ???????????? ??????
     */
    @PostMapping("/delete")
    public String paymentDelete(Model model, long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentDelete");
        PaymentDTO paymentDTO = paymentService.getPaymentDetail(pid);
        paymentDTO.setPcheck(0);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("user", customUser);
        model.addAttribute("mid", paymentDTO.getMid());

        return "redirect:userlist";
    }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ????????? ?????? ?????? ?????? (?????? ?????? ??????)
     */
    @GetMapping("/adminlist")
    @PreAuthorize("hasRole('?????????')")
    public String adminPaymentList(Model model, @ModelAttribute("filterDTO") FilterDTO filterDTO, 
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUser customUser) {
        log.info("============== PaymentList : " + filterDTO);
        if(filterDTO.getMinptotalprice() == null) filterDTO.setMinptotalprice("");
        if(filterDTO.getMaxptotalprice() == null) filterDTO.setMaxptotalprice("");
        if(filterDTO.getMid() == null) filterDTO.setMid("");

        List<FilterDTO> filterList = paymentService.getAllPaymentFilter(filterDTO);
        int listsize = filterList.size();
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).total(listsize == 0 ? 1 : listsize).build();
        pageDTO.setPaging();

        model.addAttribute("user", customUser);
        model.addAttribute("filter", paymentService.getPaymentFilterList(filterDTO, pageDTO.getSkip(), pageDTO.getSize()));
        model.addAttribute("page", pageDTO);

        return "payment/adminlist";
    }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ?????? ????????? ?????? ????????? ??????
     */
    // @PostMapping("/adminlist")
    // @PreAuthorize("hasRole('?????????')")
    // public String adminPaymentListFilter(Model model, @ModelAttribute("filterDTO") FilterDTO filterDTO,
    //         @RequestParam(value = "mid") long mid,
    //         @RequestParam(value = "page", defaultValue = "1") int page,
    //         @RequestParam(value = "size", defaultValue = "10") int size,
    //         @AuthenticationPrincipal CustomUser customUser) {
    //     log.info("==================== PaymentListFilter : " + filterDTO);

    //     filterDTO.setMid(Long.toString(mid));
    //     List<FilterDTO> filterList = paymentService.getAllPaymentFilter(filterDTO);
    //     PageDTO pageDTO = PageDTO.builder().page(page).size(size).total(filterList.size()).build();
    //     pageDTO.setPaging();

    //     model.addAttribute("user", customUser);
    //     model.addAttribute("mid", mid);
    //     model.addAttribute("filter", paymentService.getPaymentFilterList(filterDTO, pageDTO.getSkip(), pageDTO.getSize()));
    //     model.addAttribute("page", pageDTO);

    //     return "payment/adminlist";
    // }

    /*
     * ???????????? : ?????????(2022-08-12)
     * ???????????? : ?????????(2022-08-12)
     * ?????? : ?????? ??????
     */
    @PostMapping("/adminsuccess")
    public String adminPaymentsuccess(Model model, long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("AdminManage");
        log.info("============  pid : " + pid);
        PaymentDTO paymentDTO = paymentService.getPaymentDetail(pid);
        if(paymentDTO.getPcurstate() == paymentDTO.getPfinstate()){
            paymentDTO.setSid(1L);
        }
        else{
            paymentDTO.setPcurstate(paymentDTO.getPcurstate() + 1);
        }
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("user", customUser);
        model.addAttribute("mid", paymentDTO.getMid());
        model.addAttribute("pid", pid);

        return "redirect:adminmanage";
    }

    

    /*
     * ???????????? : ?????????(2022-08-10)
     * ???????????? : ?????????(2022-08-18)
     * ?????? : /payment/regist??? ???????????? regist.html????????? ??????
     */
    @GetMapping("/regist")
    public String getPaymentRegist(Model model, @AuthenticationPrincipal CustomUser customUser) {
        log.info("paymentRegist");
        model.addAttribute("user", customUser);

        return "payment/regist";
    }

    /*
     * ???????????? : ?????????(2022-08-11)
     * ???????????? : ?????????(2022-08-18)
     * ?????? : API?????? JSON ???????????? ????????? regist????????? ???????????? ????????? ??????
     */
    @PostMapping("/regist")
    public String PostPaymentRegist(Model model, PaymentDTO paymentDTO, TimeDTO timeDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PostPaymentRegist===================== ");
        paymentDTO = paymentService.getPaymentDetail(customUser.getMember().getMid());
        timeDTO.setYear(String.valueOf(LocalDateTime.now().getYear()));
        timeDTO.setMonth(String.valueOf(LocalDateTime.now().getMonth()));
        timeDTO.setDate(String.valueOf(LocalDateTime.now().getDayOfMonth()));
        timeDTO.setHour(String.valueOf(LocalDateTime.now().getHour()));
        timeDTO.setMin(String.valueOf(LocalDateTime.now().getMinute()));
        timeDTO.setSec(String.valueOf(LocalDateTime.now().getSecond()));
        log.info(timeDTO.toString());

        model.addAttribute("paymentDTO", paymentDTO);
        model.addAttribute("timeDTO", timeDTO);
        model.addAttribute("user", customUser);
        return "payment/regist";
    }

    /*
     * ???????????? : ?????????(2022-08-10)
     * ???????????? : ?????????(2022-08-16)
     * ?????? : /payment/regist ??????????????? ????????? ??????
     */

    @PostMapping("/registadd")
    public String postPaymentRegistAdd(Model model, PaymentDTO paymentDTO, TimeDTO timeDTO, @AuthenticationPrincipal CustomUser customUser) {

        LocalDateTime t = LocalDateTime.parse(timeDTO.getTime());
        paymentDTO.setPtime(t);
        paymentDTO.setPregdate(LocalDateTime.now());
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setPcheck(1);
        paymentDTO.setPcurstate(2L);
        paymentDTO.setSid(3L); // ????????????
        paymentDTO.setMid(customUser.getMember().getMid());

        log.info(paymentDTO);
        paymentService.registPayment(paymentDTO);
        model.addAttribute("user", customUser);

        return "redirect:/payment/userlist?mid=" + customUser.getMember().getMid();

    }

    /*
     * ???????????? : ?????????(2022-08-11)
     * ???????????? : ?????????(2022-08-11)
     * ?????? : /payment/regist ??????????????? ????????? ????????? api??? ?????? ????????? ?????? ( image -> json -> PaymentDTO )
     */
    @CrossOrigin
    @PostMapping(value = "/registrec", produces = { "application/json" })
    @ResponseBody
    public String postPamyentRegistRec(@RequestParam(value = "receipt") MultipartFile file) {

        // ?????? ??????
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM/dd"));
        String folderPath = str.replace("/", File.separator);
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }

        String saveName = uuid + "_" + fileName;
        Path savePath = Paths.get(uploadPath + File.separator + folderPath + File.separator + saveName);

        log.info("-------------------------------------" + saveName);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // API ??????
        String apiURL = "https://9bpsb8rl83.apigw.ntruss.com/custom/v1/17635/3f8a9f00642ae1ed5a37e05854e1ed8f7b295c6a7cf2b987b31dfa2a5740aec3/document/receipt";
        String secretKey = key;
        String imageFile = uploadPath +  File.separator + folderPath + File.separator + saveName;

        String result = null;

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

            byte[] buffer = new byte[1024 * 8];

            try {
                while (true) {

                    int count = din.read(buffer);

                    if (count == -1) {
                        break;
                    }

                    bos.write(buffer, 0, count);

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null)
                    try {
                        bos.close();
                    } catch (Exception e) {
                    }
            }

            result = new String(bos.toByteArray(), "UTF-8");

            // log.info(result);

        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    /*
     * ???????????? : ?????????(2022-08-11)
     * ???????????? : ?????????(2022-08-11)
     * ?????? : OCR api?????? ???????????? ??????
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
     * ???????????? : ?????????(2022-08-17)
     * ???????????? : ?????????(2022-08-17)
     * ?????? : ????????? ?????? ??????
     */
    @GetMapping("/view/{pathName1}/{pathName2}/{fileName}")
    public ResponseEntity<Resource> viewFileGET(
            @PathVariable String pathName1,
            @PathVariable String pathName2,
            @PathVariable String fileName){
        String path = uploadPath + File.separator + pathName1 + File.separator + pathName2 + File.separator + fileName;
        // String[] array = fileName.split("\\");
        // for (String str : array) {
        //     path += File.separator + str; 
        //     log.info(str);
        // }
        Resource resource = new FileSystemResource(path);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /*
     * ?????? ?????? : ?????????(2022-08-10)
     * ?????? ?????? : ?????????(2022-08-18)
     * ?????? : ?????? ????????? ?????? ?????? ????????? ?????? ??????
     */
    @GetMapping("/adminmanage")
    @PreAuthorize("hasAnyRole('??????', '??????', '?????????', '?????????')")
    public String adminlist(Model model, @RequestParam(value = "mid") long mid,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal CustomUser customUser) {
        log.info("adminManage");

        List<PaymentDTO> paymentList = paymentService.getAuthList(mid);
        int listsize = paymentList.size();
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).total(listsize == 0 ? 1 : listsize).build();
        pageDTO.setPaging();

        model.addAttribute("mid", mid);
        model.addAttribute("adminpaymentList", paymentService.getAuthPage(mid, pageDTO.getSkip(), size));
        model.addAttribute("page", pageDTO);
        model.addAttribute("user", customUser);
 
        log.info(paymentService.getAuthList(mid).size() + "==========================" + paymentService.getAuthPage(mid, pageDTO.getSkip(), size).size() + "===================" + pageDTO.getPage());

        return "payment/adminmanage";
    }

    /*
     * ?????? ?????? : ????????? (2022-08-12)
     * ?????? ?????? : ????????? (2022-08-18)
     * ?????? : detail?????? ????????? ????????? ????????? ???????????? adminmanage ????????? ??????(?????? ??????)
     */
    @PostMapping("/adminmanage")
    @PreAuthorize("hasAnyRole('??????', '??????', '?????????', '?????????')")
    public String adminManageComment(Model model, @ModelAttribute("commentDTO") CommentDTO commentDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("adminManageComment");
        commentDTO.setCregdate(LocalDateTime.now());
        commentDTO.setCmoddate(LocalDateTime.now());
        commentDTO.setCcheck(1);
        commentDTO.setMid(customUser.getMember().getMid());
        commentService.registComment(commentDTO);

        PaymentDTO paymentDTO = paymentService.getPaymentDetail(commentDTO.getPid());
        paymentDTO.setSid(2L);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("adminpaymentList", paymentService.getAuthList(commentDTO.getMid()));
        model.addAttribute("pid", commentDTO.getPid());
        model.addAttribute("user", customUser);

        return "redirect:admindetail";
    }

    /*
     * ?????? ?????? : ????????? (2022-08-12)
     * ?????? ?????? : ????????? (2022-08-18)
     * ?????? : pid??? ????????? ????????? ?????? ????????? ??????
     */
    @GetMapping("/userdetail")
    public String userDetail(Model model, @RequestParam(value = "pid") long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("UserDetail");

        model.addAttribute("payment", paymentService.getPaymentDetail(pid));
        model.addAttribute("comments", commentService.getCommentsByPid(pid));
        model.addAttribute("user", customUser);
        log.info(commentService.getCommentsByPid(pid));

        return "payment/userdetail";
    }

    /*
     * ?????? ?????? : ????????? (2022-08-12)
     * ?????? ?????? : ????????? (2022-08-18)
     * ?????? : pid??? ???????????? ????????? ?????? ????????? ??????
     */
    @GetMapping("/admindetail")
    @PreAuthorize("hasAnyRole('??????', '??????', '?????????', '?????????')")
    public String adminDetail(Model model, @RequestParam(value = "pid") long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("AdminDetail-------------------------------------");
        
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));
        model.addAttribute("comments", commentService.getCommentsByPid(pid));
        model.addAttribute("user", customUser);
        log.info("result-------------------------------------");
        log.info(commentService.getCommentsByPid(pid));

        return "payment/admindetail";
    }
    
       /*
     * ?????? ?????? : ????????? (2022-08-17)
     * ?????? ?????? : ????????? (2022-08-19)
     * ?????? : ?????? ?????? ?????? ?????? ??? ???????????? ??????
     */
    
    @GetMapping("/chartJSON")
    @ResponseBody
    @PreAuthorize("hasAnyRole('?????????')")
    public String drawChart(Model model,PaymentDTO paymentDTO) {
        List<PaymentDTO> chartList = paymentService.getHqBarChartData( paymentDTO);
        List<PaymentDTO> chartList2 = paymentService.getDeptBarChartData( paymentDTO);
        List<PaymentDTO> chartList3 = paymentService.getHqPieChartData( paymentDTO);
        List<PaymentDTO> chartList4 = paymentService.getDeptPieChartData( paymentDTO);
        List<PaymentDTO> chartList5 = paymentService.getTeamPieChartData( paymentDTO);


        Gson gson = new Gson();
        JsonArray jArray = new JsonArray();
        JsonArray jArray2 = new JsonArray();
        JsonArray jArray3 = new JsonArray();
        JsonArray jArray4 = new JsonArray();
        JsonArray jArray5 = new JsonArray();
        JsonArray jArrayfinal = new JsonArray();
        

        Iterator<PaymentDTO> it = chartList.iterator();
        Iterator<PaymentDTO> it2 = chartList2.iterator();
        Iterator<PaymentDTO> it3 = chartList3.iterator();
        Iterator<PaymentDTO> it4 = chartList4.iterator();
        Iterator<PaymentDTO> it5 = chartList5.iterator();
        
        while(it.hasNext()) {
            PaymentDTO ls = it.next();
            JsonObject object = new JsonObject();
            String ptypename = ls.getPtypename();
            int cnt = ls.getCnt();
            
            object.addProperty("type",ptypename);
            object.addProperty("count", cnt);
            jArray.add(object);
        }
        while(it2.hasNext()) {
            PaymentDTO ls2 = it2.next();
            JsonObject object = new JsonObject();
            String ptypename = ls2.getPtypename();
            int cnt = ls2.getCnt();
            
            object.addProperty("type",ptypename);
            object.addProperty("count", cnt);
            jArray2.add(object);
        }
        while(it3.hasNext()) {
            PaymentDTO ls3 = it3.next();
            JsonObject object = new JsonObject();
            Long hqid= ls3.getHqid();
            Long pt=ls3.getPt();
            
            object.addProperty("hqid",hqid);
            object.addProperty("totalprice", pt);
            jArray3.add(object);
        }
        while(it4.hasNext()) {
            PaymentDTO ls4 = it4.next();
            JsonObject object = new JsonObject();
            Long deptid= ls4.getDeptid();
            Long pt=ls4.getPt();
            
            object.addProperty("deptid",deptid);
            object.addProperty("totalprice", pt);
            jArray4.add(object);
        }
        while(it5.hasNext()) {
            PaymentDTO ls5 = it5.next();
            JsonObject object = new JsonObject();
            Long teamid= ls5.getTeamid();
            Long pt=ls5.getPt();
            
            object.addProperty("teamid",teamid);
            object.addProperty("totalprice", pt);
            jArray5.add(object);
        }
        jArrayfinal.add(jArray);
        jArrayfinal.add(jArray2);
        jArrayfinal.add(jArray3);
        jArrayfinal.add(jArray4);
        jArrayfinal.add(jArray5);
        
        log.info(jArrayfinal);
        String json = gson.toJson(jArrayfinal);
        model.addAttribute("json", json);
        

        return json;
    }


    @GetMapping("/chart")
    @PreAuthorize("hasAnyRole('?????????')")
    public String chart(Model model, @AuthenticationPrincipal CustomUser customUser){
        
        model.addAttribute("user", customUser);
        return "payment/chart";
    }
    

    /*
     * ?????? ?????? : ????????? (2022-08-16)
     * ?????? ?????? : ????????? (2022-08-18)
     * ?????? : csv download
     */
    @ResponseBody
    @GetMapping(value = "downloadcsv")
    @PreAuthorize("hasAnyRole('??????', '??????', '?????????', '?????????')")
    public ResponseEntity<String> downloadCSV(@ModelAttribute("filterDTO") FilterDTO filterDTO, @AuthenticationPrincipal CustomUser customUser) {
		log.info("downloadCSV");
		
		List<FilterDTO> filterList = paymentService.getPaymentFilterList(filterDTO, 0, 100000);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=MS949");
		header.add("Content-Disposition", "attachment; filename=\""+LocalDate.now()+".csv"+"\"");
				
		return new ResponseEntity<String>(setCSVContent(filterList), header, HttpStatus.CREATED);
	}

    public String setCSVContent(List<FilterDTO> filterList) {
		String data = "";
		
		data += "??????, ??????, ???, ??????, ??????????????????, ????????????, ????????????, ????????????, ????????????\n";
		
		for (int i=0; i<filterList.size(); i++) {
			data += filterList.get(i).getHq() + ",";
			data += filterList.get(i).getDept() + ",";
			data += filterList.get(i).getTeam() + ",";
			data += filterList.get(i).getMname() + ",";
			data += filterList.get(i).getCardType() + ",";
			data += filterList.get(i).getPname() + ",";
			data += filterList.get(i).getPtime() + ",";
			data += filterList.get(i).getPtotalprice() + ",";
			data += filterList.get(i).getSnameBySid() + "\n";
		}
		
		return data;
	}


}
