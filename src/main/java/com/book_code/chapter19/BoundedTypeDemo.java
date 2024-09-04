package com.book_code.chapter19;

import com.book_code.chapter13.Circle;
import com.book_code.chapter13.GeometricObject;
import com.book_code.chapter13.Rectangle;

public class BoundedTypeDemo {
  public static void main(String[] args ) {
    Rectangle rectangle = new Rectangle(2, 2);
    Circle circle = new Circle(2);

    System.out.println("Same area? " +
      equalArea(rectangle, circle));
  }

  public static <E extends GeometricObject> boolean equalArea(
      E object1, E object2) {
    return object1.getArea() == object2.getArea();
  }
}