# Application_Developer_University_of_Chicago

# Training Management Application

This is a simple console application written in Java that processes training data in JSON format. It performs the following tasks:

1. **Counts the number of people who have completed each training.**
2. **Lists all people who completed specified trainings during a specified fiscal year.**
3. **Finds all people with completed trainings that have expired or are about to expire within one month of a given date.**

## Requirements

- Java 8 or higher
- Jackson library for JSON parsing
- `trainings.txt` file containing the input JSON data in the specified format at location Application_Developer_University_Of_Chicago/src/trainings.txt

## Getting Started

### Prerequisites

Ensure that you have the following installed on your system:

- Java 8 or higher
- Git (optional, if you're cloning the repository)
- Maven (for dependency management and build)

### Cloning the Repository

To clone the repository:

```bash
git clone https://github.com/shahdhairya6789/Application_Developer_University_of_Chicago.git
cd training-management-app

### Build and Run the Application

1. Run the following command to build the project and install dependencies:

   ```bash
   mvn clean install
   ```

2. After running the above command, the application will process the training data, and three output JSON files will be generated in the `target/` directory.

## Output Files

The output files will include:

- **completed_trainings.json**: A list of each completed training and the count of participants.
- **fiscal_year_training.json**: A list of participants who completed specified trainings in the fiscal year 2024.
- **expiring_trainings.json**: A list of participants with trainings that have expired or will expire soon.

## Usage

To run the application, execute the following command:

```bash
java -jar target/training-data-processing.jar
```

