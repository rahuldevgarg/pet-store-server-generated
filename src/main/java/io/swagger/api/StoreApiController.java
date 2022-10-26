package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Order;
import io.swagger.services.Impl.StoreServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T12:36:17.472Z[GMT]")
@RestController
public class StoreApiController implements StoreApi {

    private static final Logger log = LoggerFactory.getLogger(StoreApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final StoreServiceImpl storeService;

    @org.springframework.beans.factory.annotation.Autowired
    public StoreApiController(ObjectMapper objectMapper, HttpServletRequest request, StoreServiceImpl storeService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.storeService = storeService;
    }

    public ResponseEntity<Void> deleteOrder(@Parameter(in = ParameterIn.PATH, description = "ID of the order that needs to be deleted", required = true, schema = @Schema()) @PathVariable("orderId") Long orderId) {
        String accept = request.getHeader("accept");
        try {
            storeService.deleteOrder(orderId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.resolve(404));
        }
    }

    public ResponseEntity<Map<String, Integer>> getInventory() {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            var inventory = storeService.getInventory();
            return new ResponseEntity<Map<String, Integer>>(inventory,HttpStatus.OK);
        }

        return new ResponseEntity<Map<String, Integer>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Order> getOrderById(@Parameter(in = ParameterIn.PATH, description = "ID of order that needs to be fetched", required = true, schema = @Schema()) @PathVariable("orderId") Long orderId) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Optional<Order> order = storeService.getOrderById(orderId);
                return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error(e.getMessage(),e);
                return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<Order>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Order> placeOrder(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Order body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Order order = storeService.placeOrder(body);
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error(e.getMessage(),e);
                return new ResponseEntity<Order>(HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<Order>(HttpStatus.NOT_IMPLEMENTED);
    }

}
