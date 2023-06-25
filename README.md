# OttoTestCode

# IP Range Controller

This project is a RESTful API that retrieves IP ranges from the Amazon Web Services (AWS) IP Ranges API and provides endpoints to query and filter the IP ranges.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Mockito
- JUnit

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven

### Installation

1. Clone the repository:

   ```bash
   git clone [https://github.com/your-username/ip-range-controller.git](https://github.com/aljaserm/OttoTestCode.git)
   ```

2. Navigate to the project directory:

   ```bash
   cd ip-range-controller
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

### Running the Application

1. Run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```

   The application will start on the default port 8080.

2. Test the endpoints using a REST client (e.g., cURL or Postman) or by accessing them directly in a web browser:

   - Retrieve all IP ranges:
     - GET http://localhost:8080/ip-ranges

   - Retrieve IP ranges for a specific region:
     - GET http://localhost:8080/ip-ranges?region=<region_code>

     Replace `<region_code>` with the desired AWS region code (e.g., us).

Feel free to modify and expand on this template to provide more information specific to your project.
