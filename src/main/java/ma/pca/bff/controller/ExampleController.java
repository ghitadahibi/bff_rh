package ma.pca.bff.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ma.pca.bff.config.ExampleExceptionType;
import ma.pca.bff.config.Resources;
import ma.pca.bff.dto.ExamplePageableResponse;
import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.dto.ExampleResponse;
import ma.pca.bff.service.ExampleService;
import ma.pca.starter.web.exception.FunctionalRuntimeException;
import ma.pca.starter.web.rest.RequestDetails;
import ma.pca.starter.web.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.core.io.ByteArrayResource;
import java.io.IOException;
import java.util.*;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

@RestController
@RequestMapping(Resources.API + Resources.EXAMPLE)
@Slf4j
public class ExampleController {
    @Autowired
    ExampleService exampleService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    RestClient restClient;
    @GetMapping("/test-connection")
    public void testConnection() {
        // Vérifiez si la connexion est réussie en récupérant un document de la collection jobmatching
        List<Document> documents = mongoTemplate.findAll(Document.class, "jobmatching");
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
    @GetMapping
    public ExampleResponse getExample() {
        restClient.execute(
            RequestDetails.builder().path("http://localhost:8081/api/exception")
                .build(), HttpMethod.GET, null, ExampleResponse.class, null);
        throw new FunctionalRuntimeException(
            ExampleExceptionType.EXAMPLE_EXCEPTION, "Testingé Exception");
    }

    @GetMapping(path = "/page")
    public ExamplePageableResponse getExamplePage(
        @RequestParam(required = false)
        final Integer page) {
        return exampleService.examplePage(page == null ? 1 : page);
    }

    @PostMapping(path = "/gateway")
    public ExampleResponse gatewayExample(
        @RequestBody
        final ExampleRequest request) {
        return exampleService.processRequest(request);
    }

    @PostMapping
    public ExampleResponse postExample(
        @RequestBody
        final ExampleRequest request) {
        return ExampleResponse.builder().value("Testing").build();
    }
    @PostMapping("/uploadjoboffre")
    public ResponseEntity<String> uploadJobOffer(@RequestParam("joboffre_nom") String joboffer_nom,
                                                 @RequestParam("joboffre") MultipartFile joboffer) throws IOException {

        // Check if the file is empty or missing
        if (joboffer == null || joboffer.isEmpty()) {
            throw new IllegalArgumentException("Job offer file is empty or missing");
        }

        // Set the API endpoint URL
        String apiUrl = "http://localhost:8000/uploadjoboffre";

        // Create a RestTemplate object
        RestTemplate restTemplate = new RestTemplate();

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set the request body
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("joboffre_nom", joboffer_nom);
        requestBody.add("joboffre", new ByteArrayResource(joboffer.getBytes()) {
            @Override
            public String getFilename() {
                return joboffer.getOriginalFilename();
            }
        });
        System.out.println(joboffer_nom);
        // Create the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request to the FastAPI backend
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Return the response
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }






    @GetMapping("/jobmatching")
    @ResponseBody
    public String getJobMatching(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8000/jobmatching?page=" + page + "&size=" + size;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        //System.out.println(response.getBody());
        return response.getBody();
    }

    private void sendEmailToUser(String userEmail, String subject, String body) throws MessagingException {
        String yourGmailEmail = "kenzadahibipro@gmail.com"; // Remplacez "votre_email@gmail.com" par votre adresse e-mail Gmail réelle
        String yourGmailPassword = "rtfvuakokyjbtbok"; // Remplacez "votre_mot_de_passe" par votre mot de passe Gmail réel

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(userEmail); // Mettez l'adresse e-mail à laquelle vous souhaitez envoyer l'e-mail ici
        helper.setSubject(subject);
        helper.setText(body, true);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("kenzadahibipro@gmail.com"); // Utilisez votre adresse e-mail Gmail réelle ici
        mailSender.setPassword("rtfvuakokyjbtbok"); // Utilisez votre mot de passe Gmail réel ici

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        mailSender.send(message);
    }



    @PostMapping("/getselectedrows")
    @ResponseBody
    public String getSelectedRows(@RequestBody Map<String, Object> payload) throws IOException, MessagingException {
        List<Map<String, Object>> selectedRows = (List<Map<String, Object>>) payload.get("selectedRows");

        // Extract the email addresses and names from the selected rows
        List<Map<String, String>> recipients = new ArrayList<>();
        for (Map<String, Object> row : selectedRows) {
            String email = (String) row.get("email");
            String name = email.split("@")[0]; // Extract the name from the email address
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", email);
            recipient.put("name", name);
            recipients.add(recipient);
        }

        // Envoie des e-mails aux utilisateurs sélectionnés
        for (Map<String, String> recipient : recipients) {
            String email = recipient.get("email");
            String name = recipient.get("name");

            // Récupérez les informations de compétence et d'expérience de l'utilisateur
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));
            Document document = mongoTemplate.findOne(query, Document.class, "jobmatching");
            System.out.println(document.toJson());
            String competence = document.getString("comptence");
            System.out.println(competence);
            // Utilisez les informations de compétence et d'expérience pour générer un lien vers un test de LeetCode approprié
            String testUrl = getProblem(competence);

            String htmlContent = "<div style='background-color:#f8f9fa; border-radius:25px; padding:20px;'>" +
                    "<div style='background-color:white; border-radius:25px; padding:20px; margin-top:20px;'>" +
                    "<img src='https://media.licdn.com/dms/image/C510BAQGHxGm0jEqcaw/company-logo_200_200/0/1519911753747?e=2147483647&v=beta&t=9ce2gCmeiT2SBlNJhUadgszWIhDrDfG1xZqlK0Iwghw' alt='PCA Logo' style='display:block; margin:auto;'>" +
                    "<p>Bonjour " + name + "</p>" +
                    "<p>Nous avons le plaisir de vous informer que vous avez été présélectionné(e) pour passer le quiz de sélection des candidats pour les offres d'emploi postuler</p>" +
                    "<p>Vous êtes donc invité(e) à passer le test d'une durée de 200 min, vous avez deux jours pour le compléter.</p>" +
                    "<a href='" + testUrl + "'>" + testUrl + "</a>" +
                    "<p>Bien à vous.</p>" +
                    "<p>Système d'Information de PCA.</p>" +
                    "</div>" +
                    "</div>";
            System.out.println("failed");
            sendEmailToUser(email,"bravo",htmlContent);
        }

