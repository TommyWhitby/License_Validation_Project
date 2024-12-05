# License Validation Java Application

## Project Overview
This Java application provides a robust license validation system using Java Streams and functional programming techniques. The project demonstrates advanced stream processing, data validation, and filtering capabilities.

## Key Features
- Utalises a Single Stream pipeline for all processing and manipulation of the data
- Comprehensive license validation checks
- Flexible data parsing using Java Streams and Collectors
- Detailed validation rules for license attributes

## Validation Criteria
The application validates licenses based on multiple attributes:

### Required Fields
- Checks for presence of critical license information
- Allows optional state field

### Age and Issuance Validation
- Confirms license holder is 21 years or older
- Verifies license issuance date is not in the future
- Ensures license was issued within the last 10 years
- Checks license expiration status

### Physical Characteristics Validation
- Height verification:
  * 150-193 cm inclusive
  * 59-76 inches inclusive
- Hair color validation (HTML hex color code)
- Eye color validation (predefined set of colors)

### Identification Number Validation
- 9-digit decimal license number check

## Technical Implementation
- Utilizes Java Streams API
- Implements functional programming paradigms
- Uses Lambda expressions
- Leverages Java Collectors for data processing

## Input
- License data in key-value pair format
- Batch processing of multiple license records
- Supports various input formats and configurations

## Output
- Prints validated license records
- Provides count of valid licenses
- Formatted output with separator lines

## Prerequisites
- Java Development Kit (JDK)
- Basic understanding of Java Streams and functional programming

## How to Run
1. Create a Java project
2. Place license data in the `data/pass.txt` file
3. Compile and run the `Pass.java` application

## Skills Demonstrated
- Advanced Java Stream processing
- Data validation techniques
- Functional programming
- Complex input parsing
