import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor() {
  }

  getCart() {
    const cartJson = sessionStorage.getItem('cart');
    if (cartJson) {
      return JSON.parse(cartJson);
    } else {
      return [];
    }
  }

  saveCart(cart: any) {
    const cartJson = JSON.stringify(cart);
    sessionStorage.setItem('cart', cartJson);
  }

  getTotalQuantity(): number {
    const carts = this.getCart();
    let totalQuantity = 0;
    carts.forEach((item) => {
      totalQuantity += item.quantity;
    });
    return totalQuantity;
  }

  getTotalPrice(): number {
    const carts = this.getCart();
    let totalPrice = 0;
    carts.forEach((item) => {
      totalPrice += (item.price * item.quantity);
    });
    return totalPrice;
  }
}
