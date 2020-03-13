package ru.dvkombarov.app.domain;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Question {

    @CsvBindByName
    private String number;

    @CsvBindByName
    private String text;

    @CsvBindByName
    private String answer;

    public Question() {
    }

    public String getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(number, question.number) &&
                Objects.equals(text, question.text) &&
                Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, text, answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "number=" + number +
                ", text='" + text + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
