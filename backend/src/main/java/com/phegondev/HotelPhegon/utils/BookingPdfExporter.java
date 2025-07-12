package com.phegondev.HotelPhegon.utils; // ou o pacote de sua preferência

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.phegondev.HotelPhegon.entity.Booking;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingPdfExporter {

    private final List<Booking> bookingList;

    public BookingPdfExporter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(3, 7, 34)); // Cor azul escuro
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);

        String[] headers = {"ID Reserva", "Hóspede", "Email", "Quarto", "Check-In", "Check-Out"};

        for (String header : headers) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Booking booking : bookingList) {
            table.addCell(String.valueOf(booking.getId()));
            table.addCell(booking.getUser() != null ? booking.getUser().getName() : "N/A");
            table.addCell(booking.getUser() != null ? booking.getUser().getEmail() : "N/A");
            table.addCell(booking.getRoom() != null ? booking.getRoom().getRoomType() : "N/A");
            table.addCell(booking.getCheckInDate().format(formatter));
            table.addCell(booking.getCheckOutDate().format(formatter));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        // Configura a resposta HTTP para indicar que é um arquivo PDF
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_reservas_" + java.time.LocalDate.now() + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Cria o documento PDF
        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Adiciona o Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(3, 7, 34));
            Paragraph title = new Paragraph("Relatório de Reservas", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Cria a tabela
            PdfPTable table = new PdfPTable(6); // 6 colunas
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{1.5f, 3.5f, 4.0f, 3.0f, 2.5f, 2.5f});
            table.setSpacingBefore(10);

            // Adiciona o cabeçalho e os dados
            writeTableHeader(table);
            writeTableData(table);

            document.add(table);
        }
    }
}