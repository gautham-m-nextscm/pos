package com.shubham.increff.Dto;

import com.shubham.increff.model.*;
import com.shubham.increff.pojo.*;
import com.shubham.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandCategoryService brandCategoryService;

    @Transactional
    public OrderPojo createOrder(OrderForm orderForm) throws ApiException {
        //converting the orderForm to OrderPojo
        OrderPojo orderPojo = convert(orderForm);
        //Adding Order to OrderTable in Db
        orderService.createOrder(orderPojo);
        //saving the orderId to int variable
        int orderId =orderPojo.getId();  //orderService.getId(orderPojo);
        //saving the list of orderitem from orderForm into orderDetailFormList varible
        List<OrderFormDetail> orderFormDetailList = orderForm.getOrderDetailList();
        //creating the Pojo list that will be used to add orderItems in orderItemTable
        List<OrderItemPojo> orderItemPojoList = new ArrayList<OrderItemPojo>();

        //converting the OrderDetailForm List into OrderItemPojo List
        for (OrderFormDetail orderFormDetail : orderFormDetailList) {
            //getting product id from barcode
            int productId = productService.getProductIdByBarcode(orderFormDetail.getBarcode());
            //creating orderItemPojo from orderDetailForm, orderId, productId
            OrderItemPojo orderItemPojo = createOrderItemPojo(orderFormDetail, orderId, productId);//method name change
            //adding OrderItemPojo to orderItemPojoList;
            orderItemPojoList.add(orderItemPojo);
        }

        //looping over converted OrderItemPojo List and adding orderItems to OrderItem Table
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            //getting productId to fetch inventory stock count
            int productId = orderItemPojo.getProductId();
            //getting inventoryPojo from productId
            InventoryPojo inventoryPojo = inventoryService.get(productId);
            //validating that order qty is less then available qty;
            if (inventoryPojo.getQuantity() < orderItemPojo.getQuantity()) {
                throw new ApiException("order quantity is greater then available");
            }
            //Updating new inventory count
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemPojo.getQuantity());
            //updating the count to InventoryTable
            inventoryService.update(productId, inventoryPojo);
            //adding OrderItem details in orderItem Table
            orderItemService.add(orderItemPojo);
        }
        //returning order id
        return  orderPojo;
    }

    public OrderData getOrderById(int orderId) throws ApiException {
        //getting the orderPojo from orderId;
        OrderPojo orderPojo = orderService.get(orderId);
        //getting all OrderItem By orderId in OrderItemPojo List
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllOrderItemByOrderId(orderId);
        //creating new List OrderItemDataDetail for returning
        List<OrderItemDataDetail> orderItemDataDetailList = new ArrayList<OrderItemDataDetail>();
        //converting in OrderItemPojo List into OrderItemDataDetail List
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            //getting product pojo to get name,barcode
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            //getting BrandCategory pojo to get brand,category
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getId());
            //creating createOrderItemDataDetail
            OrderItemDataDetail orderItemDataDetail=createOrderItemDataDetail(orderItemPojo, productPojo, brandCategoryPojo);
            //adding createOrderItemDataDetail to list
            orderItemDataDetailList.add(orderItemDataDetail);
        }
        //creating returning orderdata which need to be return
        return createorderData(orderPojo, orderItemDataDetailList);
    }

    public List<OrderData> getAllOrders() throws ApiException {
        //getting list of all order pojos
        List<OrderPojo> orderPojoList = orderService.getAll();
        //creating list of OrderData
        List<OrderData> orderDataList = new ArrayList<OrderData>();
        //looping over order pojo and getting orderdata in list
        for (OrderPojo orderPojo : orderPojoList) {
            int orderId = orderPojo.getId();
            //getting order data by order id
            orderDataList.add(getOrderById(orderId));
        }
        //returning order data list
        return orderDataList;
    }

    public void updateOrder(int orderId, OrderUpdateForm orderUpdateForm) throws ApiException {
        OrderPojo orderPojo = convert(orderUpdateForm);
        orderService.updateOrder(orderId, orderPojo);
        List<OrderUpdateFormDetail> orderUpdateFormDetailList = orderUpdateForm.getOrderUpdateFormDetailList();
        List<OrderItemPojo> orderItemPojoList = new ArrayList<OrderItemPojo>();
        for (OrderUpdateFormDetail orderUpdateFormDetail : orderUpdateFormDetailList) {
            //orderItem id to find and update order It
            int orderItemId=orderUpdateFormDetail.getOrderItemId();
            //getting productid from barcode
            int productId = productService.getProductIdByBarcode(orderUpdateFormDetail.getBarcode());
            //creating orderItemPojo from orderDetailForm, orderId, productId
            OrderItemPojo orderItemPojo = createOrderItemPojo(orderUpdateFormDetail, orderId, productId);
            //updating the order
            orderItemService.updateOrderItem(orderItemId,orderItemPojo);
        }


    }

    private static OrderData createorderData(OrderPojo orderPojo, List<OrderItemDataDetail> orderItemDataDetailList) {
        OrderData orderData = new OrderData();
        orderData.setOrderDateTime(orderPojo.getDate_time());
        orderData.setId(orderPojo.getId());
        orderData.setOrderItemDataDetailList(orderItemDataDetailList);
        return orderData;
    }


    private static OrderItemDataDetail createOrderItemDataDetail(OrderItemPojo orderItemPojo, ProductPojo productPojo, BrandCategoryPojo brandCategoryPojo) {
        OrderItemDataDetail orderItemDataDetail = new OrderItemDataDetail();
        orderItemDataDetail.setQuantity(orderItemPojo.getQuantity());
        orderItemDataDetail.setSellingprice(orderItemPojo.getSellingPrice());
        orderItemDataDetail.setOrderItemId(orderItemPojo.getId());
        orderItemDataDetail.setProductId(orderItemPojo.getProductId());
        orderItemDataDetail.setName(productPojo.getName());
        orderItemDataDetail.setBarcode(productPojo.getBarcode());
        orderItemDataDetail.setBrand(brandCategoryPojo.getBrand());
        orderItemDataDetail.setCategory(brandCategoryPojo.getCategory());
        return orderItemDataDetail;
    }

    private static OrderPojo convert(OrderForm orderForm) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate_time(orderForm.getOrderDateTime());
        return orderPojo;
    }
    private static OrderPojo convert(OrderUpdateForm orderUpdateForm) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDate_time(orderUpdateForm.getOrderDateTime());
        return orderPojo;
    }

    private static OrderItemPojo createOrderItemPojo(OrderFormDetail orderFormDetail, int orderId, int productId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(orderFormDetail.getQuantity());
        orderItemPojo.setSellingPrice(orderFormDetail.getSellingprice());
        return orderItemPojo;
    }
    private static OrderItemPojo createOrderItemPojo(OrderUpdateFormDetail orderUpdateFormDetail, int orderId, int productId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(orderUpdateFormDetail.getQuantity());
        orderItemPojo.setSellingPrice(orderUpdateFormDetail.getSellingprice());
        return orderItemPojo;
    }

}
