package org.levex.games.fallingsand;

public class Complex{

    private double a, b;
    public Complex(double a, double b){
        this.a = a;
        this.b = b;
    }
    public Complex square(){
        return new Complex(this.a*this.a - this.b*this.b, 2*this.a*this.b);
    }
    public Complex add(Complex cn){
        return new Complex(this.a+cn.a, this.b+cn.b);
    }
    public double magnitude(){
        return a*a+b*b;
    }
}