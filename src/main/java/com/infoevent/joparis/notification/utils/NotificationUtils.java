package com.infoevent.joparis.notification.utils;


import com.infoevent.joparis.notification.entities.NotificationRequest;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

public class NotificationUtils {
    public static byte[] createTicketPdf(NotificationRequest request) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            if (request.getUser() != null) {
                document.add(new Paragraph("Nom Complet: " + request.getUser().getFirstName() + " " + request.getUser().getLastName()));
            }
            if (request.getEvent() != null) {
                document.add(new Paragraph("Evenement: " + request.getEvent().getName()));
                document.add(new Paragraph("Date et heure: " + request.getEvent().getDate() + " " + request.getEvent().getStartTime()));
            }
            if (request.getOfferType() != null) {
                document.add(new Paragraph("Type de billet: " + request.getOfferType().getDescription()));
            }
            if (request.getPrice() != null) {
                document.add(new Paragraph("Prix: " + request.getPrice()));
            }
            if (request.getVenue() != null) {
                document.add(new Paragraph("Lieu: " + request.getVenue().getName()));
                document.add(new Paragraph("Adresse: " + request.getVenue().getLocation().toString()));
            }

            if (request.getQrCode() != null && request.getQrCode().length > 0) {
                Image qrCodeImage = new Image(ImageDataFactory.create(request.getQrCode()));
                document.add(qrCodeImage);
            }

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String constructWelcomeEmailBody(NotificationRequest request) {
        String firstName = request.getUser().getFirstName() != null ? request.getUser().getFirstName() : "";
        String lastName = request.getUser().getLastName() != null ? request.getUser().getLastName() : "";
        return "Cher/Chère " + firstName + " " + lastName + ","
                + "\n\nNous sommes ravis de vous accueillir dans notre communauté."
                + "\n\nCordialement,"
                + "\n\nInfoEvent";
    }

    public static String constructConfirmationEmailBody(NotificationRequest request) {
        String firstName = request.getUser().getFirstName() != null ? request.getUser().getFirstName() : "";
        String lastName = request.getUser().getLastName() != null ? request.getUser().getLastName() : "";
        String eventName = request.getEvent().getName() != null ? request.getEvent().getName() : "l'événement";
        String dateTime = request.getEvent().getDate().toString() != null && request.getEvent().getStartTime().toString() != null
                ? request.getEvent().getDate() + "a" + request.getEvent().getStartTime() : "la date";
        String location = request.getVenue().getLocation() != null ? request.getVenue().getLocation().toString() : "le lieu";
        String ticketType = request.getOfferType().getDescription() != null ? request.getOfferType().getDescription() : "le type de billet";
        String price = request.getPrice() != null ? request.getPrice() : "le montant";

        return "Cher/Chère " + firstName + " " + lastName + ","
                + "\n\nNous vous remercions pour votre achat et sommes ravis de vous compter parmi nous pour " + eventName + "."
                + "\n\nVeuillez trouver ci-joint votre billet pour l'événement. Les détails de votre achat sont les suivants :\n"
                + "\n- Nom de l'événement : " + eventName
                + "\n- Date et heure : " + dateTime
                + "\n- Lieu : " + location
                + "\n- Type de billet : " + ticketType
                + "\n- Montant : " + price
                + "\n\nNous vous conseillons d'arriver un peu en avance pour éviter toute attente inutile et pour faciliter le contrôle d'accès."
                + "\n\nSi vous avez des questions ou si vous avez besoin d'informations supplémentaires, n'hésitez pas à nous contacter."
                + "\n\nNous avons hâte de vous accueillir !"
                + "\n\nCordialement,"
                + "\n\nInfoEvent";
    }
}