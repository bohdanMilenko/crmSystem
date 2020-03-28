# CRM System for Bank

This is a back-end application for a bank system. It gives an opportunity to create a Customer Account, and operate on three different financial products: Checking Account, Credit Line, RRSP Account. The customer has an opportunity to deposit/withdraw check balance, check if he is eligible for a certain promotion and apply for it afterwards.  

## Main features:
- Application is built using a Service-Oriented Architecture as well as Singleton design pattern
- Service level is responsible for operations on passed entities and ORM
- Main classes are covered with Junit tests, addressing each & every aspect of the method in the given class
- Utilities class is responsible for getting User Input and assuring the correct format (Regex)
- Abstract classes and Interfaces are used to impose scalability and group classes by their functionality
