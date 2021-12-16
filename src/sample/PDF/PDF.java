package sample.PDF;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import sample.database.Const;
import sample.database.DatabaseHandler;

public class PDF {


    private static Font font_regular = new Font(loadBaseFont("sample/css/fonts/FiraSans-Light.ttf"),
            12, Font.NORMAL, BaseColor.BLACK);
    private static Font font_medium = new Font(loadBaseFont("sample/css/fonts/FiraSans-Regular.ttf"),
            12, Font.NORMAL, BaseColor.BLACK);

    private static BaseFont loadBaseFont(String fontName) {
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseFont;
    }

    public void getPDFofTable(FileOutputStream fos) throws DocumentException, IOException, URISyntaxException, SQLException, ClassNotFoundException {

        Document document = new Document();
        PdfWriter.getInstance(document, fos);
        document.open();

        PdfPTable table = new PdfPTable(7);
        float[] ar = {5,1.2f,2.5f,5.2f,5,8,4.5f};
        table.setWidthPercentage(100);
        table.setPaddingTop(25);
        table.setWidths(ar);
        addTableHeader(table);
        addRows(table, Const.TOP);

        document.add(new Paragraph("Расписание по верхней неделе:", font_medium));
        document.add(new Phrase(" ", font_medium));

        document.add(table);

        document.add(new Paragraph("\nРасписание по нижней неделе:\n", font_medium));
        document.add(new Phrase(" "));
        table.flushContent();
        addTableHeader(table);
        addRows(table, Const.BOTTOM);
        document.add(table);
        document.close();
        fos.flush();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("День недели","№","Время", "Тип", "Дисциплина", "Преподователь", "Аудитория").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setPhrase(new Phrase(columnTitle, font_medium));
                table.addCell(header);
            });
    }

    private void addRows(PdfPTable table, String week) throws SQLException, ClassNotFoundException {
        DatabaseHandler Handler = new DatabaseHandler();
        ResultSet result = Handler.getSubjectForTable(week);
        String check = "", day, type = "";

        while (result.next()) {
            day = result.getString(Const.SUBJECT_DAY);
            if (day.equals(check)) day = "";
            else if (result.getString(Const.SUBJECT_DAY).equals("1")) day = "Понедельник";
            else if (result.getString(Const.SUBJECT_DAY).equals("2")) day = "Вторник";
            else if (result.getString(Const.SUBJECT_DAY).equals("3")) day = "Среда";
            else if (result.getString(Const.SUBJECT_DAY).equals("4")) day = "Четверг";
            else if (result.getString(Const.SUBJECT_DAY).equals("5")) day = "Пятница";
            else if (result.getString(Const.SUBJECT_DAY).equals("6")) day = "Суббота";
            else if (result.getString(Const.SUBJECT_DAY).equals("7")) day = "Воскресенье";

            if (day.equals("")) day = result.getString(Const.SUBJECT_DAY);

            if (result.getString(Const.SUBJECT_TYPE).equals(Const.LECTURE)) type = "Лекция";
            else if (result.getString(Const.SUBJECT_TYPE).equals(Const.SEMINAR)) type = "Семинар";
            else if (result.getString(Const.SUBJECT_TYPE).equals(Const.PRACTICE)) type = "Практика";
            else if (result.getString(Const.SUBJECT_TYPE).equals(Const.LABORATORY)) type = "Лабораторная";

            if (day.equals(check)){
                day = "";
                addBaseRow(table, day);
                addBaseRow(table, String.valueOf(result.getInt(Const.SUBJECT_SERIAL_NUMBER)));
                addBaseRow(table,result.getString(Const.SUBJECT_TIME_START).substring(0, 5) + " - "
                        + result.getString(Const.SUBJECT_TIME_END).substring(0, 5));
                addBaseRow(table, type);
                addBaseRow(table, result.getString(Const.SUBJECT_NAME));
                addBaseRow(table, result.getString(Const.SUBJECT_TEACHER));
                addBaseRow(table, result.getString(Const.SUBJECT_ROOM));
            }
            else {
                addBoldRow(table, day);
                addBoldRow(table, String.valueOf(result.getInt(Const.SUBJECT_SERIAL_NUMBER)));
                addBoldRow(table, result.getString(Const.SUBJECT_TIME_START).substring(0, 5) + " - "
                        + result.getString(Const.SUBJECT_TIME_END).substring(0, 5));
                addBoldRow(table, type);
                addBoldRow(table, result.getString(Const.SUBJECT_NAME));
                addBoldRow(table, result.getString(Const.SUBJECT_TEACHER));
                addBoldRow(table, result.getString(Const.SUBJECT_ROOM));
            }
            check = result.getString(Const.SUBJECT_DAY);
        }
    }
    private void addBoldRow(PdfPTable table, String data){
        PdfPCell cell = new PdfPCell();
        cell.setBorderWidthTop(1.5f);
        cell.setPhrase(new Phrase(data, font_regular));
        table.addCell(cell);
    }
    private void addBaseRow(PdfPTable table, String data){
        table.addCell(new Phrase(data, font_regular));
    }
}
