package com.example.xtr100.app1_diplomado_calc.entities;

/**
 * Created by XTR100 on 01/07/2016.
 */
public class Operation {

    int numberA;
    int numberB;
    int result;

    public int getNumberA() {
        return numberA;
    }

    public void setNumberA(int numberA) {
        this.numberA = numberA;
    }

    public int getNumberB() {
        return numberB;
    }

    public void setNumberB(int numberB) {
        this.numberB = numberB;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "numberA=" + numberA +
                ", numberB=" + numberB +
                ", result=" + result +
                '}';
    }
}
