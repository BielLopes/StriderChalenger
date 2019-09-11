package mvc.springframework.serveces;

import mvc.springframework.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer findCustomerById(Long id);

    List<Customer> findAllCustomers();

    Customer saveCustomer(Customer customer);
}
