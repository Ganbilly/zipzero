package com.ktds.zipzero.payment.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : mid로 직원이 등록한 영수증 목록을 가져옴
     */
    @GetMapping("/userlist")
    public String userPaymentList(Model model, @RequestParam(value = "mid") long mid,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentList");

        List<PaymentDTO> filterList = paymentService.getAllPaymentList(mid);
        PageDTO pageDTO = PageDTO.builder().page(page).size(10).total(filterList.size()).build();
        pageDTO.setPaging();

        model.addAttribute("user", customUser);
        model.addAttribute("mid", mid);
        model.addAttribute("paymentList", paymentService.getPaymentList(mid, pageDTO.getSkip(), size));
        model.addAttribute("page", pageDTO);

        return "payment/userlist";
    }

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 영수증 상세 페이지에서 수정 페이지로 이동
     */
    @PostMapping("/modify")
    public String paymentModify(Model model, @ModelAttribute("pid") long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentModify");

        model.addAttribute("user", customUser);
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/modify";
    }

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 영수증 수정 페이지에서 수정한 내용을 반영
     */
    @PostMapping("/modifyresult")
    public String paymentModifyResult(Model model, @ModelAttribute("paymentDTO") PaymentDTO paymentDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PaymentModifyResult");
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setSid(3L);
        paymentDTO.setPcheck(1);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("user", customUser);
        model.addAttribute("mid", paymentService.getPaymentDetail(paymentDTO.getPid()).getMid());

        return "redirect:userlist";
    }

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 영수증 상세 페이지에서 영수증을 삭제
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
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 영수증 전체 목록 조회 (검색 기능 포함)
     */
    @GetMapping("/adminlist")
    @PreAuthorize("hasRole('관리자')")
    public String adminPaymentList(Model model, @ModelAttribute("filterDTO") FilterDTO filterDTO, 
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUser customUser) {
        log.info("============== PaymentList : " + filterDTO);
        if(filterDTO.getMinptotalprice() == null) filterDTO.setMinptotalprice("");
        if(filterDTO.getMaxptotalprice() == null) filterDTO.setMaxptotalprice("");
        if(filterDTO.getMid() == null) filterDTO.setMid("");
        // if(filterDTO.getStartTime().getYear() < 1001) filterDTO.setStartTime(null);

        List<FilterDTO> filterList = paymentService.getAllPaymentFilter(filterDTO);
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).total(filterList.size()).build();
        pageDTO.setPaging();

        model.addAttribute("user", customUser);
        model.addAttribute("filter", paymentService.getPaymentFilterList(filterDTO, pageDTO.getSkip(), pageDTO.getSize()));
        model.addAttribute("page", pageDTO);

        return "payment/adminlist";
    }

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 검색 조건에 맞춰 검색한 결과
     */
    // @PostMapping("/adminlist")
    // @PreAuthorize("hasRole('관리자')")
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
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 승인 처리
     */
    @PostMapping("/adminsuccess")
    public String adminPaymentsuccess(Model model, long pid, @AuthenticationPrincipal CustomUser customUser) {
        log.info("AdminManage");
        log.info("============  pid : " + pid);
        PaymentDTO paymentDTO = paymentService.getPaymentDetail(pid);
        paymentDTO.setSid(1L);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("user", customUser);
        model.addAttribute("mid", paymentDTO.getMid());
        model.addAttribute("pid", pid);

        return "redirect:adminmanage";
    }

    

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 정문경(2022-08-18)
     * 기능 : /payment/regist로 접속하면 regist.html페이지 연결
     */
    @GetMapping("/regist")
    public String getPaymentRegist(Model model, @AuthenticationPrincipal CustomUser customUser) {
        log.info("paymentRegist");
        model.addAttribute("user", customUser);

        return "payment/regist";
    }

    /*
     * 만든사람 : 이은성(2022-08-11)
     * 최종수정 : 정문경(2022-08-18)
     * 기능 : API에서 JSON 데이터를 받아서 regist페이지 입력칸에 채우는 기능
     */
    @PostMapping("/regist")
    public String PostPaymentRegist(Model model, PaymentDTO paymentDTO, TimeDTO timeDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("PostPaymentRegist===================== ");
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
        model.addAttribute("user", customUser);
        return "payment/regist";
    }

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-16)
     * 기능 : /payment/regist 페이지에서 영수증 등록
     */

    @PostMapping("/registadd")
    public String postPaymentRegistAdd(Model model, PaymentDTO paymentDTO, TimeDTO timeDTO, @AuthenticationPrincipal CustomUser customUser) {

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
        model.addAttribute("user", customUser);

        return "redirect:/payment/regist";

    }

    /*
     * 만든사람 : 이은성(2022-08-11)
     * 최종수정 : 이은성(2022-08-11)
     * 기능 : /payment/regist 페이지에서 이미지 등록시 api를 통해 객체로 반환 ( image -> json -> PaymentDTO )
     */
    @CrossOrigin
    @PostMapping(value = "/registrec", produces = { "application/json" })
    @ResponseBody
    public String postPamyentRegistRec(@RequestParam(value = "receipt") MultipartFile file) {

        // 파일 저장
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

        // API 호출
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
     * 만든사람 : 이은성(2022-08-17)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 저장된 파일 표시
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
     * 만든 사람 : 김예림(2022-08-10)
     * 최종 수정 : 정문경(2022-08-18)
     * 기능 : 본인 소속의 모든 직원 영수증 내역 조회
     */
    @GetMapping("/adminmanage")
    @PreAuthorize("hasAnyRole('팀장', '담당', '본부장', '관리자')")
    public String adminlist(Model model, @RequestParam(value = "mid") long mid,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal CustomUser customUser) {
        log.info("adminManage");

        List<PaymentDTO> paymentList = paymentService.getAuthList(mid);
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).total(paymentList.size()).build();
        pageDTO.setPaging();

        model.addAttribute("mid", mid);
        model.addAttribute("adminpaymentList", paymentService.getAuthPage(mid, pageDTO.getSkip(), size));
        model.addAttribute("page", pageDTO);
        model.addAttribute("user", customUser);
 
        log.info(paymentService.getAuthList(mid).size() + "==========================" + paymentService.getAuthPage(mid, pageDTO.getSkip(), size).size() + "===================" + pageDTO.getPage());

        return "payment/adminmanage";
    }

    /*
     * 만든 사람 : 정문경 (2022-08-12)
     * 최종 수정 : 정문경 (2022-08-18)
     * 기능 : detail에서 작성한 모달의 결과를 저장하고 adminmanage 페이지 반환
     */
    @PostMapping("/adminmanage")
    @PreAuthorize("hasAnyRole('팀장', '담당', '본부장', '관리자')")
    public String adminManageComment(Model model, @ModelAttribute("commentDTO") CommentDTO commentDTO, @AuthenticationPrincipal CustomUser customUser) {
        log.info("adminManageComment");
        commentDTO.setCregdate(LocalDateTime.now());
        commentDTO.setCmoddate(LocalDateTime.now());
        commentDTO.setCcheck(1);
        commentDTO.setMid(paymentService.getMidByPid(commentDTO.getPid()));
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
     * 만든 사람 : 정문경 (2022-08-12)
     * 최종 수정 : 정문경 (2022-08-18)
     * 기능 : pid로 유저의 영수증 상세 페이지 조회
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
     * 만든 사람 : 정문경 (2022-08-12)
     * 최종 수정 : 정문경 (2022-08-18)
     * 기능 : pid로 관리자의 영수증 상세 페이지 조회
     */
    @GetMapping("/admindetail")
    @PreAuthorize("hasAnyRole('팀장', '담당', '본부장', '관리자')")
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
     * 만든 사람 : 정문경 (2022-08-16)
     * 최종 수정 : 정문경 (2022-08-18)
     * 기능 : csv download
     */
    @ResponseBody
    @GetMapping(value = "downloadcsv")
    @PreAuthorize("hasAnyRole('팀장', '담당', '본부장', '관리자')")
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
		
		data += "본부, 담당, 팀, 이름, 결제카드유형, 결제내역, 결제시각, 결제총액, 승인상태\n";
		
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

    /*
     * 만든 사람 : 정문경 (2022-08-16)
     * 최종 수정 : 정문경 (2022-08-16)
     * 기능 : 차트 (시각화)
     */

}