        return "Emails sent";
    }

    @GetMapping("/codeforces")
    public String getProblem(@RequestParam String tag) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://codeforces.com/api/problemset.problems?tags=" + tag;
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        JsonNode firstProblem = response.get("result").get("problems").get(0);
        int contestId = firstProblem.get("contestId").asInt();
        String index = firstProblem.get("index").asText();
        String redirectUrl = "https://codeforces.com/problemset/problem/" + contestId + "/" + index;
        return  redirectUrl;}










    @GetMapping("/joboffer")
    @ResponseBody
    public String getJoboffer( @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "5") Integer size) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8000/joboffer?page="+ page + "&size=" + size; // Replace with the URL of your FastAPI API
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    @DeleteMapping("/joboffers")
    @ResponseBody
    public String deleteAllJoboffers() {
        RestTemplate restTemplate = new RestTemplate();
        String deleteAllJoboffersUrl = "http://localhost:8000/deleteall";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(deleteAllJoboffersUrl, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }
    @DeleteMapping("/joboffers/{jobOfferName}")
    @ResponseBody
    public String deleteJobOfferById(@PathVariable String jobOfferName) {
        RestTemplate restTemplate = new RestTemplate();
        String deleteJobOfferUrl = "http://localhost:8000/joboffers/" + jobOfferName;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(deleteJobOfferUrl, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }
}
