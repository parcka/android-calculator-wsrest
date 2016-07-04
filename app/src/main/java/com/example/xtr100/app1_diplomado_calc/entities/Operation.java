package com.example.xtr100.app1_diplomado_calc.entities;

/**
 * Created by XTR100 on 01/07/2016.
 */
public class Operation {

    Integer numberA;
    Integer numberB;
    String operatorSimbol;
    Integer result;

    public Integer getNumberA() {
        return numberA;
    }

    public void setNumberA(Integer numberA) {
        this.numberA = numberA;
    }

    public Integer getNumberB() {
        return numberB;
    }

    public void setNumberB(Integer numberB) {
        this.numberB = numberB;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getOperatorSimbol() {
        return operatorSimbol;
    }

    public void setOperatorSimbol(String operatorSimbol) {
        this.operatorSimbol = operatorSimbol;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "numberA=" + numberA +
                ", numberB=" + numberB +
                ", operatorSimbol='" + operatorSimbol + '\'' +
                ", result=" + result +
                '}';
    }
}
