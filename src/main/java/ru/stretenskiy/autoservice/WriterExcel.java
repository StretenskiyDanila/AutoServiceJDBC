package ru.stretenskiy.autoservice;

import ru.stretenskiy.autoservice.tables.CostCars;
import ru.stretenskiy.autoservice.tables.MasterCountCars;
import ru.stretenskiy.autoservice.tables.Servicing;
import ru.stretenskiy.autoservice.tables.Work;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

public class WriterExcel {

    private static final File file = new File("src\\main\\resources\\.");
    private static final String path = file.getAbsolutePath();
    private static final String fileLocation = path.substring(0, path.length() - 1);

    public void writeWorks(List<Work> listWork, String nameFile) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation + nameFile));
             Workbook wb = new Workbook(os, "Source Statistic", "1.0")) {
            Worksheet ws = wb.newWorksheet("List works");
            ws.width(0, 15);
            ws.width(1, 15);
            ws.width(2, 15);
            ws.width(3, 15);
            ws.width(4, 15);

            ws.value(0, 0, "Name service");
            ws.value(0, 1, "Numeric car");
            ws.value(0, 2, "Name master");
            ws.value(0, 3, "Date work");
            ws.value(0, 4, "Cost work");
            DecimalFormat dF = new DecimalFormat("#.##");
            int i = 1;
            for (Work work : listWork) {
                Servicing servicing = work.getServicing();
                ws.value(i, 0, servicing.getName());
                ws.value(i, 1, work.getCar().getCarNumber());
                ws.value(i, 2, work.getMaster().getName());
                ws.value(i, 3, work.getDateWork().toString());
                ws.value(i, 4, dF.format(work.getCar().getIsForeign() == 1 ? servicing.getCostForeign() : servicing.getCostOur()));
                i++;
            }
        }
    }

    public void writeCostService (CostCars costCars, Date fromDate, Date beforeDate, String nameFile) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation + nameFile));
             Workbook wb = new Workbook(os, "Source Statistic", "1.0")) {
            Worksheet ws = wb.newWorksheet("Cost Service");
            ws.width(0, 15);
            ws.width(1, 15);

            ws.value(0, 0, "Cost our");
            ws.value(0, 1, "Cost foreign");
            ws.value(0, 2, "Date from");
            ws.value(0, 3, "Date before");
            ws.value(1, 0, costCars.getAllCostOur());
            ws.value(1, 1, costCars.getAllCostForeign());
            ws.value(1, 2, fromDate.toString());
            ws.value(1, 3, beforeDate.toString());
        }
    }

    public void writeCountMaster (List<MasterCountCars> masterCountCars, Date fromDate, Date beforeDate, String nameFile) throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation + nameFile));
             Workbook wb = new Workbook(os, "Source Statistic", "1.0")) {
            Worksheet ws = wb.newWorksheet("Top 5 master");
            ws.width(0, 15);
            ws.width(1, 15);
            ws.width(2, 15);
            ws.width(3, 15);

            ws.value(0, 0, "Name master");
            ws.value(0, 1, "Count cars");
            ws.value(0, 2, "Date from");
            ws.value(0, 3, "Date before");
            ws.value(1, 2, fromDate.toString());
            ws.value(1, 3, beforeDate.toString());
            int i = 1;
            for (MasterCountCars master : masterCountCars) {
                ws.value(i, 0, master.getName());
                ws.value(i, 1, master.getCountCars());
                i++;
            }
        }
    }
}
