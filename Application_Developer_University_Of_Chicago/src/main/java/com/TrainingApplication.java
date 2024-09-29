package com;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TrainingApplication {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Read the data from the JSON file
        List<Person> persons = mapper.readValue(new File("src/trainings.txt"),
                mapper.getTypeFactory().constructCollectionType(List.class, Person.class));

        // Task 1: List each completed training with the count of how many people completed it
        Map<String, Integer> trainingCompletionCount = getTrainingCompletionCount(persons);
        writeToFile("src/task1_output.txt", trainingCompletionCount);
        System.out.println("Training Completion Count: " + trainingCompletionCount);

        // Task 2: Given trainings and fiscal year, list all people who completed them
        List<String> trainings = Arrays.asList("Electrical Safety for Labs", "X-Ray Safety", "Laboratory Safety Training");
        int fiscalYear = 2024;
        Map<String, List<String>> fiscalYearCompletions = getFiscalYearCompletions(persons, trainings, fiscalYear);
        writeToFile("src/task2_output.txt", fiscalYearCompletions);
        System.out.println("Completions for FY " + fiscalYear + ": " + fiscalYearCompletions);

        // Task 3: List people whose trainings have expired or will expire soon
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        Map<String, List<ExpiryStatus>> expiringTrainings = getExpiringTrainings(persons, targetDate);
        writeToFile("src/task3_output.txt", expiringTrainings);
        System.out.println("Expiring Trainings: " + expiringTrainings);
    }

    // Task 1: List each completed training with a count of how many people have completed it
    public static Map<String, Integer> getTrainingCompletionCount(List<Person> persons) {
        Map<String, Integer> completionCount = new HashMap<>();

        for (Person person : persons) {
            for (Completion completion : person.getCompletions()) {
                completionCount.put(completion.getName(),
                        completionCount.getOrDefault(completion.getName(), 0) + 1);
            }
        }
        return completionCount;
    }

    // Task 2: For specified trainings and fiscal year, list all people who completed that training in the fiscal year
    public static Map<String, List<String>> getFiscalYearCompletions(List<Person> persons, List<String> trainings, int fiscalYear) {
        Map<String, List<String>> completionsMap = new HashMap<>();
        LocalDate fiscalStart = LocalDate.of(fiscalYear - 1, 7, 1);
        LocalDate fiscalEnd = LocalDate.of(fiscalYear, 6, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (Person person : persons) {
            for (Completion completion : person.getCompletions()) {
                if (trainings.contains(completion.getName())) {
                    LocalDate completionDate = LocalDate.parse(completion.getTimestamp(), formatter);
                    if (!completionDate.isBefore(fiscalStart) && !completionDate.isAfter(fiscalEnd)) {
                        completionsMap.computeIfAbsent(completion.getName(), k -> new ArrayList<>()).add(person.getName());
                    }
                }
            }
        }
        return completionsMap;
    }

    // Task 3: Find people whose trainings have expired or will expire within one month of the specified date
    public static Map<String, List<ExpiryStatus>> getExpiringTrainings(List<Person> persons, LocalDate targetDate) {
        Map<String, List<ExpiryStatus>> expiringMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        for (Person person : persons) {
            for (Completion completion : person.getCompletions()) {
                if (completion.getExpires() != null && !completion.getExpires().isEmpty()) {
                    LocalDate expiryDate = LocalDate.parse(completion.getExpires(), formatter);
                    if (expiryDate.isBefore(targetDate) || expiryDate.isBefore(targetDate.plus(1, ChronoUnit.MONTHS))) {
                        ExpiryStatus status = new ExpiryStatus(completion.getName(), expiryDate.isBefore(targetDate) ? "expired" : "expires soon");
                        expiringMap.computeIfAbsent(person.getName(), k -> new ArrayList<>()).add(status);
                    }
                }
            }
        }
        return expiringMap;
    }

    public static <T> void writeToFile(String filename, T data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


