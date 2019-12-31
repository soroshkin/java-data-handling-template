package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        StringBuilder textFromFile = new StringBuilder();
        try (FileReader fileReader = new FileReader(new File("src\\main\\resources\\sensitive_data.txt"));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                textFromFile.append(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textFromFile.toString().replaceAll("(\\s\\d{4})(\\s\\d{4}\\s\\d{4}\\s)(\\d{4}\\s)", "$1 **** **** $3");
    }


    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        StringBuilder textFromFile = new StringBuilder();
        try (FileReader fileReader = new FileReader("src\\main\\resources\\sensitive_data.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                textFromFile.append(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textFromFile.toString().replaceAll("(\\$\\{payment_amount})(.+)(\\$\\{balance})(.+)", String.format("%.0f$2%.0f$4", paymentAmount, balance));
    }
}
