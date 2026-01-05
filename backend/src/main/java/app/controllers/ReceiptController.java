package app.controllers;

import app.config.HibernateConfig;
import app.daos.ReceiptDAO;
import app.dtos.receipt.ReceiptDTO;
import app.entities.Receipt;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReceiptController {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    ReceiptDAO receiptDAO = new ReceiptDAO(emf);

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");


    public void getAll(Context ctx){
        try {
            List<ReceiptDTO> receiptDTOS = receiptDAO.getAllReceipt();
            if (receiptDTOS.isEmpty()) {
                ctx.status(400);
                ctx.result("No receipt found");
                logger.info("Couldnt get the fetched data");
            } else  {
                ctx.status(200);
                ctx.json(receiptDTOS);
            }
        } catch(ApiException e){
            System.out.println(e.getMessage());
            ctx.json(e.getMessage());
        }


    }



    public void getReceipt(Context ctx){
    int id = Integer.parseInt(ctx.pathParam("id"));

    try {
        Receipt receipt = receiptDAO.findById(id);
        ReceiptDTO receiptDTO = new ReceiptDTO(receipt);
        if (receiptDTO != null) {
            ctx.status(201);
            ctx.json(receiptDTO);
            logger.info("found the receipt with chosen id");
        } else {
            ctx.status(404);
            ctx.result("Not Found");
            logger.warn("receipt not found" + id);
        }
    }catch(ApiException e){
        System.out.println(e.getMessage());
        ctx.json(e.getMessage());
    }

    }


}
