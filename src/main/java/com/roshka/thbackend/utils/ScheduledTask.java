package com.roshka.thbackend.utils;

import com.roshka.thbackend.service.IBeneficio;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTask {


    @Autowired
    private IBeneficio beneficioService;

    @Autowired //INYECCION DE DEPENDENCIAS PARA EL EMAIL
    private JavaMailSender javaMailSender;
    //@Scheduled(cron = "0 0 12 1/15 * ?") //MANDA EL CORREO CADA 15 DIAS
    @Scheduled(fixedRate = 40000) // 10 seg
    public void imprimirHolaMundo() throws MessagingException, IOException {
        List<String> nombresBeneficios = beneficioService.obtenerNombresBeneficios();

        String ListHTML = "";
        int count = 1;
        for (String beneficio : nombresBeneficios)
        {
            String ptag = "<p style=\"font-size: 1rem; color:black;\">" + count + ". " + beneficio + "</p>";
            ListHTML += ptag;
            count++;
        }

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo("ferledesma352@gmail.com");
//        email.setFrom("bootcampjava341@gmail.com");
//        email.setSubject("Beneficios");
//        email.setText("Lista De Beneficios:\n" + String.join("\n*", nombresBeneficios)+"\nNO RESPONDER A ESTE MENSAJE");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(message, true);

        email.setTo("adalmontania@gmail.com");
        email.setFrom("bootcampjava341@gmail.com");
        email.setSubject("Beneficios");

        String htmlFilePath = "src/main/resources/templates/schedule-task-template.html";
        String logoUrl = "https://i.postimg.cc/rpYs8GHX/roshka-logo-white.png"; // Replace with the actual URL of your image

        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
        htmlContent = htmlContent.replace("{lista}", ListHTML);

        htmlContent = htmlContent.replace("{logo}", logoUrl);

        email.setText(htmlContent, true);
        javaMailSender.send(message);
    }
}
