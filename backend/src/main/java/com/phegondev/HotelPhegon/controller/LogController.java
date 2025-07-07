package com.phegondev.HotelPhegon.controller;

import com.phegondev.HotelPhegon.entity.LoginLog;
import com.phegondev.HotelPhegon.repo.LoginLogRepository;
import com.phegondev.HotelPhegon.service.impl.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    private PdfGenerationService pdfGenerationService;

    public LogController(LoginLogRepository loginLogRepository, PdfGenerationService pdfGenerationService) {
        this.loginLogRepository = loginLogRepository;
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping("/pdf/{username}")
    public ResponseEntity<byte[]> generateLoginHistoryPdf(@PathVariable String username) {
        try {
            // 1. Buscar os dados do banco
            List<LoginLog> logs = loginLogRepository.findByUsernameOrderByTimestampDesc(username);

            // 2. Preparar o contexto com os dados para o Thymeleaf
            Context context = new Context();
            context.setVariable("logs", logs);
            context.setVariable("username", username);

            // 3. Gerar o PDF em um array de bytes
            byte[] pdfBytes = pdfGenerationService.generatePdfFromHtml("historico_login_template", context);

            // 4. Configurar os headers da resposta HTTP para download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Define o nome do arquivo para download
            String filename = "historico-login-" + username + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            // Tratar o erro adequadamente
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}