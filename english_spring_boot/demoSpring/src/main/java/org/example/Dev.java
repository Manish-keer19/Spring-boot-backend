package org.example;

public class Dev  {

//  private  int age;

//  private  Leptop leptop;

    private  Computer computer;

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Dev() {
        System.out.println("Dev constructore");
    }



//    public Dev(int age) {
//        this.age = age;
//        System.out.println("parm constructore");
//    }

//    public Dev(Leptop leptop) {
//        this.leptop = leptop;
//    }
//
//    public Leptop getLeptop() {
//        return leptop;
//    }

//    public void setLeptop(Leptop leptop) {
//        this.leptop = leptop;
//    }

//    public int getAge() {
//        return age;
//    }

//    public void setAge(int age) {
//        this.age = age;
//    }

    public void  build() {

        System.out.println("we are building awesome project ");
        computer.compile();
    }
}
