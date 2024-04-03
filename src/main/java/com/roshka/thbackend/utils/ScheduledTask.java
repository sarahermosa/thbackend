package com.roshka.thbackend.utils;

import com.roshka.thbackend.service.IBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    public void imprimirHolaMundo() {
        List<String> nombresBeneficios = beneficioService.obtenerNombresBeneficios();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("ferledesma352@gmail.com");
        email.setFrom("bootcampjava341@gmail.com");
        email.setSubject("Beneficios");
        email.setText("Lista De Beneficios:\n" + String.join("\n*", nombresBeneficios)+"\nNO RESPONDER A ESTE MENSAJE");
        javaMailSender.send(email);
    }
}
