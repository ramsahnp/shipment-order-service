SpringBoot Postgresql BD based App.

This is a simple app which creates shipping order and tracking of its status.
1. Create/Place Order:
Api-Url: http://localhost:8080/api/v1/shipment/create
It also validates the input request data and then create unique order id of 10-digits.
By default order status is in pending status and shipment date is 7 days.

2. Track Order:
http://localhost:8080/api/v1/shipment/track-order/{order-id}
It know the status of the order





