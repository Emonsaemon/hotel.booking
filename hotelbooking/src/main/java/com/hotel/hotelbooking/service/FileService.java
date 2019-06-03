package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.model.Reservation;
import com.hotel.hotelbooking.model.Room;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    private SimpleDateFormat format;
    private RoomService roomService;

    @Autowired
    public FileService(RoomService roomService) {
        this.format = new SimpleDateFormat("dd-MM-YYYY");
        this.roomService = roomService;
    }

    public Document generateReport(PdfDocument pdf) {
        java.sql.Date dates[] = getReportDates();

        Document report = generateStandardDocument(pdf, dates);
        Table table = getStatsTableHeader();

        int daysBooked;
        double income;
        double total = 0.0;

        List<Room> rooms = roomService.findAll();
        for (Room room: rooms) {
            daysBooked =0;
            income = 0.0;

            List<Reservation> reservations = room.getReservations();
            for (Reservation reservation: reservations) {
                income += reservation.getPrice().doubleValue();
                daysBooked += (reservation.getEndDate().getTime() -
                        reservation.getStartDate().getTime())
                        /(1000*60*60*24);
            }
            total +=income;
            table = addRow(table, room.getFloor(), room.getNumber(),
                    daysBooked, income);
        }

        report.add(table);
        report = addFooter(report, total);
        return report;
    }

    private Document addFooter(Document report, double total) {
        Text lineBreak = new Text("\n");
        report.add(new Paragraph(lineBreak));

        Text totalIncome = new Text("Total income: " + total + "UAH");
        try {
            PdfFont font =
                    PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            totalIncome.setFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }
        report.add(new Paragraph(totalIncome));

        return report;
    }

    private Table addRow(Table table, Byte floor, Byte number,
                         int daysBooked, double income) {
        table.addCell(new Cell().add("" + floor));
        table.addCell(new Cell().add("" + number));
        table.addCell(new Cell().add("" + daysBooked));
        table.addCell(new Cell().add("" + income));

        return table;
    }

    private Table getStatsTableHeader() {
        float [] pointColumnWidths = {100F, 100F, 160F, 160F};
        Table table = new Table(pointColumnWidths);

        table.addCell(new Cell().add("Floor"));
        table.addCell(new Cell().add("Number"));
        table.addCell(new Cell().add("Days booked"));
        table.addCell(new Cell().add("Income, UAH"));

        return table;
    }

    private Document generateStandardDocument(PdfDocument pdf, Date[] dates) {
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("Report for year " + getYear());
        Document report = new Document(pdf);

        Text text = new Text("Report");
        String time = format.format(dates[0]) + " - " + format.format(dates[1]);
        Text month = new Text("Year " + getYear() + "\n" + time);

        try {
            PdfFont font =
                    PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            text.setFont(font);
            PdfFont monthFont =
                    PdfFontFactory.createFont(FontConstants.TIMES_ITALIC);
            month.setFont(monthFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        report.add(new Paragraph(text));
        report.add(new Paragraph(month));

        Text lineBreak = new Text("\n");
        report.add(new Paragraph(lineBreak));

        return report;
    }

    private String getYear() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    private java.sql.Date[] getReportDates() {
        java.sql.Date dates[] = new java.sql.Date[2];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, min);
        dates[0] = new java.sql.Date(calendar.getTime().getTime());


        dates[1] = new java.sql.Date((new Date()).getTime());

        return dates;
    }
}
