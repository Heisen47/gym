package com.example.gym.Controller;

import com.example.gym.dto.InvoiceRequest;
import com.example.gym.model.Invoice;
import com.example.gym.model.Payment;
import com.example.gym.model.User;
import com.example.gym.service.InvoiceService;
import com.example.gym.service.PaymentService;
import com.example.gym.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private UserService userService;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private InvoiceRequest invoiceRequest;
    private Invoice invoice;
    private User user;
    private Payment payment;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");

        payment = new Payment();
        payment.setPaymentAmount("100.00");

        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setUserId(1L);
        invoiceRequest.setInvoiceAmount("100.00");
        invoiceRequest.setInvoicedBy("Admin");
        invoiceRequest.setProduct("MEMBERSHIP");

        invoice = new Invoice();
        invoice.setInvoiceDate(ZonedDateTime.now());
        invoice.setInvoiceAmount("100.00");
        invoice.setInvoicedBy("Admin");
        invoice.setProduct("MEMBERSHIP");
        invoice.setUser(user);
        invoice.setPayment(payment);
    }

    @Test
    void createInvoice_WithValidData_ShouldReturnCreatedInvoice() throws Exception {
        // Arrange
        when(userService.getSingleUser(1L)).thenReturn(Optional.of(user));
        when(paymentService.getLatestPaymentForUser(1L)).thenReturn(Optional.of(payment));
        when(invoiceService.saveInvoice(any(Invoice.class))).thenReturn(invoice);

        // Act & Assert
        mockMvc.perform(post("/admin/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoiceRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.invoiceAmount").value("100.00"))
                .andExpect(jsonPath("$.invoicedBy").value("Admin"))
                .andExpect(jsonPath("$.product").value("MEMBERSHIP"));
    }

    @Test
    void createInvoice_WithInvalidUserId_ShouldStillCreateInvoice() throws Exception {
        // Arrange
        when(userService.getSingleUser(1L)).thenReturn(Optional.empty());
        when(paymentService.getLatestPaymentForUser(1L)).thenReturn(Optional.empty());
        when(invoiceService.saveInvoice(any(Invoice.class))).thenReturn(invoice);

        // Act & Assert
        mockMvc.perform(post("/admin/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoiceRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getInvoiceByUserId_WithValidUserId_ShouldReturnInvoices() throws Exception {
        // Arrange
        Page<Invoice> invoicePage = new PageImpl<>(Collections.singletonList(invoice));
        when(invoiceService.getInvoiceByUserId(1L, PageRequest.of(0, 10)))
                .thenReturn(invoicePage);

        // Act & Assert
        mockMvc.perform(get("/admin/invoice/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].invoiceAmount").value("100.00"))
                .andExpect(jsonPath("$.content[0].invoicedBy").value("Admin"));
    }

    @Test
    void getInvoiceByUserId_WithInvalidUserId_ShouldReturnEmptyPage() throws Exception {
        // Arrange
        Page<Invoice> emptyPage = new PageImpl<>(Collections.emptyList());
        when(invoiceService.getInvoiceByUserId(999L, PageRequest.of(0, 10)))
                .thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/admin/invoice/999")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}